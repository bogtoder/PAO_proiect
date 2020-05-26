
CREATE TABLE `autobuze` (
    `nr_id` INTEGER NOT NULL,
	`number_plate` VARCHAR(255) NOT NULL,
	`sofer` VARCHAR(255),
	`nr_locuri` INTEGER,
	`locatii` VARCHAR(255),
	`in_service` VARCHAR(255),
	`in_transit` VARCHAR(255),
	PRIMARY KEY (`nr_id`)
);

CREATE TABLE `avioane` (
    `nr_id` INTEGER NOT NULL,
    `nume` VARCHAR(255) NOT NULL,
	`pilot` VARCHAR(255),
	`copilot` VARCHAR(255),
	`masa_max` INTEGER,
	`locatii` VARCHAR(255),
	`taxa_livr` FLOAT(0),
	`colete` VARCHAR(255),
	`in_service` VARCHAR(255),
	`in_transit` VARCHAR(255),
	PRIMARY KEY (`nr_id`)
);

CREATE TABLE `nave` (
	`nr_id` INTEGER NOT NULL,
    `nume` VARCHAR(255) NOT NULL,
	`capitan` VARCHAR(255),
	`nr_locuri` INTEGER,
	`locatii` VARCHAR(255),
	`in_service` VARCHAR(255),
	`in_transit` VARCHAR(255),
	PRIMARY KEY (`nr_id`)
);

CREATE TABLE `dube` (
	`nr_id` INTEGER NOT NULL,
	`number_plate` VARCHAR(255) NOT NULL,
	`sofer` VARCHAR(255),
	`masa_maxima` INTEGER,
	`locatii` VARCHAR(255),
	`in_service` VARCHAR(255),
	`in_transit` VARCHAR(255),
	`taxa_livr` FLOAT(0),
	PRIMARY KEY (`nr_id`)
);
