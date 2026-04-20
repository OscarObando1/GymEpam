package org.oscar.gym.integration.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Runs only the training management feature tests.
 *
 * Usage:
 *   ./mvnw test -Dtest=TrainingFeatureTest
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/training.feature")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.oscar.gym.integration")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/training.html")
public class TrainingFeatureTest {
}
