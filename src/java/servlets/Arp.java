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
import querys.DBarp;

/**
 *
 * @author enrique
 */
@WebServlet(name = "Arp", urlPatterns = {"/Arp"})
public class Arp extends HttpServlet {

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
        StringBuilder arp_main;
        DBarp DBs;
        String yearKey, y1, y2;
        ArrayList<DBarp> dataArp;
        StringBuilder arp_data;

        // Abrir la Base de Datos
        DBcon = new DBconnection();
        con = DBcon.conBDD(request);
        if (con == null) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
        }

        // Resaltar opción del menú
        request.setAttribute("arp_act", "active");

        // Construir un atributo con el código HTML necesario
        // para presentar el cuadro de mando en la página JSP
        arp_main = new StringBuilder();
        arp_main.append("<main role=\"main\" class=\"col-md-9 ml-sm-auto col-lg-10 pt-3 px-4 bg-light\">");
        arp_main.append("<div id=\"dashboard_div\">");
        arp_main.append("<div id=\"chart_div\" class=\"border\"></div>");
        arp_main.append("<div style=\"height: 2px\"></div>");
        arp_main.append("<div id=\"table_div\" class=\"border\"></div>");
        arp_main.append("</div>");
        arp_main.append("</main>");
        request.setAttribute("arp_main", arp_main);

        // Construir una string con la info.de los artículos recibidos
        // y guardar su contenido en un atributo de la página JSP
        DBs = new DBarp();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        y1 = String.valueOf(Integer.valueOf(yearKey) - 1);
        y2 = String.valueOf(Integer.valueOf(yearKey));
        dataArp = DBs.ArtRec(con, request, y1, y2);
        arp_data = new StringBuilder();
        arp_data.append("[['Artículo'");
        arp_data.append(",'").append(y1).append("'");
        arp_data.append(",'").append(y2).append("']");
        for (i = 0; i < dataArp.size(); i++) {
            arp_data.append(",['").append(dataArp.get(i).getARTDES().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            arp_data.append(",").append(dataArp.get(i).getCANREC_Y1());
            arp_data.append(",").append(dataArp.get(i).getCANREC_Y2()).append("]");
        }
        arp_data.append("]");
        request.setAttribute("arp_data", arp_data);

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
