BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Admin" (
	"AID"	INTEGER,
	PRIMARY KEY("AID"),
	FOREIGN KEY("AID") REFERENCES "User"("UID")
);
CREATE TABLE IF NOT EXISTS "Cashier" (
	"CAID"	INTEGER,
	PRIMARY KEY("CAID"),
	FOREIGN KEY("CAID") REFERENCES "User"("UID")
);
CREATE TABLE IF NOT EXISTS "Customer" (
	"CID"	INTEGER,
	PRIMARY KEY("CID"),
	FOREIGN KEY("CID") REFERENCES "Person"("ID")
);
CREATE TABLE IF NOT EXISTS "OrderItem" (
	"PID"	INTEGER,
	"OID"	INTEGER,
	"Desired_Quantity"	INTEGER NOT NULL,
	"Total_Price"	REAL,
	PRIMARY KEY("PID","OID"),
	FOREIGN KEY("OID") REFERENCES "Orders"("ID"),
	FOREIGN KEY("PID") REFERENCES "Product"("ID")
);
CREATE TABLE IF NOT EXISTS "Orders" (
	"ID"	INTEGER,
	"Date"	TEXT NOT NULL,
	"Discount"	REAL,
	"Payment_Method"	TEXT CHECK("Payment_Method" IN ('CASH', 'CREDIT')),
	"Calculated_Price"	REAL,
	"Total_Price"	REAL,
	"CID"	INTEGER,
	"CAID"	INTEGER,
	PRIMARY KEY("ID"),
	FOREIGN KEY("CAID") REFERENCES "Cashier"("CAID"),
	FOREIGN KEY("CID") REFERENCES "Customer"("CID")
);
CREATE TABLE IF NOT EXISTS "Person" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Contact_Info"	TEXT,
	"Type"	TEXT NOT NULL,
	PRIMARY KEY("ID")
);
CREATE TABLE IF NOT EXISTS "Product" (
	"ID"	INTEGER,
	"Name"	TEXT NOT NULL,
	"Price"	REAL NOT NULL,
	"Quantity"	INTEGER NOT NULL,
	"Status"	TEXT,
	"SID"	INTEGER,
	"Colour"	TEXT,
	PRIMARY KEY("ID"),
	FOREIGN KEY("SID") REFERENCES "Supplier"("SID")
);
CREATE TABLE IF NOT EXISTS "Supplier" (
	"SID"	INTEGER,
	PRIMARY KEY("SID"),
	FOREIGN KEY("SID") REFERENCES "Person"("ID")
);
CREATE TABLE IF NOT EXISTS "User" (
	"UID"	INTEGER,
	"Email"	TEXT,
	"Password"	TEXT,
	"Type"	TEXT,
	"Salary"	REAL,
	PRIMARY KEY("UID"),
	FOREIGN KEY("UID") REFERENCES "Person"("ID")
);

COMMIT;

CREATE TRIGGER Calc_OrderItem_Total
AFTER INSERT ON OrderItem
FOR EACH ROW
BEGIN
    -- تحديث Total_Price للـ OrderItem
    UPDATE OrderItem
    SET Total_Price = NEW.Desired_Quantity * (SELECT Price FROM Product WHERE ID = NEW.PID)
    WHERE PID = NEW.PID AND OID = NEW.OID;

    -- تحديث Total_Price للـ Order
    UPDATE Orders
    SET Total_Price = (SELECT SUM(Total_Price) FROM OrderItem WHERE OID = NEW.OID)
    WHERE ID = NEW.OID;

    -- تحديث Calculated_Price
    UPDATE Orders
    SET Calculated_Price = Total_Price -( Total_Price * Discount / 100) 
    WHERE ID = NEW.OID;
END;
CREATE TRIGGER CheckPaymentMethod
BEFORE INSERT ON Orders
FOR EACH ROW
BEGIN
    SELECT
        CASE
            WHEN UPPER(NEW.Payment_Method) NOT IN ('CASH', 'CREDIT')
            THEN RAISE(ABORT, 'Invalid payment method! Must be Cash or Credit.')
        END;
END;
CREATE TRIGGER Check_Product_Stock
BEFORE INSERT ON OrderItem
FOR EACH ROW
BEGIN
    SELECT 
        CASE
            WHEN (SELECT Quantity FROM Product WHERE ID = NEW.PID) < NEW.Desired_Quantity
            THEN RAISE(ABORT, 'Not enough stock for this product!')
        END;
END;
CREATE TRIGGER Prevent_ProductPrice_Change
BEFORE UPDATE OF Price ON Product
FOR EACH ROW
BEGIN
    SELECT
        CASE
            WHEN EXISTS (SELECT 1 FROM OrderItem WHERE PID = OLD.ID)
            THEN RAISE(ABORT, 'Cannot change product price after it has been ordered!')
        END;
END;
CREATE TRIGGER ReduceProductQuantity
AFTER INSERT ON OrderItem
FOR EACH ROW
BEGIN
    UPDATE Product
    SET Quantity = Quantity - NEW.Desired_Quantity
    WHERE ID = NEW.PID;
END;
CREATE TRIGGER UpdateProductStatus
AFTER UPDATE OF Quantity ON Product
FOR EACH ROW
BEGIN
    UPDATE Product
    SET Status = CASE 
                    WHEN NEW.Quantity = 0 THEN 'Unavailable'
                    ELSE 'Available'
                 END
    WHERE ID = NEW.ID;
END;
CREATE TRIGGER auto_delete_subtypes
AFTER DELETE ON Person
BEGIN
    DELETE FROM Customer WHERE CID = OLD.ID;
    DELETE FROM Supplier WHERE SID = OLD.ID;
    DELETE FROM User WHERE UID = OLD.ID;
END;
CREATE TRIGGER auto_insert_from_person
AFTER INSERT ON Person
BEGIN
    INSERT INTO Customer (CID)
    SELECT NEW.ID
    WHERE NEW.Type = 'CUSTOMER';
    INSERT INTO Supplier (SID)
    SELECT NEW.ID
    WHERE NEW.Type = 'SUPPLIER';
    INSERT INTO User (UID)
    SELECT NEW.ID
    WHERE NEW.Type = 'USER';

END;
CREATE TRIGGER auto_user_type_update
AFTER UPDATE OF Type ON User
BEGIN
    INSERT INTO Admin (AID)
    SELECT NEW.UID
    WHERE NEW.Type = 'ADMIN';
    INSERT INTO Cashier (CAID)
    SELECT NEW.UID
    WHERE NEW.Type ='CASHIER';
END;
CREATE TRIGGER Prevent_Duplicate_person
BEFORE INSERT ON  Person
FOR EACH ROW
BEGIN
    SELECT CASE
        WHEN EXISTS (
            SELECT 1 FROM Person
            WHERE Contact_Info = NEW.Contact_Info
        )
        THEN RAISE(ABORT, 'User with this Email already exists!')
    END;
END;

-- Prevent duplicate Product Name
CREATE TRIGGER IF NOT EXISTS prevent_duplicate_product_name
BEFORE INSERT ON Product
FOR EACH ROW
BEGIN
    SELECT CASE
        WHEN EXISTS (SELECT 1 FROM Product WHERE Name = NEW.Name) THEN
            RAISE(ABORT, 'Product Name already exists!')
    END;
END;

-- Prevent inserting Product with non-existent Supplier
CREATE TRIGGER IF NOT EXISTS prevent_product_without_supplier
BEFORE INSERT ON Product
FOR EACH ROW
BEGIN
    SELECT CASE
        WHEN NEW.SID IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Supplier WHERE SID = NEW.SID) THEN
            RAISE(ABORT, 'Supplier does not exist!')
    END;
END;

-- Prevent updating Product with non-existent Supplier
CREATE TRIGGER IF NOT EXISTS prevent_product_without_supplier_update
BEFORE UPDATE ON Product
FOR EACH ROW
WHEN NEW.SID IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Supplier WHERE SID = NEW.SID)
BEGIN
    SELECT RAISE(ABORT, 'Supplier does not exist!');
END;
COMMIT;
