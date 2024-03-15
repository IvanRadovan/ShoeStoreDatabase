
DROP DATABASE IF EXISTS ShoeStore;
CREATE DATABASE ShoeStore;
USE ShoeStore;

set sql_safe_updates = 0;
ALTER USER 'root'@'localhost' IDENTIFIED BY '12345';

CREATE TABLE Customer (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          personalNumber CHAR(10) NOT NULL UNIQUE,
                          userPassword VARCHAR(100) NOT NULL,
                          firstName VARCHAR(100) NOT NULL,
                          lastName VARCHAR(100) NOT NULL,
                          phoneNumber CHAR(10) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          address VARCHAR(100) NOT NULL,
                          postalCode CHAR(5) NOT NULL,
                          city VARCHAR(100) NOT NULL,
                          created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ShoePrice (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           swedishFigure DOUBLE NOT NULL UNIQUE,
                           created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Color (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       singleColor VARCHAR(50) NOT NULL UNIQUE,
                       created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ShoeSize (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          euroSize DOUBLE NOT NULL UNIQUE,
                          created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ShoeBrand (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           brand VARCHAR(100) NOT NULL UNIQUE,
                           created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Shoe (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      brandId INT NOT NULL,
                      sizeId INT NOT NULL,
                      priceId INT NOT NULL,
                      stock INT DEFAULT 0 CHECK (stock > 0),
                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                      FOREIGN KEY (brandId) REFERENCES ShoeBrand(id),											-- |
                      FOREIGN KEY (sizeId) REFERENCES ShoeSize(id),											-- | Referensintegriteterna är DEFAULT för att möjliggöra försäljning av kvarvarande skomärken och för att bevara historisk data.
                      FOREIGN KEY (priceId) REFERENCES ShoePrice(id)											-- |
);

CREATE TABLE Category (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          categoryName VARCHAR(100) NOT NULL UNIQUE,
                          created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Purchase (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          customerId INT,
                          orderDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          FOREIGN KEY (customerId) REFERENCES Customer(id) ON DELETE SET NULL						-- Säkerställer referensintegritet för att behålla data om beställningen samt uppfylla GDPR-kraven (ON DELETE SET NULL).
);

CREATE TABLE ShoeCategoryRelation (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      shoeId INT NOT NULL,
                                      categoryId INT NOT NULL,
                                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                                      UNIQUE KEY uniqueCombination (shoeId, categoryId),										-- Inga dubbletter tillåtna, överflödig datalagring.
                                      FOREIGN KEY (shoeId) REFERENCES Shoe(id) ON DELETE CASCADE,  							-- Säkerställer referensintegritet, tar bort relationen om skodatan saknas (DELETE CASCADE).
                                      FOREIGN KEY (categoryId) REFERENCES Category(id) ON DELETE CASCADE 				 		-- Säkerställer referensintegritet, tar bort relationen om kategoridata saknas (DELETE CASCADE).
);

CREATE TABLE ShoeColorRelation (
                                   id INT AUTO_INCREMENT PRIMARY KEY,
                                   shoeId INT NOT NULL,
                                   colorId INT NOT NULL,
                                   created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                                   UNIQUE KEY uniqueCombination (shoeId, colorId),											-- Inga dubbletter tillåtna, överflödig datalagring.
                                   FOREIGN KEY (shoeId) REFERENCES Shoe(id) ON DELETE CASCADE,  							-- Säkerställer referensintegritet, tar bort relationen om skodatan saknas (DELETE CASCADE).
                                   FOREIGN KEY (colorId) REFERENCES Color(id) ON DELETE CASCADE 							-- Säkerställer referensintegritet, tar bort relationen om färgdatan saknas (DELETE CASCADE).
);

CREATE TABLE ShoePurchaseRelation (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      shoeId INT NOT NULL,
                                      purchaseId INT NOT NULL,
                                      quantity INT NOT NULL,
                                      created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                                      UNIQUE KEY uniqueCombination (shoeId, purchaseId),										-- Inga dubbletter tillåtna, datat ska komprimeras i sko antalet.
                                      FOREIGN KEY (shoeId) REFERENCES Shoe(id),
                                      FOREIGN KEY (purchaseId) REFERENCES Purchase(id)
);

CREATE INDEX IX_FirstName ON Customer(firstName); 					 						-- Indexeras för snabb kundsökning, särskilt användbart vid hantering av kundärenden med flera personer med samma namn.

INSERT INTO Customer (personalNumber, userPassword, firstName, lastName, phoneNumber, email, address, postalCode, city) VALUES
                                                                                                                            ('9105056621', '123', 'Kalle', 'Åkesson', 0702325000, 'kalle.akesson@hotmail.com', 'Sveavägen 97', '12743', 'Stockholm'),
                                                                                                                            ('8501014321', '123', 'Jimmie', 'Nilsson', 0761239889, 'jimmie.nilsson@gmail.com', 'Sturegatan 4', '12040', 'Borlänge'),
                                                                                                                            ('7710245050', '123', 'Julia', 'Lind', 0739871221, 'julia.lind@yahoo.com', 'Olaus Magnus Väg 66', '12745', 'Johanneshov'),
                                                                                                                            ('0012043289', '123', 'Kalle', 'Andresson', 0707051010, 'niklas.andresson@hotmail.com', 'Solandergatan 52', '12145', 'Johanneshov'),
                                                                                                                            ('9505051234', '123', 'Ann-Marie', 'Johansson', 0732631339, 'ann_marie.johansson@hotmail.com', 'Solnavägen 103', '12033', 'Solna');

INSERT INTO Category (categoryName) VALUES
                                        ('Damskor'), ('Herrskor'),
                                        ('Barnskor'), ('Lyxskor'),
                                        ('Löparskor'), ('Damstövlar'),
                                        ('Sandaler'), ('Tofflor');

INSERT INTO Color (singleColor) VALUES
                                    ('Röd'), ('Grön'), ('Svart'),
                                    ('Orange'), ('Brun'), ('Vit'),
                                    ('Blå'), ('Lila'), ('Rosa');

INSERT INTO ShoeBrand (brand) VALUES
                                  ('ECCO'), ('Nike'), ('Puma'),
                                  ('Clarks'), ('Louis Vuitton'), ('Reebok'),
                                  ('Gucci'), ('Brooks'), ('Vans');

INSERT INTO ShoePrice (swedishFigure) VALUES
                                          (595.95), (795.95), (495.95), (499.99),
                                          (995.95), (1295.95), (1195.50), (2995.95);

INSERT INTO ShoeSize (euroSize) VALUES
                                    (36.0), (38.0), (39.0), (40.0),
                                    (40.5), (41.0), (42.0), (43.0);

INSERT INTO Shoe (brandId, sizeId, priceId, stock) VALUES
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Louis Vuitton'), (SELECT id FROM ShoeSize WHERE euroSize = 38.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 2995.95), (12)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Nike'), (SELECT id FROM ShoeSize WHERE euroSize = 40.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 595.95), (10)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Puma'), (SELECT id FROM ShoeSize WHERE euroSize = 40.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 495.95), (31)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Reebok'), (SELECT id FROM ShoeSize WHERE euroSize = 40.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 595.95), (10)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Reebok'), (SELECT id FROM ShoeSize WHERE euroSize = 40.5), (SELECT id FROM ShoePrice WHERE swedishFigure = 995.95), (50)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Reebok'), (SELECT id FROM ShoeSize WHERE euroSize = 42.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 795.95), (50)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'ECCO'), (SELECT id FROM ShoeSize WHERE euroSize = 38.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 995.95), (50)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'Gucci'), (SELECT id FROM ShoeSize WHERE euroSize = 41.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 1195.50), (8)),
                                                       ((SELECT id FROM ShoeBrand WHERE brand = 'ECCO'), (SELECT id FROM ShoeSize WHERE euroSize = 38.0), (SELECT id FROM ShoePrice WHERE swedishFigure = 499.99), (20));

INSERT INTO Purchase (customerId) VALUES
                                      ((SELECT id FROM Customer WHERE personalNumber = '9105056621')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '9105056621')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '9105056621')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '8501014321')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '7710245050')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '9505051234')),
                                      ((SELECT id FROM Customer WHERE personalNumber = '9505051234'));

INSERT INTO ShoeCategoryRelation (shoeId, categoryId) VALUES
                                                          ((1), (1)),			 -- Louis Vuitton | Damskor
                                                          ((1), (4)), 		 -- Louis Vuitton | Lyxskor
                                                          ((2), (2)),			 -- Nike 		  | Herrskor
                                                          ((2), (1)),			 -- Nike 		  | Damskor
                                                          ((2), (5)), 		 -- Nike 		  | Löparskor
                                                          ((3), (3)), 		 -- Puma 		  | Barnskor
                                                          ((4), (3)), 		 -- Reebok		  | Barnskor
                                                          ((5), (1)),			 -- Reebok		  | Damskor
                                                          ((6), (2)),			 -- Reebok		  | Herrskor
                                                          ((6), (5)), 		 -- Reebok		  | Löparskor
                                                          ((7), (2)),			 -- ECCO		  | Herrskor
                                                          ((7), (7)),			 -- ECCO		  | Sandaler
                                                          ((8), (1)),			 -- Gucci		  | Damskor
                                                          ((8), (6)),			 -- Gucci		  | Stövlar
                                                          ((9), (7));			 -- ECCO		  | Sandaler

INSERT INTO ShoeColorRelation (shoeId, colorId) VALUES
                                                    ((1), (3)),			 -- Louis Vuitton | Svart
                                                    ((1), (6)), 		 -- Louis Vuitton | Vit
                                                    ((1), (8)),			 -- Louis Vuitton | Lila
                                                    ((2), (1)),			 -- Nike 		  | Röd
                                                    ((2), (6)), 		 -- Nike 		  | Vit
                                                    ((3), (7)), 		 -- Puma 		  | Blå
                                                    ((4), (4)), 		 -- Reebok		  | Orange
                                                    ((5), (9)),			 -- Reebok		  | Rosa
                                                    ((6), (2)),			 -- Reebok		  | Grön
                                                    ((6), (6)), 		 -- Reebok		  | Vit
                                                    ((7), (3)),			 -- ECCO		  | Svart
                                                    ((8), (3)),			 -- Gucci		  | Svart
                                                    ((8), (8)),			 -- Gucci		  | Lila
                                                    ((9), (2)),			 -- ECCO		  | Grön
                                                    ((9), (6));			 -- ECCO		  | Vit

INSERT INTO ShoePurchaseRelation (shoeId, purchaseId, quantity) VALUES
                                                                    ((2), (1), (1)),		-- Nike   		 | Kalle Åkesson
                                                                    ((6), (2), (1)),		-- Reebok 		 | Kalle Åkesson
                                                                    ((7), (3), (1)),		-- ECCO   		 | Kalle Åkesson
                                                                    ((3), (4), (1)),		-- Puma			 | Jimmie Nilsson
                                                                    ((9), (4), (1)),		-- ECCO			 | Jimmie Nilsson
                                                                    ((1), (5), (3)),		-- Louis Vuitton | Julia Lind
                                                                    ((8), (6), (2)),		-- Gucci		 | Ann-Marie Johansson
                                                                    ((7), (6), (3)),		-- ECCO		 	 | Ann-Marie Johansson
                                                                    ((7), (7), (1));		-- ECCO		 	 | Ann-Marie Johansson

/*-------------------------------------------------------------------------
Vilka kunder har köpt svarta sandaler i storlek 38 av märket Ecco? Lista deras namn och använd inga hårdkodade id-nummer i din fråga.
-------------------------------------------------------------------------*/
SELECT Customer.firstName AS Forename, Customer.lastName AS Surname
FROM Customer
         INNER JOIN Purchase ON Purchase.customerId = Customer.id
         INNER JOIN ShoePurchaseRelation ON ShoePurchaseRelation.purchaseId = Purchase.id
         INNER JOIN Shoe ON ShoePurchaseRelation.shoeId = Shoe.id
         INNER JOIN ShoeBrand ON Shoe.brandId = ShoeBrand.id
         INNER JOIN ShoeCategoryRelation ON ShoeCategoryRelation.shoeId = Shoe.id
         INNER JOIN Category ON ShoeCategoryRelation.categoryId = Category.id
         INNER JOIN ShoeSize ON Shoe.sizeId = ShoeSize.id
         INNER JOIN ShoeColorRelation ON ShoeColorRelation.shoeId = Shoe.id
         INNER JOIN Color ON ShoeColorRelation.colorId = Color.id
WHERE Category.categoryName = 'Sandaler'
  AND ShoeSize.euroSize = 38
  AND ShoeBrand.brand = 'ECCO'
  AND Color.singleColor = 'Svart'
GROUP BY Forename, Surname
ORDER BY Forename, Surname;

/*-------------------------------------------------------------------------
Lista antalet produkter per kategori.
Listningen ska innehålla kategori-namn och antalet produkter.
-------------------------------------------------------------------------*/
SELECT Category.categoryName AS Category, COUNT(Shoe.id) AS Quantity
FROM Category
         LEFT JOIN ShoeCategoryRelation ON ShoeCategoryRelation.categoryId = Category.id
         LEFT JOIN Shoe ON ShoeCategoryRelation.shoeId = Shoe.id
GROUP BY Category;

/*-------------------------------------------------------------------------
Skapa en kundlista med den totala summan pengar som varje kund har handlat för.
Kundensför- och efternamn, samt det totala värdet som varje person har shoppats för, skall visas.
-------------------------------------------------------------------------*/
SELECT
    Customer.firstName AS Forename,
    Customer.lastName AS Surname,
    SUM(ShoePrice.swedishFigure * ShoePurchaseRelation.quantity) AS Total
FROM Customer
         INNER JOIN Purchase ON Purchase.customerId = Customer.id
         INNER JOIN ShoePurchaseRelation ON ShoePurchaseRelation.purchaseId = Purchase.id
         INNER JOIN Shoe ON ShoePurchaseRelation.shoeId = Shoe.id
         INNER JOIN ShoePrice ON Shoe.priceId = ShoePrice.id
GROUP BY Forename, Surname
ORDER BY Total DESC, Forename , Surname;

/*-------------------------------------------------------------------------
Skriv ut en lista på det totala beställningsvärdet per ort där beställningsvärdet är större än1000 kr. Ortnamn och värde ska visas.
(det måste finnas orter i databasen där det har handlats för mindre än 1000 kr för att visa att frågan är korrekt formulerad)
-------------------------------------------------------------------------*/
SELECT
    Customer.city AS City,
    SUM(ShoePrice.swedishFigure * ShoePurchaseRelation.quantity) AS Total
FROM Customer
         INNER JOIN Purchase ON Purchase.customerId = Customer.id
         INNER JOIN ShoePurchaseRelation ON ShoePurchaseRelation.purchaseId = Purchase.id
         INNER JOIN Shoe ON ShoePurchaseRelation.shoeId = Shoe.id
         INNER JOIN ShoePrice ON Shoe.priceId = ShoePrice.id
GROUP BY City
HAVING (Total > 1000)
ORDER BY Total DESC, City DESC;

/*-------------------------------------------------------------------------
Skapa en topp-5 lista av de mest sålda produkterna.
-------------------------------------------------------------------------*/
SELECT ShoeBrand.brand as Brand, SUM(ShoePurchaseRelation.quantity) Sold
FROM ShoeBrand
         INNER JOIN Shoe ON Shoe.brandId = ShoeBrand.id
         INNER JOIN ShoePurchaseRelation ON ShoePurchaseRelation.shoeId = Shoe.id
         INNER JOIN Purchase ON ShoePurchaseRelation.purchaseId = Purchase.id
         INNER JOIN ShoePrice ON Shoe.priceId = ShoePrice.id
GROUP BY Brand
ORDER BY Sold DESC
    LIMIT 5;

/*-------------------------------------------------------------------------
Vilken månad hade du den största försäljningen?
(det måste finnas data som anger försäljning för mer än en månad i databasen för att visa att frågan är korrekt formulerad)
-------------------------------------------------------------------------*/
UPDATE Purchase
SET orderDateTime = '2023-11-27'
WHERE Purchase.id = 6;
SELECT * FROM Purchase;
-- -------------------------------------------------------------------------------
SELECT
    DATE_FORMAT(Purchase.orderDateTime, '%Y') AS Date_Year,
    DATE_FORMAT(Purchase.orderDateTime, '%M') AS Date_Month,
    SUM(ShoePrice.swedishFigure * ShoePurchaseRelation.quantity) AS Total
FROM Purchase
         INNER JOIN ShoePurchaseRelation ON ShoePurchaseRelation.purchaseId = Purchase.id
         INNER JOIN Shoe ON ShoePurchaseRelation.shoeId = Shoe.id
         INNER JOIN ShoePrice ON Shoe.priceId = ShoePrice.id
GROUP BY Date_Year, Date_Month
ORDER BY Total DESC
    LIMIT 1;

SELECT * FROM Purchase;
SELECT * FROM Shoe;
SELECT * FROM ShoePurchaseRelation;
DELIMITER //
CREATE PROCEDURE AddToCart(
    pCustomerId INT,
    pPurchaseId INT,
    pShoeId INT,
    pQuantity INT)
BEGIN

		DECLARE customerCount int default -1;
        DECLARE purchaseCount int default -1;
        DECLARE shoeCount int default -1;
		DECLARE shoeStock INT DEFAULT -1;
        DECLARE shoeAlreadyInPurchase INT DEFAULT -1;

		DECLARE EXIT HANDLER FOR 3819
BEGIN
ROLLBACK;
RESIGNAL SET MESSAGE_TEXT = 'Requested quantity exceeds available stock, rollback initiated.';
END;

        DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
RESIGNAL SET MESSAGE_TEXT = 'An exception was caught, rollback initiated.';
END;

START TRANSACTION;

SELECT COUNT(id) INTO customerCount FROM Customer WHERE id = pCustomerId;
SELECT COUNT(id) INTO purchaseCount FROM Purchase WHERE id = pPurchaseId;
SELECT COUNT(id) INTO shoeCount FROM Shoe WHERE id = pShoeId;
SELECT stock INTO shoeStock FROM Shoe WHERE id = pShoeId;
SELECT id INTO shoeAlreadyInPurchase FROM ShoePurchaseRelation WHERE purchaseId = pPurchaseId AND shoeId = pShoeId;

IF (customerCount > 0 AND shoeCount > 0 AND pQuantity > 0 AND shoeStock > 0) THEN
			IF (purchaseCount = 0) THEN
				INSERT INTO Purchase(customerId) VALUES (pCustomerId);
INSERT INTO ShoePurchaseRelation(shoeId, purchaseId, quantity) VALUES (pShoeId, LAST_INSERT_ID(), pQuantity);
ELSE
				IF EXISTS (SELECT * FROM ShoePurchaseRelation WHERE id = shoeAlreadyInPurchase) THEN
UPDATE ShoePurchaseRelation SET quantity = (quantity + pQuantity) WHERE id = shoeAlreadyInPurchase;
ELSE
					INSERT INTO ShoePurchaseRelation(shoeId, purchaseId, quantity) VALUES (pShoeId, pPurchaseId, pQuantity);
END IF;
END IF;
UPDATE Shoe SET stock = (shoeStock - pQuantity) WHERE id = pShoeId;
END IF;
COMMIT;
END //
DELIMITER ;
call AddToCart(5, null, 1, 2); 	 	-- Ny beställning
-- call AddToCart(5, 7, 1, 2);		 	-- Om beställningen redan finns ska produkten läggas till i beställningen
-- call AddToCart(3, 5, 1, 2);			-- Om beställningen finns och produkten redan finns i den ska vi lägga till ytterligare
SELECT * FROM Shoe;
SELECT * FROM Purchase;
SELECT * FROM ShoePurchaseRelation;
/*
pCustomerId INT,
pPurchaseId INT,
pShoeId INT,
pQuantity INT)
*/



