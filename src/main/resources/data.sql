-- data.sql
CREATE TABLE training_type (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT unique_name UNIQUE (name)
);

INSERT IGNORE INTO training_type (id, name) VALUES (1, 'LIFTING');
INSERT IGNORE INTO training_type (id, name) VALUES (2, 'CARDIO');
INSERT IGNORE INTO training_type (id, name) VALUES (3, 'CROSSFIT');
