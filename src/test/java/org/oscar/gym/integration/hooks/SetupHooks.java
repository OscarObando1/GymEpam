package org.oscar.gym.integration.hooks;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Cucumber hooks for test database lifecycle management.
 * Runs before each scenario to ensure a clean, consistent state.
 */
public class SetupHooks {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${test.seed.password}")
    private String seedPassword;

    /**
     * Cleans all tables and seeds base test data before each scenario.
     * Order = 0 ensures this runs before any step-level @Before hooks.
     */
    @Before(order = 0)
    public void cleanAndSeedDatabase() {
        // Delete in FK-safe order.
        // 'user' works unquoted because the datasource URL includes NON_KEYWORDS=USER.
        jdbcTemplate.execute("DELETE FROM training");
        jdbcTemplate.execute("DELETE FROM trainer_trainee");
        jdbcTemplate.execute("DELETE FROM trainee");
        jdbcTemplate.execute("DELETE FROM trainer");
        jdbcTemplate.execute("DELETE FROM user");
        jdbcTemplate.execute("DELETE FROM training_type");

        String pass = passwordEncoder.encode(seedPassword);

        // Training types
        jdbcTemplate.update("INSERT INTO training_type (id, name) VALUES (?, ?)", 1L, "LIFTING");
        jdbcTemplate.update("INSERT INTO training_type (id, name) VALUES (?, ?)", 2L, "CARDIO");
        jdbcTemplate.update("INSERT INTO training_type (id, name) VALUES (?, ?)", 3L, "CROSSFIT");

        // Trainee: Oscar.Obando
        jdbcTemplate.update(
                "INSERT INTO user (id, first_name, last_name, username, password, active) VALUES (?, ?, ?, ?, ?, ?)",
                1L, "Oscar", "Obando", "Oscar.Obando", pass, true
        );
        jdbcTemplate.update(
                "INSERT INTO trainee (id, address, date_of_birth) VALUES (?, ?, ?)",
                1L, "Calle falsa 123", java.sql.Date.valueOf("1991-03-30")
        );

        // Trainer 1: Arnold.Terminator (LIFTING)
        jdbcTemplate.update(
                "INSERT INTO user (id, first_name, last_name, username, password, active) VALUES (?, ?, ?, ?, ?, ?)",
                2L, "Arnold", "Terminator", "Arnold.Terminator", pass, true
        );
        jdbcTemplate.update("INSERT INTO trainer (id, training_type_id) VALUES (?, ?)", 2L, 1L);

        // Trainer 2: Rocky.Balboa (CARDIO)
        jdbcTemplate.update(
                "INSERT INTO user (id, first_name, last_name, username, password, active) VALUES (?, ?, ?, ?, ?, ?)",
                3L, "Rocky", "Balboa", "Rocky.Balboa", pass, true
        );
        jdbcTemplate.update("INSERT INTO trainer (id, training_type_id) VALUES (?, ?)", 3L, 2L);

        // H2 2.x does NOT auto-advance the IDENTITY sequence when explicit IDs are inserted.
        // Reset both sequences so Hibernate-generated IDs start well above our seeded IDs.
        jdbcTemplate.execute("ALTER TABLE user ALTER COLUMN id RESTART WITH 100");
        jdbcTemplate.execute("ALTER TABLE training_type ALTER COLUMN id RESTART WITH 100");
    }
}
