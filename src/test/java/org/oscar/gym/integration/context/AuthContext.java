package org.oscar.gym.integration.context;

import io.cucumber.spring.ScenarioScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Scenario-scoped shared state for Cucumber integration tests.
 * A fresh instance is created for each scenario.
 */
@Component
@ScenarioScope
public class AuthContext {

    private String jwtToken;
    private ResponseEntity<String> lastResponse;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public ResponseEntity<String> getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(ResponseEntity<String> lastResponse) {
        this.lastResponse = lastResponse;
    }

    public boolean hasToken() {
        return jwtToken != null && !jwtToken.isBlank();
    }
}
