/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.DBQueryHandler;

/**
 *
 * @author Pablo
 */
public class RegisterUserServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        Map<String,String> userList;
        String user="",pwd1="",pwd2="", error="";
        /*
        if(session.getAttribute("userList")!=null){
            userList = (HashMap<String, String>) session.getAttribute("userList");
        }else{
            userList =  new HashMap<> ();
            session.setAttribute("userList", userList);
        }*/
        
        if(request.getParameter("user")!=null && 
                request.getParameter("pwd1")!= null &&
                request.getParameter("pwd2")!= null){
            user = request.getParameter("user");
            pwd1 = request.getParameter("pwd1");
            pwd2 = request.getParameter("pwd2");
            if(!pwd1.equals(pwd2)){
                error = "Passwords doesnt match";
            }else{
                
                if(DBQueryHandler.usernameAlreadyUsed(user)){
                    error = "Username already in use";
                }else{
                    
                    if(DBQueryHandler.insertNewUser(user, pwd1)){
                        error = "Account created, procceed to <a href='index.html'>login</a>";
                    }else{
                        error = "Couldn't register user, try again later";
                    }
                }
            }
        }
        
        response.sendRedirect("registro.jsp?error="+error);
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
