/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Paquetes
package servlets;

// Librerías
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import querys.DBconnection;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import querys.DBchkCred;

/**
 *
 * @author egimenez
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Definiciones locales
        String webAppPath;
        Properties p;
        String dateKey;
        DBconnection DBcon;
        Connection con;
        String usr, pas;
        DBchkCred DBs;

        // Guardar el path de la aplicación web como una propiedad
        webAppPath = request.getServletContext().getRealPath("/");
        System.setProperty("webAppPath", webAppPath);

        // Obtener la fecha clave de datos y guardarla como una propiedad  
        p = new Properties();
        webAppPath = System.getProperty("webAppPath");
        try {
            p.load(new FileInputStream(webAppPath + "/config/config.ini"));
        } catch (FileNotFoundException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "No hallado el archivo config.ini</div>");
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        } catch (IOException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "Error de lectura del archivo config.ini</div>");
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        }
        dateKey = p.getProperty("DATE_KEY");
        System.setProperty("dateKey", dateKey);

        // Abrir la Base de Datos
        DBcon = new DBconnection();
        con = DBcon.conBDD(request);
        if (con == null) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        }

        // Obtener credenciales introducidas
        usr = request.getParameter("inputUser");
        pas = request.getParameter("inputPassword");

        // Verificar credenciales
        DBs = new DBchkCred();
        if (!DBs.chkCred(con, usr, pas, request)) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        }

        // Acceder al menú principal
        request.getSession().setAttribute("usr", usr);
        request.getSession().setAttribute("pas", pas);
        request.getRequestDispatcher("jsp/menu.jsp").forward(request, response);
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
