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
import querys.DBoatu;
import querys.DBsr;
import querys.DBse;
import querys.DBmex;
import querys.DBfe;
import querys.DBtdo;

/**
 *
 * @author enrique
 */
@WebServlet(name = "Tr", urlPatterns = {"/Tr"})
public class Tr extends HttpServlet {

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
        StringBuilder tr_main;
        DBoatu DBs1;
        ArrayList<DBoatu> dataOatu;
        StringBuilder oatu_data;
        DBsr DBs2;
        ArrayList<DBsr> dataSr;
        StringBuilder sr_data;
        DBse DBs3;
        ArrayList<DBse> dataSe;
        StringBuilder se_data;
        DBmex DBs4;
        ArrayList<DBmex> dataMex;
        StringBuilder mex_data;
        DBfe DBs5;
        ArrayList<DBfe> dataFe;
        StringBuilder fe_data;
        DBtdo DBs6;
        ArrayList<DBtdo> dataTdo;
        StringBuilder tdo_data;
        String yearKey, monthKey, yearMonthKey, y, m, ym;

        // Abrir la Base de Datos
        DBcon = new DBconnection();
        con = DBcon.conBDD(request);
        if (con == null) {
            request.getRequestDispatcher("jsp/login.jsp")
                    .forward(request, response);
        }

        // Resaltar opción del menú
        request.setAttribute("tr_act", "active");

        // Construir un atributo con el código HTML necesario
        // para presentar el cuadro de mando en la página JSP
        tr_main = new StringBuilder();
        tr_main.append("<main role=\"main\" class=\"col-md-9 ml-sm-auto col-lg-10 pt-3 px-4 bg-light\">");
        tr_main.append("<div id=\"dashboard_div\">");
        tr_main.append("<div class=\"row\">");
        tr_main.append("<div id=\"oatu_chart_div\" class=\"border\"></div>");
        tr_main.append("<div style=\"width: 2px\"></div>");
        tr_main.append("<div id=\"sr_chart_div\" class=\"border\"></div>");
        tr_main.append("<div style=\"width: 2px\"></div>");
        tr_main.append("<div id=\"se_chart_div\" class=\"border\"></div>");
        tr_main.append("</div>");
        tr_main.append("<div style=\"height: 2px\"></div>");
        tr_main.append("<div class=\"row\">");
        tr_main.append("<div class=\"border-left border-top border-right\">");
        tr_main.append("<p style=\"width: 1100px\"><center><strong>Expediciones por muelle</strong></center></p>");
        tr_main.append("</div>");
        tr_main.append("</div>");
        tr_main.append("<div class=\"row\">");
        tr_main.append("<div id=\"mex_chart_div\" class=\"border-left border-bottom border-right\"></div>");
        tr_main.append("<div style=\"width: 2px\"></div>");
        tr_main.append("</div>");
        tr_main.append("<div style=\"height: 2px\"></div>");
        tr_main.append("<div class=\"row\">");
        tr_main.append("<div id=\"fe_chart_div\" class=\"border\"></div>");
        tr_main.append("</div>");
        tr_main.append("<div style=\"height: 2px\"></div>");
        tr_main.append("<div class=\"row\">");
        tr_main.append("<div id=\"tdo_chart_div\" class=\"border\"></div>");
        tr_main.append("</div>");
        tr_main.append("<div style=\"height: 2px\"></div>");
        tr_main.append("</div>");
        tr_main.append("</main>");
        request.setAttribute("tr_main", tr_main);

        // Construir una string con la info.de la ocupación del almacén por 
        // tipo de ubic. y guardar su contenido en un atributo de la página JSP
        DBs1 = new DBoatu();
        dataOatu = DBs1.OcupAlmXTipoUbic(con, request);
        oatu_data = new StringBuilder();
        oatu_data.append("[['Tipo ubic.'");
        oatu_data.append(",'Huecos llenos'");
        oatu_data.append(",'Huecos vacíos']");
        for (i = 0; i < dataOatu.size(); i++) {
            oatu_data.append(",['").append(dataOatu.get(i).getUBITIP().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            oatu_data.append(",").append(dataOatu.get(i).getUBILL());
            oatu_data.append(",").append(dataOatu.get(i).getUBIVA()).append("]");
        }
        oatu_data.append("]");
        request.setAttribute("oatu_data", oatu_data);

        // Construir una string con la info.de la situación de las recepciones 
        // y guardar su contenido en un atributo de la página JSP
        DBs2 = new DBsr();
        yearMonthKey = System.getProperty("dateKey").substring(0, 6);
        ym = String.valueOf(Integer.valueOf(yearMonthKey));
        dataSr = DBs2.SitRec(con, request, ym);
        sr_data = new StringBuilder();
        sr_data.append("[['Estado'");
        sr_data.append(",'Contador']");
        for (i = 0; i < dataSr.size(); i++) {
            sr_data.append(",['").append(dataSr.get(i).getRECORDSIT().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            sr_data.append(",").append(dataSr.get(i).getCONTADOR()).append("]");
        }
        sr_data.append("]");
        request.setAttribute("sr_data", sr_data);

        // Construir una string con la info.de la situación de las expediciones 
        // y guardar su contenido en un atributo de la página JSP
        DBs3 = new DBse();
        yearMonthKey = System.getProperty("dateKey").substring(0, 6);
        ym = String.valueOf(Integer.valueOf(yearMonthKey));
        dataSe = DBs3.SitExp(con, request, ym);
        se_data = new StringBuilder();
        se_data.append("[['Estado'");
        se_data.append(",'Contador']");
        for (i = 0; i < dataSe.size(); i++) {
            se_data.append(",['").append(dataSe.get(i).getEXPORDSIT().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            se_data.append(",").append(dataSe.get(i).getCONTADOR()).append("]");
        }
        se_data.append("]");
        request.setAttribute("se_data", se_data);

        // Construir una string con la info.de las expediciones por muelle 
        // y guardar su contenido en un atributo de la página JSP
        DBs4 = new DBmex();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        monthKey = System.getProperty("dateKey").substring(4, 6);
        y = String.valueOf(Integer.valueOf(yearKey));
        m = String.valueOf(Integer.valueOf(monthKey));
        dataMex = DBs4.ExpXMuelle(con, request, y, m);
        mex_data = new StringBuilder();
        mex_data.append("[['Muelle'");
        mex_data.append(",'Uds.servidas']");
        for (i = 0; i < dataMex.size(); i++) {
            mex_data.append(",['").append(dataMex.get(i).getESTREF().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            mex_data.append(",").append(dataMex.get(i).getCANSER()).append("]");
        }
        mex_data.append("]");
        request.setAttribute("mex_data", mex_data);

        // Construir una string con la info.de las faltas en expediciones 
        // y guardar su contenido en un atributo de la página JSP
        DBs5 = new DBfe();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        monthKey = System.getProperty("dateKey").substring(4, 6);
        y = String.valueOf(Integer.valueOf(yearKey));
        m = String.valueOf(Integer.valueOf(monthKey));
        dataFe = DBs5.FaltasExp(con, request, y, m);
        fe_data = new StringBuilder();
        fe_data.append("[['Artículo'");
        fe_data.append(",'Uds.pedidas'");
        fe_data.append(",'Uds.servidas'");
        fe_data.append(",'Uds.faltantes']");
        for (i = 0; i < dataFe.size(); i++) {
            fe_data.append(",['").append(dataFe.get(i).getARTDES().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            fe_data.append(",").append(dataFe.get(i).getCANPED());
            fe_data.append(",").append(dataFe.get(i).getCANSER());
            fe_data.append(",").append(dataFe.get(i).getCANFAL()).append("]");
        }
        fe_data.append("]");
        request.setAttribute("fe_data", fe_data);

        // Construir una string con la info.del top 10 de operarios 
        // y guardar su contenido en un atributo de la página JSP
        DBs6 = new DBtdo();
        yearKey = System.getProperty("dateKey").substring(0, 4);
        monthKey = System.getProperty("dateKey").substring(4, 6);
        y = String.valueOf(Integer.valueOf(yearKey));
        m = String.valueOf(Integer.valueOf(monthKey));
        dataTdo = DBs6.Top10Oper(con, request, y, m);
        tdo_data = new StringBuilder();
        tdo_data.append("[['Operario'");
        tdo_data.append(",'Pallets movidos']");
        for (i = 0; i < dataTdo.size(); i++) {
            tdo_data.append(",['").append(dataTdo.get(i).getRECOPEDES().trim()
                    .replace("\"", " ")
                    .replace("'", " ")
                    .replace("\\", " "))
                    .append("'");
            tdo_data.append(",").append(dataTdo.get(i).getCONTADOR()).append("]");
        }
        tdo_data.append("]");
        request.setAttribute("tdo_data", tdo_data);

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
