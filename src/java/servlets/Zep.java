/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Paquetes
package servlets;

// Librerías
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import querys.DBconnection;
import querys.DBzep;

/**
 *
 * @author enrique
 */
@WebServlet(name = "Zep", urlPatterns = {"/Zep"})
public class Zep extends HttpServlet {

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
        int i;
        DBconnection DBcon;
        Connection con;
        StringBuilder zep_main;
        DBzep DBs;
        String yearKey, y;
        ArrayList<DBzep> dataZep;
        StringBuilder zep_data;

        // Abrir la Base de Datos
        DBcon = new DBconnection();
        con = DBcon.conBDD(request);
        if (con == null) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        }

        // Resaltar opción del menú
        request.setAttribute("zep_act", "active");

        // Construir un atributo con el código HTML necesario
        // para presentar el cuadro de mando en la página JSP
        zep_main = new StringBuilder();
        zep_main.append("<main role=\"main\" class=\"col-md-9 ml-sm-auto col-lg-10 pt-3 px-4 bg-light\">");
        zep_main.append("<div id=\"dashboard_div\">");
        zep_main.append("<div id=\"filter1_div\"></div>");
        zep_main.append("<div id=\"filter2_div\"></div>");
        zep_main.append("<div <p><center><strong>Zonas expediciones (Paraguay)</strong></center></p></div>");
        zep_main.append("<div id=\"chart_div\" class=\"border\"></div>");
        zep_main.append("<div style=\"height: 2px\"></div>");
        zep_main.append("<div id=\"table_div\" class=\"border\"></div>");
        zep_main.append("</div>");
        zep_main.append("</main>");
        request.setAttribute("zep_main", zep_main);

        // Construir una string con la info.de las zonas de expediciones
        // y guardar su contenido en un atributo de la página JSP
        DBs = new DBzep();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        y = String.valueOf(Integer.valueOf(yearKey));
        dataZep = DBs.ZonasExp(con, request, y);
        zep_data = new StringBuilder();
        zep_data.append("[['Zona'");
        zep_data.append(",'Año'");
        zep_data.append(",'Mes'");
        zep_data.append(",'Uds.servidas']");
        for (i = 0; i < dataZep.size(); i++) {
            zep_data.append(",['").append(dataZep.get(i).getDIR3DES().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            zep_data.append(",'").append(dataZep.get(i).getAÑO().trim()).append("'");
            zep_data.append(",'").append(dataZep.get(i).getMES().trim()).append("'");
            zep_data.append(",").append(dataZep.get(i).getCANSER()).append("]");
        }
        zep_data.append("]");
        request.setAttribute("zep_data", zep_data);

        // Acceder al menú principal
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
