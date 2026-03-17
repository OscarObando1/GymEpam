package org.oscar.gym.integration.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.oscar.gym.integration.context.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TraineeSteps {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AuthContext authContext;

    @Autowired
    private ObjectMapper objectMapper;

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    private HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (authContext.hasToken()) {
            headers.setBearerAuth(authContext.getJwtToken());
        }
        return headers;
    }

    // ── GET /trainee ──────────────────────────────────────────────────────────

    @When("I fetch trainee with username {string}")
    public void fetchTrainee(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee?username=" + username),
                HttpMethod.GET,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    // ── POST /trainee (public) ────────────────────────────────────────────────

    @When("I create a trainee with firstName {string} lastName {string}")
    public void createTrainee(String firstName, String lastName) {
        Map<String, Object> body = Map.of("firstName", firstName, "lastName", lastName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee"), HttpMethod.POST, new HttpEntity<>(body, headers), String.class
        );
        authContext.setLastResponse(response);
    }

    @When("I create a trainee with blank firstName and lastName {string}")
    public void createTraineeWithBlankFirstName(String lastName) {
        Map<String, Object> body = Map.of("firstName", "", "lastName", lastName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee"), HttpMethod.POST, new HttpEntity<>(body, headers), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── PUT /trainee ──────────────────────────────────────────────────────────

    @When("I update trainee {string} setting firstName {string} lastName {string} isActive {string}")
    public void updateTrainee(String username, String firstName, String lastName, String isActive) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", username);
        body.put("firstName", firstName);
        body.put("lastName", lastName);
        body.put("isActive", Boolean.parseBoolean(isActive));
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee"), HttpMethod.PUT, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── DELETE /trainee ───────────────────────────────────────────────────────

    @When("I delete trainee with username {string}")
    public void deleteTrainee(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee?username=" + username),
                HttpMethod.DELETE,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    // ── PATCH /trainee/update ─────────────────────────────────────────────────

    @When("I activate trainee {string} with isActive {string}")
    public void activateTrainee(String username, String isActive) {
        Map<String, Object> body = Map.of("username", username, "isActive", Boolean.parseBoolean(isActive));
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee/update"), HttpMethod.PATCH, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── PUT /trainee/list-trainer ─────────────────────────────────────────────

    @When("I update trainer list for {string} with {string}")
    public void updateTrainerList(String traineeUsername, String trainerUsername) {
        Map<String, Object> body = new HashMap<>();
        body.put("username", traineeUsername);
        body.put("listUsernameTrainer", List.of(trainerUsername));
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee/list-trainer"), HttpMethod.PUT, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── POST /trainee/change ──────────────────────────────────────────────────

    @When("I change password for {string} from {string} to {string}")
    public void changeTraineePassword(String username, String oldPass, String newPass) {
        Map<String, Object> body = Map.of("username", username, "oldPass", oldPass, "newPass", newPass);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainee/change"), HttpMethod.POST, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── Assertions ────────────────────────────────────────────────────────────

    @Then("the trainee first name is {string}")
    public void traineeFirstNameIs(String expectedFirstName) throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.get("firstName").asText()).isEqualTo(expectedFirstName);
    }

    @Then("the response contains a username with {string}")
    public void responseContainsUsername(String expectedUsername) throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.get("username").asText()).isEqualTo(expectedUsername);
    }
}
