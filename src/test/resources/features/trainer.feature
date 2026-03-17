Feature: Trainer Management
  As an authenticated gym staff member
  I want to manage trainers
  So that I can maintain the gym's trainer roster

  Background:
    Given the database is initialized with test data
    And I am authenticated as "Oscar.Obando" with password "password123"

  # ── POST /trainer (requires authentication) ────────────────────────────────

  Scenario: Create a trainer when authenticated returns 201 with credentials
    When I create a trainer with firstName "Bruce" lastName "Lee" specialization "CROSSFIT"
    Then the response status is 201
    And the response contains a username

  Scenario: Create trainer with invalid specialization returns 400
    When I create a trainer with firstName "Bruce" lastName "Lee" specialization "YOGA"
    Then the response status is 400

  # ── NFR: Trainer creation requires authentication ─────────────────────────

  Scenario: Create trainer without authentication is rejected with 403
    Given I am not authenticated
    When I create a trainer with firstName "Bruce" lastName "Lee" specialization "CROSSFIT"
    Then the response status is 403

  # ── GET /trainer ───────────────────────────────────────────────────────────

  Scenario: Retrieve an existing trainer by username returns full profile
    When I fetch trainer with username "Arnold.Terminator"
    Then the response status is 200
    And the trainer first name is "Arnold"

  Scenario: Retrieve a non-existent trainer returns 404
    When I fetch trainer with username "NoSuch.Trainer"
    Then the response status is 404

  # ── NFR: Trainer retrieval requires authentication ─────────────────────────

  Scenario: Fetch trainer without authentication is rejected with 403
    Given I am not authenticated
    When I fetch trainer with username "Arnold.Terminator"
    Then the response status is 403

  # ── PUT /trainer ───────────────────────────────────────────────────────────

  Scenario: Update an existing trainer successfully
    When I update trainer "Arnold.Terminator" with firstName "Arnold" lastName "Strong" specialization "CARDIO"
    Then the response status is 200

  Scenario: Update a non-existent trainer returns 404
    When I update trainer "Ghost.Trainer" with firstName "Ghost" lastName "Trainer" specialization "LIFTING"
    Then the response status is 404

  # ── PATCH /trainer/update/{id} ─────────────────────────────────────────────

  Scenario: Deactivate an active trainer
    When I activate trainer "Arnold.Terminator" with isActive "false"
    Then the response status is 200

  Scenario: Reactivate a deactivated trainer
    When I activate trainer "Arnold.Terminator" with isActive "true"
    Then the response status is 200

  # ── POST /trainer/change ───────────────────────────────────────────────────

  Scenario: Change trainer password with correct old password succeeds
    When I change trainer password for "Arnold.Terminator" from "password123" to "newpass456"
    Then the response status is 200

  # ── GET /trainer/not-assigned ──────────────────────────────────────────────

  Scenario: Get trainers not assigned to a trainee returns a list
    When I get unassigned trainers for trainee "Oscar.Obando"
    Then the response status is 200
    And the response contains a list of trainers

  Scenario: Get unassigned trainers for a non-existent trainee returns 404
    When I get unassigned trainers for trainee "Ghost.Trainee"
    Then the response status is 404
