/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class DBQueryHandler {

    public static final String DB_NAME = "OmegaDBTest";
    public static final int SQL_EXCEPTION_CODE = -1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Map<String, String> tableStructure = new HashMap<>();

        tableStructure.put("edad", "int");
        tableStructure.put("campo1", "varchar(255)");
        tableStructure.put("campo2", "varchar(255)");
        tableStructure.put("campo3", "varchar(255)");

        System.out.println(DBQueryFormater.formatCreateTableQuery(DB_NAME, tableStructure));
        //getAllTables();
        //getColumnTypes("MySixthTable");
        //getColumnTypes("tablita_prueba");
        //createTable("tablita_prueba", tableStructure);
        getAllTables("");

        Map<String, String> insertValues = new HashMap<>();

        insertValues.put("edad", "13");
        insertValues.put("campo1", "slgo");
        insertValues.put("campo2", "blere");
        insertValues.put("campo3", "erer");

        Map<String, String> updateValues = new HashMap<>();
        updateValues.put("campo1", "updated");
        updateValues.put("campo2", "cambie");
        updateValues.put("campo3", "jala");

        System.out.println(DBQueryFormater.formatUpdateRegistryQuery("tablita_prueba", "edad", updateValues));
        System.out.println(DBQueryFormater.formatInsertQuery("tablita_prueba", insertValues));
        
        
        int a = insertTableRegistry("tablita_prueba", insertValues);
        System.out.println("affected on insert:" + a);
        selectAll("tablita_prueba");
        a = updateTableRegistry("tablita_prueba","edad","37", updateValues);
        System.out.println("affected on update:" + a);
        //System.out.println(get);
        /*
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
             "root", "root");
            Statement query = con.createStatement();
            //String QueryString = "create table MySixthTable (id int not null, name " +
            // "varchar(25),gender varchar(20),address varchar(50),phone varchar(20), primary key(id))";
            //query.executeUpdate(QueryString);
            query.executeUpdate("INSERT INTO MySixthTable VALUES (6, 'Juanelo', 'Male', 'Great Avenue #123', '(55) 1234 1234')");
            ResultSet rs = query.executeQuery("SELECT * FROM tablita_prueba");
            System.out.println("<p>");
            while(rs.next()) {
             System.out.println("<BR>Id: "+ rs.getInt("id"));
             System.out.println(" Name: "+ rs.getString("name"));
            } 
        }catch(SQLException ex){
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public static boolean usernameAlreadyUsed(String user){
        boolean result = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT * FROM users WHERE USERNAME='" + user + "'");

            if(rs.next()) {
                result = true;
            }

            query.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static boolean grantAccessToUser(String user, String pwd){
        boolean result = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE USERNAME=? AND PWD=?");
            
            ps.setString(1, user);            
            ps.setString(2, pwd);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                result = true;
            }
            
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public static boolean insertNewUser(String username, String pwd){
        boolean result = false;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");
            //Statement query = con.createStatement();
            PreparedStatement ps = con.prepareStatement("INSERT INTO USERS values(?, ?)");
            ps.setString(1, username);
            ps.setString(2, pwd);
            
            int success = ps.executeUpdate();
            result = success == 1;
            
            con.close();
        } catch (SQLException ex) {
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    private static Map<String, String> getColumTypeDictionary(Connection con, String tableName) throws SQLException{
        Map<String, String> columnDictionary = new HashMap<>();
        DatabaseMetaData meta = con.getMetaData();
        // needs to be in upper case for derby -> https://stackoverflow.com/questions/19156565/how-to-create-table-in-lower-case-name-javadb-derby
        ResultSet res = meta.getColumns(null, null, tableName.toUpperCase(), null);
               
        while (res.next()) {
            columnDictionary.put(res.getString("COLUMN_NAME"), res.getString("TYPE_NAME"));
        }
        
        return columnDictionary;
    }
    
    public static void selectAll(String tableName) {
        Set<String> columnSet = new HashSet<>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");
            DatabaseMetaData meta = con.getMetaData();
            ResultSet res = meta.getColumns(null, null, tableName.toUpperCase(), null);

            //System.out.println("Listing columns of: " + tableName );
            while (res.next()) {
                columnSet.add(res.getString("COLUMN_NAME"));
            }
            Statement query = con.createStatement();
            
            ResultSet rs = query.executeQuery("SELECT * FROM " + tableName.toUpperCase());

            while (rs.next()) {
                for(String col : columnSet)
                    System.out.println("Id: " + rs.getString(col));
                //System.out.println("Name: " + rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("Failed");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void createTable(String tableName, Map<String, String> fields) throws SQLException, ClassNotFoundException{
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                "root", "root");
        
        Statement query = con.createStatement();
        String QueryString = DBQueryFormater.formatCreateTableQuery(tableName, fields);
        query.executeUpdate(QueryString);
        
        con.close();  
    }

    public static int insertTableRegistry(String tableName, Map<String, String> fields){
        Map<String, String> columnDictionary = new HashMap<>();    
        int records = 0;
        
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");

            columnDictionary = getColumTypeDictionary(con, tableName);

            String QueryString = DBQueryFormater.formatInsertQuery(tableName, fields);
            PreparedStatement ps = con.prepareStatement(QueryString);

            int i = 1;
            for (Map.Entry<String, String> f : fields.entrySet()) {
                if (columnDictionary.get(f.getKey().toUpperCase()).equals("VARCHAR")) {
                    ps.setString(i, f.getValue());
                } else if (columnDictionary.get(f.getKey().toUpperCase()).equals("INTEGER")) {
                    ps.setInt(i, Integer.parseInt(f.getValue()));
                } else if (columnDictionary.get(f.getKey().toUpperCase()).equals("FLOAT")) {
                    ps.setFloat(i, Float.parseFloat(f.getValue()));
                }
                i++;
            }
            System.out.println(QueryString);
            records = ps.executeUpdate(); //number of rows affected/inserted

            con.close();
        }catch(SQLException ex){
            records = SQL_EXCEPTION_CODE;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public static int updateTableRegistry(String tableName, String rowId, String rowIdVal, Map<String, String> updateValues){
        
        Map<String, String> columnDictionary = new HashMap<>();
        int records = 0;
        
        try{        
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME + ";create=true;",
                    "root", "root");

            columnDictionary = getColumTypeDictionary(con, tableName);

            String QueryString = DBQueryFormater.formatUpdateRegistryQuery(tableName, rowId, updateValues);
            System.out.println(QueryString);        
            PreparedStatement ps = con.prepareStatement(QueryString);
            
            Iterator<Map.Entry<String, String>> entries = updateValues.entrySet().iterator();
            int i=1;
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                String value = entry.getValue();
                String key = entry.getKey().toUpperCase();
                System.out.println(key + ":" + value);
                if (columnDictionary.get(key).equals("VARCHAR")) {
                    ps.setString(i, value);
                } else if (columnDictionary.get(key).equals("INTEGER")) {
                    ps.setInt(i, Integer.parseInt(value));
                } else if (columnDictionary.get(key).equals("FLOAT")) {
                    ps.setFloat(i, Float.parseFloat(value));
                }
                i++;
            }
            
            if (columnDictionary.get(rowId.toUpperCase()).equals("VARCHAR")) {
                ps.setString(i, rowIdVal);
            } else if (columnDictionary.get(rowId.toUpperCase()).equals("INTEGER")) {
                ps.setInt(i, Integer.parseInt(rowIdVal));
            } else if (columnDictionary.get(rowId.toUpperCase()).equals("FLOAT")) {
                ps.setFloat(i, Float.parseFloat(rowIdVal));
            }
            
            records = ps.executeUpdate();

            con.close();      
        }catch(SQLException ex){
            System.out.println(ex.toString());
            records = SQL_EXCEPTION_CODE;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return records;
    }
    /*
    public static void getColumnTypes(String tableName) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME, "root", "root");
            DatabaseMetaData meta = con.getMetaData();
            // needs to be in upper case for derby -> https://stackoverflow.com/questions/19156565/how-to-create-table-in-lower-case-name-javadb-derby
            ResultSet res = meta.getColumns(null, null, tableName.toUpperCase(), null);
            // = meta.getTables(null, null, null, new String[] {"TABLE"});
            System.out.println("Listing columns of: " + tableName);
            while (res.next()) {
                System.out.println(res.getString("TYPE_NAME"));
                System.out.println(res.getString("COLUMN_NAME"));
            }
            res.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }/*/

    public static List<String> getAllTables(String user) {
        List<String> tableNames = new LinkedList<>();
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/" + DB_NAME, "root", "root");
            DatabaseMetaData meta = con.getMetaData();

            ResultSet res = meta.getTables(null, null, null, new String[]{"TABLE"});
            System.out.println("List of tables: ");
            while (res.next()) {
                System.out.println(res.getString("TABLE_NAME"));
                if(res.getString("TABLE_NAME").startsWith(user))
                    tableNames.add(res.getString("TABLE_NAME"));
            }
            res.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBQueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tableNames;
    }

}
