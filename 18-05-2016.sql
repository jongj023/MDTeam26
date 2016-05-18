-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `enabled` INT(11) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`locker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`locker` (
  `lockerid` INT(11) NOT NULL AUTO_INCREMENT,
  `locker_floor` INT(11) NOT NULL,
  `locker_number` INT(2) UNSIGNED ZEROFILL NOT NULL,
  `locker_tower` VARCHAR(1) NOT NULL,
  `user` VARCHAR(45) NULL DEFAULT NULL,
  `date_acquired` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date_expired` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`lockerid`),
  INDEX `fk_user` (`user` ASC),
  CONSTRAINT `fk_user`
    FOREIGN KEY (`user`)
    REFERENCES `mydb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`lockerhistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`lockerhistory` (
  `historyid` INT(11) NOT NULL AUTO_INCREMENT,
  `locker` INT(11) NOT NULL,
  `user` VARCHAR(45) NULL DEFAULT NULL,
  `date_acquired` TIMESTAMP NULL DEFAULT NULL,
  `date_expired` DATE NULL DEFAULT NULL,
  `date_updated` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `action` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`historyid`),
  INDEX `fk_lockerhistory_1_idx` (`locker` ASC),
  INDEX `fk_lockerhistory_2_idx` (`user` ASC),
  CONSTRAINT `fk_lockerhistory_1`
    FOREIGN KEY (`locker`)
    REFERENCES `mydb`.`locker` (`lockerid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lockerhistory_2`
    FOREIGN KEY (`user`)
    REFERENCES `mydb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 144
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_roles` (
  `user_role_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(255) NOT NULL DEFAULT 'USER',
  `username` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  INDEX `fk_user_roles_1_idx` (`username` ASC),
  CONSTRAINT `fk_user_roles_1`
    FOREIGN KEY (`username`)
    REFERENCES `mydb`.`user` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
