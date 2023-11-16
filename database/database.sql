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
  `password` varchar(45) NOT NULL,
  role int NOT NULL,
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

-- add test data
INSERT INTO Address VALUES ('1', 'Test Street', 'Test City', 'TE5T 1NG');
INSERT INTO CardDetail VALUES (123456789, 1234, 123);
INSERT INTO User VALUES (1, 'Test', 'User', 'testuser@test.com', 'testpassword', 2, '1', 'TE5T 1NG', 123456789);
INSERT INTO Brand VALUES (1, 'Test Brand');
INSERT INTO Product VALUES ('R001', 1, 'Track1', 1.99, 'OO', 'Track1 Description');
INSERT INTO Product VALUES ('L001', 1, 'Locomotive1', 5.99, 'OO', 'Locomotive1 Description');
INSERT INTO Product VALUES ('S001', 1, 'RollingStock1', 10.99, 'OO', 'RollingStock1 Description');
INSERT INTO Product VALUES ('C001', 1, 'Controller1', 4.99, 'OO', 'Controller1 Description');
INSERT INTO Product VALUES ('T001', 1, 'TrainSet1', 19.99, 'OO', 'TrainSet1 Description');
INSERT INTO ControllerTable VALUES ('C001', 1);
INSERT INTO ControllerTrainSetLink VALUES ('T001', 'C001');
INSERT INTO DccCode VALUES ('L001', 'DCC');
INSERT INTO EraLink VALUES ('L001', 'Era 1');
INSERT INTO LocomotiveTrainSetLink VALUES ('T001', 'L001');
INSERT INTO RollingStockTrainSetLinkk VALUES ('T001', 'S001');
INSERT INTO Stock VALUES ('R001', 10);
INSERT INTO TrackPacks VALUES ('R001', 1);
INSERT INTO TrackPackTrainSetLink VALUES ('T001', 'R001');
INSERT INTO Orders VALUES (1, '2018-01-01', 'Pending', 1);
INSERT INTO OrderLines VALUES (1, 'T001', 1);
