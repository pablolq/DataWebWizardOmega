/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pablo
 */
public class FieldServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int numFields;
        try (PrintWriter out = response.getWriter()) {
            if(request.getParameter("numFields")==null){
                numFields = 20;
            }else{
                String num = request.getParameter("numFields");
                System.out.println(num);
                numFields = Integer.parseInt(num);
            }
            

            String res = "";
            System.out.println(numFields);
            for (int i = 0; i < numFields; i++) {
                res = res + "Field Name: <input id='fieldName" + i + "' type='text' value=''/>\n"
                        + "        <select id='ddlType" + i + "'>\n"
                        + "            <option>varchar</option>\n"
                        + "            <option>int</option>\n"
                        + "            <option>double</option>\n"
                        + "        </select>  \n"
                        + "        Primary Key <input id='PK" + i + "' type='radio' value='' />\n"
                        + "        Longitud: <input id='length" + i + "' type='text' value='' />\n"
                        + "        <br>";
            }
            out.println(res);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
