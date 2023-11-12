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
  cardNumber int NOT NULL PRIMARY KEY,
  expiryDate int NOT NULL,
  CVV int NOT NULL
);

CREATE TABLE User (
  userID int NOT NULL PRIMARY KEY,
  forename varchar(45) NOT NULL,
  surnmane varchar(45) NOT NULL,
  email varchar(45) NOT NULL,
  roles int NOT NULL,
  `password` varchar(45) NOT NULL,
  houseNumber varchar(10) NOT NULL,
  postCode varchar(10) NOT NULL,
  cardNumber int NOT NULL,
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
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE ControllerTrainSetLink (
  trainSetCode varchar(5) NOT NULL PRIMARY KEY,
  controllerCode varchar(5) NOT NULL,
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode)
);

CREATE TABLE DccCode (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  dccCode varchar(45) NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE EraLink (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  era varchar(45) NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE LocomotiveTrainSetLink (
  trainSetCode varchar(5) NOT NULL,
  locomotiveCode varchar(5) NOT NULL,
  PRIMARY KEY (trainSetCode, locomotiveCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode),
  FOREIGN KEY (locomotiveCode) REFERENCES Product(productCode)
);

CREATE TABLE Orders (
  orderID int NOT NULL PRIMARY KEY,
  `date` date NOT NULL,
  `status` varchar(45) NOT NULL,
  userID int NOT NULL,
  FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE OrderLines (
  orderID int NOT NULL,
  productCode varchar(5) NOT NULL,
  quantity int,
  PRIMARY KEY (orderID, productCode),
  FOREIGN KEY (orderID) REFERENCES Orders(orderID),
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE RollingStockTrainSetLinkk (
  trainSetCode varchar(5) NOT NULL,
  rollingStockCode varchar(5) NOT NULL,
  PRIMARY KEY (trainSetCode, rollingStockCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode),
  FOREIGN KEY (rollingStockCode) REFERENCES Product(productCode)
);

CREATE TABLE Stock (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  stockCount int NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE TrackPacks (
  productCode varchar(5) NOT NULL PRIMARY KEY,
  isExtensionPack boolean NOT NULL,
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE TrackPackTrainSetLink (
  trainSetCode varchar(5) NOT NULL,
  trackPackCode varchar(5) NOT NULL,
  PRIMARY KEY (trainSetCode, trackPackCode),
  FOREIGN KEY (trainSetCode) REFERENCES Product(productCode),
  FOREIGN KEY (trackPackCode) REFERENCES Product(productCode)
);
