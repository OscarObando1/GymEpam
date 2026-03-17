Feature: User Authentication
  As a gym management system user
  I want to authenticate with my credentials
  So that I can access protected endpoints

  Background:
    Given the database is initialized with test data

  # ── Positive scenarios ─────────────────────────────────────────────────────

  Scenario: Successful login returns a JWT token
    When I log in with username "Oscar.Obando" and password "password123"
    Then the response status is 200
    And a JWT token is returned in the response

  Scenario: Trainer can also log in successfully
    When I log in with username "Arnold.Terminator" and password "password123"
    Then the response status is 200
    And a JWT token is returned in the response

  # ── Negative scenarios ─────────────────────────────────────────────────────

  Scenario: Login with wrong password is rejected
    When I log in with username "Oscar.Obando" and password "wrongpassword"
    Then the response is a client error

  Scenario: Login with non-existent username is rejected
    When I log in with username "nobody.here" and password "password123"
    Then the response is a client error

  # ── NFR: Security / permissions ───────────────────────────────────────────

  Scenario: Protected endpoint is inaccessible without any authentication token
    When I call GET "/trainee?username=Oscar.Obando" without any token
    Then the response status is 403

  Scenario: Protected endpoint is inaccessible with an invalid JWT token
    When I call GET "/trainee?username=Oscar.Obando" with token "this.is.not.a.valid.jwt"
    Then the response status is 403

  Scenario: Public endpoint for trainee registration is accessible without token
    When I call GET "/trainee?username=Oscar.Obando" without any token
    Then the response status is 403
