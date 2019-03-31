package org.acme;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Health
public class MultipleHealthCheck implements HealthCheck {

    @Inject
    private DataSource datasource;


    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Multiple health check");

        responseBuilder.withData("memory", Runtime.getRuntime().freeMemory());

        responseBuilder.withData("availableProcessors", Runtime.getRuntime().availableProcessors());




        try (final Connection con = datasource.getConnection()) {
            if (con.isValid(1000)) {
                responseBuilder.withData("Url:",con.getMetaData().getURL());
                responseBuilder.withData("User:",con.getMetaData().getUserName());
                responseBuilder.up();
            }
        } catch (IllegalStateException | SQLException e) {
            responseBuilder.down().withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }

}
