/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSRest;

import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("UserTables")
public class UserTablesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserTablesResource
     */
    public UserTablesResource() {
    }

    /**
     * Retrieves representation of an instance of WSRest.UserTablesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@QueryParam("username") String username) {
        List<String> lista = getAllTablesFromUser(username.toUpperCase());
        StringBuilder sb = new StringBuilder("<Lista>");
        
        lista.forEach((t) -> {
            sb.append("<tabla><nombre>");
            sb.append(t);
            sb.append("</nombre></tabla>");
        });

        sb.append("</Lista>");
        return sb.toString();
    }

    /**
     * PUT method for updating or creating an instance of UserTablesResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private static java.util.List<java.lang.String> getAllTablesFromUser(java.lang.String username) {
        WSClient.SoapService_Service service = new WSClient.SoapService_Service();
        WSClient.SoapService port = service.getSoapServicePort();
        return port.getAllTablesFromUser(username);
    }

    

    

    

    
}
