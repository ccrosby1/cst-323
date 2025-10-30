-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema workouttracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workouttracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workouttracker` DEFAULT CHARACTER SET utf8 ;
USE `workouttracker` ;

-- -----------------------------------------------------
-- Table `workouttracker`.`workout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workouttracker`.`workout` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NULL,
  `name` VARCHAR(45) NULL,
  `duration` INT NULL,
  `muscleGroup` VARCHAR(100) NULL,
  `notes` TEXT(1000) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
