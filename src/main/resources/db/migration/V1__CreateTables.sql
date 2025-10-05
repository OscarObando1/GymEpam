CREATE DATABASE IF NOT EXISTS gymdb;

CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS training_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT unique_name UNIQUE (name)
);

INSERT IGNORE INTO training_type (id, name) VALUES (1, 'LIFTING');
INSERT IGNORE INTO training_type (id, name) VALUES (2, 'CARDIO');
INSERT IGNORE INTO training_type (id, name) VALUES (3, 'CROSSFIT');


CREATE TABLE IF NOT EXISTS trainer (
    id BIGINT PRIMARY KEY,
    training_type_id BIGINT,
    CONSTRAINT fk_trainer_user FOREIGN KEY (id)
        REFERENCES user (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_trainer_training_type FOREIGN KEY (training_type_id)
        REFERENCES training_type (id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS trainee (
    id BIGINT PRIMARY KEY,
    address VARCHAR(255),
    date_of_birth DATE,
    CONSTRAINT fk_trainee_user FOREIGN KEY (id)
        REFERENCES user (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS trainer_trainee (
    trainer_id BIGINT NOT NULL,
    trainee_id BIGINT NOT NULL,
    PRIMARY KEY (trainer_id, trainee_id),
    CONSTRAINT fk_tt_trainer FOREIGN KEY (trainer_id)
        REFERENCES trainer (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_tt_trainee FOREIGN KEY (trainee_id)
        REFERENCES trainee (id)
        ON DELETE CASCADE
);
