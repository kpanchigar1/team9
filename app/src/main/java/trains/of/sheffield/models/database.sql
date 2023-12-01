-- Resets DB

USE team009;

SET FOREIGN_KEY_CHECKS = 0;
SET GROUP_CONCAT_MAX_LEN=32768;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
  FROM information_schema.tables
  WHERE table_schema = (SELECT DATABASE());
SELECT IFNULL(@tables,'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Address (
  houseNumber varchar(10) NOT NULL,
  streetName varchar(30) NOT NULL,
  city varchar(30) NOT NULL,
  postCode varchar(10) NOT NULL,
  PRIMARY KEY (houseNumber, postCode)
);

CREATE TABLE CardDetail (
  cardName varchar(45) NOT NULL,
  cardNumber varchar(16) NOT NULL PRIMARY KEY,
  expiryDate varchar(4) NOT NULL,
  CVV varchar(4) NOT NULL
);

CREATE TABLE User (
  userID varchar(45) NOT NULL PRIMARY KEY,
  forename varchar(45) NOT NULL,
  surname varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  passwordHash varchar(64) NOT NULL,
  role int NOT NULL,
  houseNumber varchar(10) NOT NULL,
  postCode varchar(10) NOT NULL,
  cardNumber varchar(16),
  FOREIGN KEY (houseNumber, postCode) REFERENCES Address(houseNumber, postCode),
  FOREIGN KEY (cardNumber) REFERENCES CardDetail(cardNumber)
);

CREATE TABLE Brand (
  brandID int NOT NULL PRIMARY KEY,
  brandName varchar(45) NOT NULL
);

CREATE TABLE Product (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  brandID int NOT NULL,
  productName varchar(45) NOT NULL,
  retailPrice double NOT NULL,
  gauge varchar(2) NOT NULL,
  `description` varchar(45) NOT NULL,
  FOREIGN KEY (brandID) REFERENCES Brand(brandID)
);

CREATE TABLE ControllerTable (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  isAnalogue boolean NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE ControllerTrainSetLink (
trainSetCode varchar(5) NOT NULL,
controllerCode varchar(5) NOT NULL,
quantity int NOT NULL DEFAULT 1,
PRIMARY KEY (trainSetCode, controllerCode),
FOREIGN KEY (trainSetCode) REFERENCES Product(productCode) ON DELETE CASCADE,
FOREIGN KEY (controllerCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE EraLink (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  era varchar(45) NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE LocomotiveTrainSetLink (
  trainSetCode varchar(5) NOT NULL,
  locomotiveCode varchar(5) NOT NULL,
  quantity int NOT NULL DEFAULT 1,
  PRIMARY KEY (trainSetCode, locomotiveCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode) ON DELETE CASCADE,
  FOREIGN KEY (locomotiveCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE Orders (
  orderID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `date` timestamp NOT NULL,
  `status` int NOT NULL,
  userID VARCHAR(45) NOT NULL,
  totalPrice double NOT NULL,
  FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE OrderLines (
  orderID int NOT NULL,
  productCode varchar(5) NOT NULL,
  quantity int,
  PRIMARY KEY (orderID, productCode),
  FOREIGN KEY (orderID) REFERENCES Orders(orderID) ON DELETE CASCADE,
  FOREIGN KEY (productCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE RollingStockTrainSetLink (
  trainSetCode varchar(5) NOT NULL,
  rollingStockCode varchar(5) NOT NULL,
  quantity int NOT NULL DEFAULT 1,
  PRIMARY KEY (trainSetCode, rollingStockCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode) ON DELETE CASCADE,
  FOREIGN KEY (rollingStockCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE Stock (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  stockCount int NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE TrackPacks (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  isExtensionPack boolean NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

CREATE TABLE TrackPackTrainSetLink (
  trainSetCode varchar(5) NOT NULL,
  trackPackCode varchar(5) NOT NULL,
  quantity int NOT NULL DEFAULT 1,
  PRIMARY KEY (trainSetCode, trackPackCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode) ON DELETE CASCADE,
  FOREIGN KEY (trackPackCode) REFERENCES Product(productCode) ON DELETE CASCADE
);

-- add test data
INSERT INTO Brand VALUES (1, 'Hornby');
INSERT INTO Brand VALUES (2, 'Dapol');
-- Inserting test data for boxed sets
INSERT INTO Product VALUES ('M002', 1, 'Mallard Record Breaker Set', 249.99, 'OO', 'Iconic steam train set');
INSERT INTO Product VALUES ('M003', 2, 'Flying Scotsman Set', 219.99, 'OO', 'Classic locomotive train set');
-- Add corresponding entries to other related tables if needed
INSERT INTO EraLink VALUES ('M002', 'Era 3');
INSERT INTO EraLink VALUES ('M003', 'Era 3-4');
-- Inserting test data for track packs
INSERT INTO Product VALUES ('P002', 1, 'Track Pack B', 39.99, 'OO', 'Advanced track expansion pack');
INSERT INTO Product VALUES ('P003', 1, '3rd Radius Extension Pack', 49.99, 'OO', 'Extended curved track pack');
INSERT INTO TrackPacks VALUES ('P002', 0);
INSERT INTO TrackPacks VALUES ('P003', 1);
-- Add corresponding entries to other related tables if needed

-- Inserting test data for controllers
INSERT INTO Product VALUES ('C004', 1, 'DCC Elite Controller', 99.99, 'OO', 'Advanced digital controller');
INSERT INTO Product VALUES ('C005', 1, 'Standard Controller', 29.99, 'OO', 'Basic analogue controller');
INSERT INTO ControllerTable VALUES ('C004', 0);
INSERT INTO ControllerTable VALUES ('C005', 1);
-- Add corresponding entries to other related tables if needed

-- Inserting test data for additional locomotives
INSERT INTO Product VALUES ('L005', 1, 'Class 5MT Black Five', 79.99, 'OO', 'Steam locomotive for Era 3-4');
INSERT INTO Product VALUES ('L006', 1, 'Class 35 Hymek Diesel', 89.99, 'OO', 'Diesel locomotive for Era 5-6');
INSERT INTO EraLink VALUES ('L005', 'Era 3');
INSERT INTO EraLink VALUES ('L006', 'Era 4-5');
-- Add corresponding entries to other related tables if needed

-- Inserting test data for additional carriages
INSERT INTO Product VALUES ('S006', 1, 'GWR Toad Guards Van', 22.99, 'OO', 'Historic brake carriage');
INSERT INTO Product VALUES ('S007', 1, 'Mark 1 (maroon) Corridor First', 32.99, 'OO', 'Classic first-class carriage');
INSERT INTO EraLink VALUES ('S006', 'Era 2');
INSERT INTO EraLink VALUES ('S007', 'Era 3');
-- Add corresponding entries to other related tables if needed

-- Inserting test data for additional wagons
INSERT INTO Product VALUES ('S003', 1, 'Coalfish Open Wagon', 14.99, 'OO', 'Coal transportation wagon');
INSERT INTO Product VALUES ('S004', 1, '21t Iron Ore Tippler', 19.99, 'OO', 'Ore transportation wagon');
INSERT INTO EraLink VALUES ('S003', 'Era 3');
INSERT INTO EraLink VALUES ('S004', 'Era 4');
-- Add corresponding entries to other related tables if needed
-- Linking locomotives to train sets
INSERT INTO LocomotiveTrainSetLink VALUES ('M002', 'L005', 1);
INSERT INTO LocomotiveTrainSetLink VALUES ('M003', 'L006', 1);

-- Linking rolling stock to train sets
INSERT INTO RollingStockTrainSetLink VALUES ('M002', 'S004', 1);
INSERT INTO RollingStockTrainSetLink VALUES ('M002', 'S006', 1);
INSERT INTO RollingStockTrainSetLink VALUES ('M003', 'S003', 1);
INSERT INTO RollingStockTrainSetLink VALUES ('M003', 'S007', 1);

-- Linking controllers to train sets
INSERT INTO ControllerTrainSetLink VALUES ('M002', 'C004', 1);
INSERT INTO ControllerTrainSetLink VALUES ('M003', 'C005', 1);

-- Linking track packs to train sets
INSERT INTO TrackPackTrainSetLink VALUES ('M002', 'P002', 1);
INSERT INTO TrackPackTrainSetLink VALUES ('M003', 'P003', 1);

INSERT INTO Stock VALUES ('M002', 10);
INSERT INTO Stock VALUES ('M003', 10);
INSERT INTO Stock VALUES ('P002', 10);
INSERT INTO Stock VALUES ('P003', 10);
INSERT INTO Stock VALUES ('C004', 10);
INSERT INTO Stock VALUES ('C005', 10);
INSERT INTO Stock VALUES ('L005', 10);
INSERT INTO Stock VALUES ('L006', 10);
INSERT INTO Stock VALUES ('S003', 10);
INSERT INTO Stock VALUES ('S004', 10);
INSERT INTO Stock VALUES ('S006', 10);
INSERT INTO Stock VALUES ('S007', 10);

INSERT INTO User Values('29e58b39-22e7-429d-b900-72aefcb28f61', 'Manager', '1', 'manager@example.com', '2932ee97197e7f618e08cfb106f3f9da7bae0eb9a679c0826af23cdbef520f54', 0, '1', 'S1 1AA', NULL);