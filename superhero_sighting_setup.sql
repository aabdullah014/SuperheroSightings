DROP DATABASE IF EXISTS superheroSightingDB;
CREATE DATABASE superheroSightingDB;

USE superheroSightingDB;

CREATE TABLE location(
	
    locationId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `coordinates` VARCHAR(50),
    `description` VARCHAR(250)

);

CREATE TABLE power(
	
	powerId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(25) NOT NULL,
    `description` VARCHAR(100) NOT NULL
    
);

CREATE TABLE hero(

	heroId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `powerId` INT NOT NULL,
    FOREIGN KEY (powerId) REFERENCES power(powerId),
    `description` VARCHAR(250)

);

CREATE TABLE org(

	orgId INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(75) NOT NULL,
    `description` VARCHAR(250) NOT NULL,
    `phone` VARCHAR(25) NOT NULL,
    `locationId` INT NOT NULL,
    FOREIGN KEY (locationId) REFERENCES location(locationId)

);

CREATE TABLE heroOrg(
	`heroId` INT NOT NULL,
    `orgId` INT NOT NULL,
    PRIMARY KEY pk_heroOrg (heroId, orgId),
    FOREIGN KEY fk_heroOrg_hero (heroId)
		REFERENCES hero(heroId),
	FOREIGN KEY fk_heroOrg_org (orgId)
		REFERENCES org(orgId)

);

CREATE TABLE sighting(

	`sightingId` INT PRIMARY KEY AUTO_INCREMENT,
    `heroId` INT NOT NULL,
    `locationId` INT NOT NULL,
    FOREIGN KEY (heroId) REFERENCES hero(heroId),
    FOREIGN KEY (locationId) REFERENCES location(locationId),
    `time` DATETIME NOT NULL

);