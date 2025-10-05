CREATE TABLE IF NOT EXISTS training (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trainee_id BIGINT NOT NULL,
    trainer_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    training_type_id BIGINT NOT NULL,
    training_date DATE NOT NULL,
    duration_training INT NOT NULL,
    CONSTRAINT fk_training_trainer FOREIGN KEY (trainer_id) REFERENCES trainer(id),
    CONSTRAINT fk_training_trainee FOREIGN KEY (trainee_id) REFERENCES trainee(id),
    CONSTRAINT fk_training_type FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);
