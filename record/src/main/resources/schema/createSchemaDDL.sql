---
-- DATABASE: records
---

DROP TABLE IF EXISTS `records`.`student_auth`;
DROP TABLE IF EXISTS `records`.`student`;

CREATE SCHEMA `records`;

CREATE TABLE IF NOT EXISTS `records`.`student_auth`(
	
	`username` VARCHAR(200) NOT NULL PRIMARY KEY,
	`password` VARCHAR(200) NOT NULL,
	`role` VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS `records`.`student`(
	`student_id` INT(6) NOT NULL PRIMARY KEY,
	`student_name` VARCHAR(50) NOT NULL,
	`email` VARCHAR(50) NOT NULL,
	`standard` INT(2)
);

