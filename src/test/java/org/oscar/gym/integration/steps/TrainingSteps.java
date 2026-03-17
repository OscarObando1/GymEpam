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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TrainingSteps {

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

    // ── POST /training ────────────────────────────────────────────────────────

    @When("I create a training with trainer {string} trainee {string} name {string} date {string} duration {int}")
    public void createTraining(String trainerUsername, String traineeUsername, String name, String date, int duration) {
        Map<String, Object> body = new HashMap<>();
        body.put("trainerUsername", trainerUsername);
        body.put("traineeUsername", traineeUsername);
        body.put("name", name);
        body.put("date", date);
        body.put("duration", duration);
        ResponseEntity<String> response = restTemplate.exchange(
                url("/training"), HttpMethod.POST, new HttpEntity<>(body, authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── GET /training (list types) ────────────────────────────────────────────

    @When("I get all training types")
    public void getAllTrainingTypes() {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/training"), HttpMethod.GET, new HttpEntity<>(authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── GET /training/list-trainee/trainings ──────────────────────────────────

    @When("I get training list for trainee {string}")
    public void getTraineeTrainingList(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/training/list-trainee/trainings?username=" + username),
                HttpMethod.GET,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    @When("I get training list for trainee {string} from {string} to {string}")
    public void getTraineeTrainingListWithDates(String username, String dateFrom, String dateTo) {
        String path = String.format(
                "/training/list-trainee/trainings?username=%s&dateFrom=%s&dateTo=%s",
                username, dateFrom, dateTo
        );
        ResponseEntity<String> response = restTemplate.exchange(
                url(path), HttpMethod.GET, new HttpEntity<>(authHeaders()), String.class
        );
        authContext.setLastResponse(response);
    }

    // ── GET /training/list-trainer/trainings ──────────────────────────────────

    @When("I get training list for trainer {string}")
    public void getTrainerTrainingList(String username) {
        ResponseEntity<String> response = restTemplate.exchange(
                url("/training/list-trainer/trainings?username=" + username),
                HttpMethod.GET,
                new HttpEntity<>(authHeaders()),
                String.class
        );
        authContext.setLastResponse(response);
    }

    // ── Assertions ────────────────────────────────────────────────────────────

    @Then("the response contains a list of training types")
    public void responseContainsListOfTrainingTypes() throws Exception {
        JsonNode node = objectMapper.readTree(authContext.getLastResponse().getBody());
        assertThat(node.isArray()).as("Response should be a JSON array of training types").isTrue();
        assertThat(node.size()).as("There should be at least one training type").isGreaterThan(0);
    }
}
