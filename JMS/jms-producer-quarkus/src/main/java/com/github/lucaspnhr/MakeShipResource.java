package com.github.lucaspnhr;

import org.jboss.resteasy.reactive.RestQuery;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ship")
public class MakeShipResource {

    @Inject
    ConnectionFactory connectionFactory;

    @GET
    @Path("/submit")
    @Produces(MediaType.TEXT_PLAIN)
    public String submitShipment(@RestQuery("weight") int weight,@RestQuery("product-name") String productName) {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            context.createProducer().send(context.createQueue("shipment"), "%d - %s".formatted(weight,productName));
        }
        return "ship submited";
    }
}