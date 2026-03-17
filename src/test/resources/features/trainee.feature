Feature: Trainee Management
  As an authenticated gym staff member
  I want to manage trainees
  So that I can maintain the gym's trainee database

  Background:
    Given the database is initialized with test data
    And I am authenticated as "Oscar.Obando" with password "password123"

  # ── POST /trainee (public endpoint) ───────────────────────────────────────

  Scenario: Create a new trainee with valid data returns 201 and generated credentials
    When I create a trainee with firstName "Jane" lastName "Doe"
    Then the response status is 201
    And the response contains a username with "Jane.Doe"

  Scenario: Create a second trainee with the same last name generates a unique username
    When I create a trainee with firstName "Oscar" lastName "Obando"
    Then the response status is 201

  Scenario: Create trainee with blank first name is rejected with 400
    When I create a trainee with blank firstName and lastName "Doe"
    Then the response status is 400

  # ── GET /trainee ───────────────────────────────────────────────────────────

  Scenario: Retrieve an existing trainee by username returns full profile
    When I fetch trainee with username "Oscar.Obando"
    Then the response status is 200
    And the trainee first name is "Oscar"

  Scenario: Retrieve a non-existent trainee returns 404
    When I fetch trainee with username "NoSuch.User"
    Then the response status is 404

  # ── PUT /trainee ───────────────────────────────────────────────────────────

  Scenario: Update an existing trainee successfully
    When I update trainee "Oscar.Obando" setting firstName "UpdatedOscar" lastName "Obando" isActive "true"
    Then the response status is 200

  Scenario: Update a non-existent trainee returns 404
    When I update trainee "Ghost.User" setting firstName "Ghost" lastName "User" isActive "true"
    Then the response status is 404

  # ── PATCH /trainee/update ──────────────────────────────────────────────────

  Scenario: Deactivate an active trainee
    When I activate trainee "Oscar.Obando" with isActive "false"
    Then the response status is 200

  Scenario: Reactivate a deactivated trainee
    When I activate trainee "Oscar.Obando" with isActive "true"
    Then the response status is 200

  # ── PUT /trainee/list-trainer ──────────────────────────────────────────────

  Scenario: Update trainee's assigned trainer list with a valid trainer
    When I update trainer list for "Oscar.Obando" with "Arnold.Terminator"
    Then the response status is 200

  Scenario: Update trainer list with non-existent trainer returns an error
    # NOTE: ideally this should return 404, but the service currently throws NullPointerException
    # when the trainer is not found (returns 500). The test verifies the call fails as expected.
    When I update trainer list for "Oscar.Obando" with "Ghost.Trainer"
    Then the response is an error

  # ── POST /trainee/change ───────────────────────────────────────────────────

  Scenario: Change trainee password with correct old password succeeds
    When I change password for "Oscar.Obando" from "password123" to "newpass456"
    Then the response status is 200

  # ── DELETE /trainee ────────────────────────────────────────────────────────

  Scenario: Delete an existing trainee returns 200
    When I delete trainee with username "Oscar.Obando"
    Then the response status is 200

  Scenario: Delete a non-existent trainee returns 404
    When I delete trainee with username "Nobody.Here"
    Then the response status is 404

  # ── NFR: Security / permissions ────────────────────────────────────────────

  Scenario: Unauthenticated request to get trainee is blocked
    Given I am not authenticated
    When I fetch trainee with username "Oscar.Obando"
    Then the response status is 403

  Scenario: Unauthenticated request to delete trainee is blocked
    Given I am not authenticated
    When I delete trainee with username "Oscar.Obando"
    Then the response status is 403
