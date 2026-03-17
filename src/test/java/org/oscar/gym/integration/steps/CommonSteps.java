package org.oscar.gym.integration.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.oscar.gym.integration.context.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shared step definitions used across multiple feature files.
 */
public class CommonSteps {

    @Autowired
    private AuthContext authContext;

    @Given("the database is initialized with test data")
    public void databaseInitialized() {
        // Actual initialization is handled by @Before hook in SetupHooks
    }

    @Then("the response status is {int}")
    public void responseStatusIs(int expectedStatus) {
        assertThat(authContext.getLastResponse().getStatusCode().value())
                .as("Expected HTTP status %d", expectedStatus)
                .isEqualTo(expectedStatus);
    }

    @Then("the response is a client error")
    public void responseIsClientError() {
        assertThat(authContext.getLastResponse().getStatusCode().is4xxClientError())
                .as("Expected a 4xx client error response")
                .isTrue();
    }

    @Then("the response is an error")
    public void responseIsAnyError() {
        assertThat(authContext.getLastResponse().getStatusCode().isError())
                .as("Expected a 4xx or 5xx error response")
                .isTrue();
    }
}
