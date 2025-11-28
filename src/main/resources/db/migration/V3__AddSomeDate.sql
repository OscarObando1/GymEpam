
INSERT IGNORE INTO user (id, first_name, last_name, username, password, active)
VALUES (1, 'Oscar', 'Obando', 'Oscar.Obando', '$2a$12$ReNphvQiXYhyEQ8cHvfJU.M3nt87ta6P92Y8YyeVO7LQItRWyhKOS', 1);

INSERT IGNORE INTO trainee (id, address, date_of_birth)
VALUES (1, 'Calle falsa 123', '1991-03-30');

INSERT IGNORE INTO user (id, first_name, last_name, username, password, active)
VALUES (2, 'Arnold', 'Terminator', 'Arnold.Terminator', '$2a$12$ReNphvQiXYhyEQ8cHvfJU.M3nt87ta6P92Y8YyeVO7LQItRWyhKOS', 1);

INSERT IGNORE INTO trainer (id, training_type_id)
VALUES (2, 1);

INSERT IGNORE INTO user (id, first_name, last_name, username, password, active)
VALUES (3, 'Rocky', 'Balboa', 'Rocky.Balboa', '$2a$12$ReNphvQiXYhyEQ8cHvfJU.M3nt87ta6P92Y8YyeVO7LQItRWyhKOS', 1);

INSERT IGNORE INTO trainer (id, training_type_id)
VALUES (3, 2);

INSERT IGNORE INTO user (id, first_name, last_name, username, password, active)
VALUES (4, 'Jet', 'Li', 'Jet.Li', '$2a$12$ReNphvQiXYhyEQ8cHvfJU.M3nt87ta6P92Y8YyeVO7LQItRWyhKOS', 1);

INSERT IGNORE INTO trainer (id, training_type_id)
VALUES (4, 3);
