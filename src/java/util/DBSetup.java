/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class DBSetup {
    
    public static final String DB_NAME = "OmegaDBTest";
    
    public static void main(String[] args) {
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
             "root", "root");
            Statement query = con.createStatement();
            String QueryString = "create table users (username varchar(255) not null, pwd varchar(25), primary key(username))";
            query.executeUpdate(QueryString);
            //query.executeUpdate("INSERT INTO MySixthTable VALUES (6, 'Juanelo', 'Male', 'Great Avenue #123', '(55) 1234 1234')");
            
            ResultSet rs = query.executeQuery("SELECT * FROM users");
            
            while(rs.next()) {
             System.out.println("U: "+ rs.getInt("username"));
             System.out.println("P: "+ rs.getString("pwd"));
            } 
            
            query.close();
            con.close();
        }catch(SQLException ex){
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
