--liquibase formatted sql
--changeset brijesh:pokemon-table

CREATE TABLE Pokemon.pokemon (
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

--changeset brijesh:power-table
CREATE TABLE Pokemon.power (
	id INT auto_increment NOT NULL,
	name varchar(100) NOT NULL,
	CONSTRAINT power_PK PRIMARY KEY (id),
	CONSTRAINT power_UN UNIQUE KEY (name)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

--changeset brijesh:removed-column
ALTER TABLE Pokemon.pokemon DROP COLUMN power;
--changeset brijesh:added-power
ALTER TABLE Pokemon.pokemon ADD power_id INT NULL;

--changeset brijesh:added-foregin-key
ALTER TABLE Pokemon.pokemon ADD CONSTRAINT pokemon_FK FOREIGN KEY (power_id) REFERENCES Pokemon.power(id);








