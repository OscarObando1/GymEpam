package org.oscar.gym.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Gym app for EPAM",
                description = "Api rest",
                contact = @Contact(
                        name = "Oscar Obando",
                        email = "oscar.obandoo@outlook.com"
                ),
                summary = "api rest where there are crud with this entities trainee,trainer,trainings"
        )
)
public class SwaggerConfig {
}
