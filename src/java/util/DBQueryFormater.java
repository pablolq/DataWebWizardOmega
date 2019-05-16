/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Map;

/**
 *
 * @author Pablo
 */
public class DBQueryFormater {
    
    public static String formatInsertQuery(String tableName, Map<String, String> fields) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ");
        queryBuilder.append(tableName);
        queryBuilder.append(" values(");
        for (int i = 0; i < fields.size() - 1; i++) {
            queryBuilder.append("?, ");
        }
        queryBuilder.append("?)");

        return queryBuilder.toString();
    }

    public static String formatCreateTableQuery(String tableName, Map<String, String> fields) {
        
        StringBuilder queryBuilder = new StringBuilder("CREATE TABLE ");
        queryBuilder.append(tableName);
        queryBuilder.append(" (");

        for (Map.Entry<String, String> col : fields.entrySet()) {
            queryBuilder.append(col.getKey());
            queryBuilder.append(" ");
            queryBuilder.append(col.getValue());
            queryBuilder.append(", ");
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        //queryBuilder.append("primary key(" + fields +")");
        
        queryBuilder.append(")");
        
        return queryBuilder.toString();   
    }
    
    public static String formatUpdateRegistryQuery(String tableName, String rowId, Map<String, String> updatedFields) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE ");

        queryBuilder.append(tableName);
        queryBuilder.append(" SET ");

        for (Map.Entry<String, String> col : updatedFields.entrySet()) {
            queryBuilder.append(col.getKey());
            queryBuilder.append("=? ");  
            queryBuilder.append(", ");
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(" WHERE ");
        queryBuilder.append(rowId);
        queryBuilder.append("=?");

        return queryBuilder.toString();
    }
}
