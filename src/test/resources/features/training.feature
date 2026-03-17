Feature: Training Management
  As an authenticated gym staff member
  I want to manage training sessions
  So that I can track gym activity and statistics

  Background:
    Given the database is initialized with test data
    And I am authenticated as "Oscar.Obando" with password "password123"

  # ── GET /training (training types) ────────────────────────────────────────

  Scenario: List all available training types
    When I get all training types
    Then the response status is 200
    And the response contains a list of training types

  # ── POST /training ─────────────────────────────────────────────────────────

  Scenario: Create a valid training session returns 200
    When I create a training with trainer "Arnold.Terminator" trainee "Oscar.Obando" name "Morning Lifting" date "2026-04-01" duration 60
    Then the response status is 200

  Scenario: Create another training session with a different trainer
    When I create a training with trainer "Rocky.Balboa" trainee "Oscar.Obando" name "Cardio Burn" date "2026-04-02" duration 45
    Then the response status is 200

  Scenario: Create training with non-existent trainee returns 404
    When I create a training with trainer "Arnold.Terminator" trainee "Ghost.Trainee" name "Session" date "2026-04-01" duration 60
    Then the response status is 404

  Scenario: Create training with non-existent trainer returns 404
    When I create a training with trainer "Ghost.Trainer" trainee "Oscar.Obando" name "Session" date "2026-04-01" duration 60
    Then the response status is 404

  # ── GET /training/list-trainee/trainings ───────────────────────────────────

  Scenario: List all trainings for a trainee
    When I get training list for trainee "Oscar.Obando"
    Then the response status is 200

  Scenario: List trainings for a trainee with date range filter
    When I get training list for trainee "Oscar.Obando" from "2026-01-01" to "2026-12-31"
    Then the response status is 200

  Scenario: List trainings for a non-existent trainee returns 404
    When I get training list for trainee "Ghost.User"
    Then the response status is 404

  # ── GET /training/list-trainer/trainings ───────────────────────────────────

  Scenario: List all trainings for a trainer
    When I get training list for trainer "Arnold.Terminator"
    Then the response status is 200

  Scenario: List trainings for a non-existent trainer returns 404
    When I get training list for trainer "Ghost.Trainer"
    Then the response status is 404

  # ── NFR: Training endpoints require authentication ─────────────────────────

  Scenario: List training types without authentication is rejected
    Given I am not authenticated
    When I get all training types
    Then the response status is 403

  Scenario: List trainee trainings without authentication is rejected
    Given I am not authenticated
    When I get training list for trainee "Oscar.Obando"
    Then the response status is 403

  Scenario: Create training without authentication is rejected
    Given I am not authenticated
    When I create a training with trainer "Arnold.Terminator" trainee "Oscar.Obando" name "Session" date "2026-04-01" duration 60
    Then the response status is 403
