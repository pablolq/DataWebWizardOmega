/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WSSoap;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import util.DBQueryHandler;

/**
 *
 * @author Pablo
 */
@WebService(serviceName = "SoapService")
public class SoapService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllTablesFromUser")
    public String[] getAllTablesFromUser(@WebParam(name = "username") String username) {
        //TODO write your implementation code here:
        List<String> lista= DBQueryHandler.getAllTables(username);        
        String[] tablas = lista.stream().toArray(String[]::new);
        return tablas;
    }
}
