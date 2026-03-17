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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainerSteps {

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

    // ── POST /trainer (requires auth) ─────────────────────────────────────────

    @When("I create a trainer with firstName {string} lastName {string} specialization {string}")
    public void createTrainer(String firstName, String lastName, String specialization) {
        Map<String, Object> body = Map.of(
                "firstName", firstName,
                "lastName", lastName,
                "specialization", specialization
        );
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer"), HttpMethod.POST, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── GET /trainer ──────────────────────────────────────────────────────────

    @When("I fetch trainer with username {string}")
    public void fetchTrainer(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer?username=" + username),
                HttpMethod.GET,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    // ── PUT /trainer ──────────────────────────────────────────────────────────

    @When("I update trainer {string} with firstName {string} lastName {string} specialization {string}")
    public void updateTrainer(String username, String firstName, String lastName, String specialization) {
        Map<String, Object> body = Map.of(
                "username", username,
                "firstName", firstName,
                "lastName", lastName,
                "specialization", specialization,
                "isActive", true
        );
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer"), HttpMethod.PUT, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── PATCH /trainer/update/{id} ────────────────────────────────────────────

    @When("I activate trainer {string} with isActive {string}")
    public void activateTrainer(String username, String isActive) {
        Map<String, Object> body = Map.of("username", username, "isActive", Boolean.parseBoolean(isActive));
        // The {id} path variable is not used by the service; using 0 as a placeholder
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer/update/0"), HttpMethod.PATCH, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── GET /trainer/not-assigned ─────────────────────────────────────────────

    @When("I get unassigned trainers for trainee {string}")
    public void getUnassignedTrainers(String traineeUsername) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer/not-asigned?username=" + traineeUsername),
                HttpMethod.GET,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    // ── POST /trainer/change ──────────────────────────────────────────────────

    @When("I change trainer password for {string} from {string} to {string}")
    public void changeTrainerPassword(String username, String oldPass, String newPass) {
        Map<String, Object> body = Map.of("username", username, "oldPass", oldPass, "newPass", newPass);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/trainer/change"), HttpMethod.POST, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── Assertions ────────────────────────────────────────────────────────────

    @Then("the trainer first name is {string}")
    public void trainerFirstNameIs(String expectedFirstName) throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.get("firstName").asText()).isEqualTo(expectedFirstName);
    }

    @Then("the response contains a list of trainers")
    public void responseContainsListOfTrainers() throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.isArray()).as("Response should be a JSON array of trainers").isTrue();
    }

    @Then("the response contains a username")
    public void responseContainsUsername() throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.has("username")).as("Response should contain a 'username' field").isTrue();
        assertThat(node.get("username").asText()).isNotBlank();
    }
}
