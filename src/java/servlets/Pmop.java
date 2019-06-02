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
import querys.DBpmop;

/**
 *
 * @author enrique
 */
@WebServlet(name = "Pmop", urlPatterns = {"/Pmop"})
public class Pmop extends HttpServlet {

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
        StringBuilder pmop_main;
        DBpmop DBs;
        String yearKey, y;
        ArrayList<DBpmop> dataPmop;
        StringBuilder pmop_data;        

        // Abrir la Base de Datos
        DBcon = new DBconnection();
        con = DBcon.conBDD(request);
        if (con == null) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
            return;
        }

        // Resaltar opción del menú
        request.setAttribute("pmop_act", "active");
        
        // Construir un atributo con el código HTML necesario
        // para presentar el cuadro de mando en la página JSP
        pmop_main = new StringBuilder();
        pmop_main.append("<main role=\"main\" class=\"col-md-9 ml-sm-auto col-lg-10 pt-3 px-4 bg-light\">");
        pmop_main.append("<div id=\"dashboard_div\">");
        pmop_main.append("<div class=\"row\">");
        pmop_main.append("<div id=\"filter1_div\" class=\"col-sm\"></div>");
        pmop_main.append("<div id=\"filter3_div\" class=\"col-sm\"></div>");
        pmop_main.append("</div>");                
        pmop_main.append("<div id=\"filter2_div\"></div>");
        pmop_main.append("<div id=\"chart_div\" class=\"border\"></div>");
        pmop_main.append("<div style=\"height: 2px\"></div>");
        pmop_main.append("<div id=\"table_div\" class=\"border\"></div>");
        pmop_main.append("</div>");
        pmop_main.append("</main>");
        request.setAttribute("pmop_main", pmop_main);
        
        // Construir una string con la info.de los pallets movidos por los 
        // operarios y guardar su contenido en un atributo de la página JSP
        DBs = new DBpmop();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        y = String.valueOf(Integer.valueOf(yearKey));
        dataPmop = DBs.PalletsMovXOper(con, request, y);
        pmop_data = new StringBuilder();
        pmop_data.append("[['Operario'");
        pmop_data.append(",'Año'");
        pmop_data.append(",'Mes'");
        pmop_data.append(",'Pallets movidos']");
        for (i = 0; i < dataPmop.size(); i++) {            
             pmop_data.append(",['").append(dataPmop.get(i).getRECOPEDES().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
             pmop_data.append(",'").append(dataPmop.get(i).getAÑO()).append("'");
             pmop_data.append(",'").append(dataPmop.get(i).getMES()).append("'");
             pmop_data.append(",").append(dataPmop.get(i).getCONTADOR()).append("]");
        }
        pmop_data.append("]");
        request.setAttribute("pmop_data", pmop_data);        

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
