/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSRest;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Pablo
 */
@Path("TableSchema")
public class TableSchemaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TableSchemaResource
     */
    public TableSchemaResource() {
    }

    /**
     * Retrieves representation of an instance of WSRest.TableSchemaResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@QueryParam("table") String table) {
        List<String> columns = getTableData(table);
        StringBuilder sb = new StringBuilder("<Tabla>");
        int i = 0;
        while(i<columns.size()){
            sb.append("<columna><nombre>");
            sb.append(columns.get(i++));
            sb.append("</nombre><tipo>");
            sb.append(columns.get(i++));
            sb.append("</tipo></columna>");
        }

        sb.append("</Tabla>");
        return sb.toString();
    }

    /**
     * Retrieves representation of an instance of WSRest.TableSchemaResource
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String postXml(@QueryParam("table") String table) {
        System.out.println("POST SCHEMA");
        List<String> columns = getTableData(table);
        StringBuilder sb = new StringBuilder("{ \"columnas\" : [");
        int i = 0;
        while(i<columns.size()){
            sb.append("{\"nombre\":\"");
            sb.append(columns.get(i++));
            sb.append("\",\"tipo\":\"");
            sb.append(columns.get(i++));
            if(i!=columns.size()){
                sb.append("\"},");
            }else{
                sb.append("\"}");
            }
        }
        
        sb.append("]}");
        System.out.println(sb.toString());
        return sb.toString();
    }
    /**
     * PUT method for updating or creating an instance of TableSchemaResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteXml(@QueryParam("table") String table) {
        
    }

    private static java.util.List<java.lang.String> getTableData(java.lang.String tableName) {
        WSClient.SoapService_Service service = new WSClient.SoapService_Service();
        WSClient.SoapService port = service.getSoapServicePort();
        return port.getTableData(tableName);
    }
    
    
    
}
