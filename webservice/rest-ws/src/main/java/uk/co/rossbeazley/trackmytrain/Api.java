package uk.co.rossbeazley.trackmytrain;

import uk.co.rossbeazley.trackmytrain.wsclient.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
public class Api {


    @GET
    @Path("hello")
    @Produces("text/plain")
    public String hello() {
        return "world";
    }

    @GET
    @Path("service/{id}")
    @Produces(APPLICATION_JSON)
    public Response service(@PathParam("id") String id) {
        System.out.println("get service " + id );

        ServiceResult result = ServiceDetailsClient.serviceDetails(id);
        return Response.ok().entity(result.toJson()).build();
    }


    @GET
    @Path("service")
    @Produces(APPLICATION_JSON)
    public Response serviceWithParam(@QueryParam("id") String id) {
        System.out.println("get service " + id );

        ServiceResult result = ServiceDetailsClient.serviceDetails(id);
        return Response.ok().entity(result.toJson()).build();
    }


    @GET
    @Path("departures/{from}/to/{to}")
    @Produces(APPLICATION_JSON)
    public Response service(@PathParam("from") String from,@PathParam("to") String to) {
        DeparturesResult result = DeparturesClient.services(from, to);
        return Response.ok().entity(result.toJson()).build();
    }

}
