--liquibase formatted sql
--changeset Jaypal:pokemon-table

CREATE TABLE pokemon (
	name varchar(100) NOT NULL,
	id INT auto_increment NOT NULL,
	imgUrl varchar(100) NULL,
	power varchar(100) NULL,
	CONSTRAINT pokemon_PK PRIMARY KEY (id),
	CONSTRAINT pokemon_UN UNIQUE KEY (name)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

--changeset Jaypal:power-table
CREATE TABLE power (
	id INT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT power_PK PRIMARY KEY (id),
	CONSTRAINT power_UN UNIQUE KEY (name)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

--changeset Jaypal:removed-column
ALTER TABLE pokemon DROP COLUMN power;
--changeset Jaypal:added-power
ALTER TABLE pokemon ADD power_id INT NULL;

--changeset Jaypal:added-foregin-key
ALTER TABLE pokemon ADD CONSTRAINT pokemon_FK FOREIGN KEY (power_id) REFERENCES power(id);








