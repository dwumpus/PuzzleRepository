package net.panzegoria.puzzleBuilderRest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("puzzle")
public class PuzzleService {
    @GET
    @Path("/ID/{varX}")
    @Produces("text/html")
    public Response getStartingPage(@PathParam("varX") String test) {
        String output = "<h1>Hello World</h1>" + test;
        return Response.status(200).entity(output).build();
    }
}
