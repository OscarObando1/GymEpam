package org.oscar.gym.integration.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runs only the trainer management feature tests.
 *
 * Usage:
 *   ./mvnw test -Dtest=TrainerFeatureTest
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/trainer.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.oscar.gym.integration")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/trainer.html")
public class TrainerFeatureTest {
}
