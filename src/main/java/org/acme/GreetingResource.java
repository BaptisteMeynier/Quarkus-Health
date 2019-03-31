package org.acme;

import org.eclipse.microprofile.health.Health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greeting")
@Produces(MediaType.TEXT_PLAIN)
@Health
public class GreetingResource {

    @GET
    public String getValue(){
        return "Hello";
    }
}
