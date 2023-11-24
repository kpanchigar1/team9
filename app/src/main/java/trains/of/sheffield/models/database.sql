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
  cardNumber varchar(16) NOT NULL PRIMARY KEY,
  cardName varchar(45) NOT NULL,
  expiryDate int NOT NULL,
  CVV int NOT NULL
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
  orderID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `date` date NOT NULL,
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
  FOREIGN KEY (orderID) REFERENCES Orders(orderID),
  FOREIGN KEY (productCode) REFERENCES Product(productCode)
);

CREATE TABLE RollingStockTrainSetLink (
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
INSERT INTO Brand VALUES (1, 'Test Brand');
INSERT INTO Product VALUES ('R001', 1, 'Track1', 1.99, 'OO', 'Track1 Description');
INSERT INTO Product VALUES ('L001', 1, 'Locomotive1', 5.99, 'OO', 'Locomotive1 Description');
INSERT INTO Product VALUES ('S001', 1, 'RollingStock1', 10.99, 'OO', 'RollingStock1 Description');
INSERT INTO Product VALUES ('C001', 1, 'Controller1', 4.99, 'OO', 'Controller1 Description');
INSERT INTO Product VALUES ('M001', 1, 'TrainSet1', 19.99, 'OO', 'TrainSet1 Description');
INSERT INTO Product VALUES ('P001', 1, 'TrackPack1', 9.99, 'OO', 'TrackPack1 Description');
INSERT INTO ControllerTable VALUES ('C001', 1);
INSERT INTO ControllerTrainSetLink VALUES ('M001', 'C001');
INSERT INTO DccCode VALUES ('L001', 'DCC');
INSERT INTO EraLink VALUES ('L001', 'Era 1');
INSERT INTO LocomotiveTrainSetLink VALUES ('M001', 'L001');
INSERT INTO RollingStockTrainSetLink VALUES ('M001', 'S001');
INSERT INTO Stock VALUES ('R001', 10);
INSERT INTO Stock VALUES ('L001', 10);
INSERT INTO Stock VALUES ('S001', 10);
INSERT INTO Stock VALUES ('C001', 10);
INSERT INTO Stock VALUES ('M001', 10);
INSERT INTO Stock VALUES ('P001', 10);
INSERT INTO TrackPacks VALUES ('R001', 1);
INSERT INTO TrackPackTrainSetLink VALUES ('M001', 'R001');
INSERT INTO Address VALUES ('498', 'Glossop Road', 'Sheffield', 'S10 2QA');
INSERT INTO User VALUES ('d0f89dff-5293-49d6-8342-0cd9fc035b60', 'Gabriel', 'Plume', 'gplume02@gmail.com', '602557fd40b0dbffac5bf6efbd70652a459aa727ffad5788dff9f0e679e93c07', '2', '498', 'S10 2QA', NULL);
INSERT INTO CardDetail VALUES ('1234567891011121', 'Visa', 1234, 123);
INSERT INTO Address VALUES ('46', 'Harefield Road', 'Sheffield', 'S11 8NU');
INSERT INTO User VALUES ('46760aad-4326-4e9d-a824-1b8cb59347f9', 'Krish', 'Panchigar', 'krishpanchigar14@gmail.com', '1422616620215fe03b1994505eb303c860441455ef36f5fc127d3de90baabd2d', 0, '46', 'S11 8NU', 1234567891011121);
INSERT INTO Orders VALUES (1, '2020-12-01', 2, '46760aad-4326-4e9d-a824-1b8cb59347f9', 19.99);
INSERT INTO OrderLines VALUES (1, 'M001', 1);
