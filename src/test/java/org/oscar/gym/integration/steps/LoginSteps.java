package org.oscar.gym.integration.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.oscar.gym.integration.context.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthContext authContext;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    // ── Shared auth steps (used in Background of multiple features) ──────────

    @Given("I am authenticated as {string} with password {string}")
    public void authenticateAs(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/login"),
                HttpMethod.POST,
                new HttpEntity<>(Map.of("username", username, "password", password), headers),
                String.class
        );
        assertThat(response.getStatusCode().value())
                .as("Login must succeed before running the scenario")
                .isEqualTo(200);
        // Strip surrounding quotes if the token is returned as a JSON string literal
        String token = response.getBody();
        if (token != null && token.startsWith("\"")) {
            token = token.substring(1, token.length() - 1);
        }
        authContext.setJwtToken(token);
        authContext.setLastResponse(response);
    }

    @Given("I am not authenticated")
    public void clearAuthentication() {
        authContext.setJwtToken(null);
    }

    // ── Login endpoint steps ──────────────────────────────────────────────────

    @When("I log in with username {string} and password {string}")
    public void login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/login"),
                HttpMethod.POST,
                new HttpEntity<>(Map.of("username", username, "password", password), headers),
                String.class
        );
        authContext.setLastResponse(response);
        if (response.getStatusCode() == HttpStatus.OK) {
            String token = response.getBody();
            if (token != null && token.startsWith("\"")) {
                token = token.substring(1, token.length() - 1);
            }
            authContext.setJwtToken(token);
        }
    }

    @When("I call GET {string} without any token")
    public void callGetWithoutToken(String path) {
        ResponseEntity<String> response = restTemplate.exchange(
                url(path), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    @When("I call GET {string} with token {string}")
    public void callGetWithInvalidToken(String path, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<String> response = restTemplate.exchange(
                url(path), HttpMethod.GET, new HttpEntity<>(headers), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── Login-specific assertions ─────────────────────────────────────────────

    @Then("a JWT token is returned in the response")
    public void jwtTokenReturnedInResponse() {
        String body = authContext.getLastResponse().getBody();
        assertThat(body).isNotNull().isNotBlank();
        // Remove surrounding JSON quotes if present
        if (body.startsWith("\"")) {
            body = body.substring(1, body.length() - 1);
        }
        // JWT has exactly 3 base64-encoded segments separated by dots
        assertThat(body.split("\\.")).hasSize(3);
    }
}
