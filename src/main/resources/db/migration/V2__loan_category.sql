CREATE TABLE `loan_category` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `loan_category` VARCHAR(512),
    `interest_rate` DOUBLE,
    PRIMARY KEY(id)
);


INSERT INTO `loan_category` (`loan_category`, `interest_rate`) VALUES
("POLLUTION_PREVENTION", 6),
("WATER", 7),
("CIRCULAR_ECONOMY", 5),
("CLIMATE_ADAPTATION", 8),
("CLIMATE_MITIGATION", 9),
("BIODIVERSITY", 10)