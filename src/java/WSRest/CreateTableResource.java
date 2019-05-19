/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSRest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Pablo
 */
@Path("CreateTable")
public class CreateTableResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreateTableResource
     */
    public CreateTableResource() {
    }

    /**
     * Retrieves representation of an instance of WSRest.CreateTableResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml (@QueryParam("query") String query) {
        executeQuery(query);
        return "tabla creada";
    }

    /**
     * PUT method for updating or creating an instance of CreateTableResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public String putHtml(@QueryParam("queryParam") String queryParam) {
        System.out.println(queryParam);
        executeQuery(queryParam);
        return "tabla insertada";
    }
    
    /**
     * DELETE method for updating or creating an instance of CreateTableResource
     * @param content representation for the resource
     */
    @DELETE
    @Consumes(MediaType.TEXT_HTML)    
    @Produces(MediaType.TEXT_HTML)
    public String deleteHtml(@QueryParam("query") String query) {
        System.out.println(query);
        executeQuery(query);
        return "tabla borrada";
    }

    private static void executeQuery(java.lang.String query) {
        WSClient.SoapService_Service service = new WSClient.SoapService_Service();
        WSClient.SoapService port = service.getSoapServicePort();
        port.executeQuery(query);
    }
    
    
    
}
