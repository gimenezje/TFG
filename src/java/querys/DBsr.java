/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Paquetes
package querys;

// Librerías
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author enrique
 */
public class DBsr {

    // Atributos
    private String RECORDSIT;
    private int CONTADOR;

    // Constructor 1
    public DBsr() {

    }

    // Constructor 2
    public DBsr(String RECORDSIT, int CONTADOR) {
        this.RECORDSIT = RECORDSIT;
        this.CONTADOR = CONTADOR;
    }

    // Setters y getters 
    public String getRECORDSIT() {
        return RECORDSIT;
    }

    public void setRECORDSIT(String RECORDSIT) {
        this.RECORDSIT = RECORDSIT;
    }

    public int getCONTADOR() {
        return CONTADOR;
    }

    public void setCONTADOR(int CONTADOR) {
        this.CONTADOR = CONTADOR;
    }

    // Obtener matriz con la situación 
    // de las recepciones
    public ArrayList<DBsr> SitRec(Connection DBcon,
            HttpServletRequest request, String ym)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBsr> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBsr>();

        // Obtener matriz con la situación 
        // de las recepciones
        try {
            pst = DBcon.prepareStatement(
                    "SELECT (CASE RECORDSIT"
                    + " " + "WHEN 'PD' THEN 'Pendientes'"
                    + " " + "WHEN 'EC' THEN 'En curso'"
                    + " " + "WHEN 'CE' THEN 'Cerradas'"
                    + " " + "END) AS RECORDSIT,"
                    + " " + "COUNT(*) AS CONTADOR"
                    + " " + "FROM RECORDCAB"
                    + " " + "WHERE TO_CHAR(FECORD, 'YYYYMM') =" + " " + "'" + ym + "'"
                    + " " + "GROUP BY (CASE RECORDSIT"
                    + " " + "WHEN 'PD' THEN 'Pendientes'"
                    + " " + "WHEN 'EC' THEN 'En curso'"
                    + " " + "WHEN 'CE' THEN 'Cerradas'"
                    + " " + "END)");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBsr(
                        rs.getString("RECORDSIT"),
                        rs.getInt("CONTADOR")));
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "No fue posible extraer la info. de la Base de Datos</div>");
        }

        // Devolución matriz
        return data;
    }
}
