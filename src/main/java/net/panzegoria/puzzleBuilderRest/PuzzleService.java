package net.panzegoria.puzzleBuilderRest;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;

@Path("puzzle")
public class PuzzleService {
    @Context
    ServletContext servletContext;

    @GET
    @Path("/{varX}")
    @Produces("text/html")
    public Response getPuzzleByID(@PathParam("varX") String name) {
        String output = readFile(name);
        return Response.status(200).entity(output).build();
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.WILDCARD)
    public Response createPuzzle(@QueryParam("name") String name, String puzzle) {
        writeFile(name, puzzle);
        return Response.status(200).build();
    }

    private String makeFilename(String nameIn) {
        nameIn = nameIn.replaceAll("[^a-zA-Z0-9.-]", "_");
        String folder = servletContext.getRealPath("/Maps");

        File file = new File(folder);

        if(!(file.exists())) {
            file.mkdir();
        }

        return String.format(folder + "/map-%s.txt", nameIn);
    }

    private String readFile(String filename) {
        File f = new File(filename);
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes,"UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void writeFile(String puzzleName, String puzzleData) {
        String filename = makeFilename(puzzleName);

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter( filename));
            writer.write( puzzleData);
        }
        catch ( IOException e)
        {
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
            }
        }
    }
}
