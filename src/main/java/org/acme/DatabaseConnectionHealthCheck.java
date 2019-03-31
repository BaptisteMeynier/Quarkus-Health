package org.acme;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Health
public class DatabaseConnectionHealthCheck implements HealthCheck {

    @Inject
    private DataSource datasource;


    @Override
    public HealthCheckResponse call() {

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");

        try (final Connection con = datasource.getConnection()) {
            if (con.isValid(1000)) {
                responseBuilder.withData("Url:", con.getMetaData().getURL());
                responseBuilder.withData("User:", con.getMetaData().getUserName());
                responseBuilder.up();
            }
        } catch (IllegalStateException | SQLException e) {
            responseBuilder.down().withData("error", e.getMessage());
        }

        return responseBuilder.build();
    }

}
