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
public class DBse {

    // Atributos
    private String EXPORDSIT;
    private int CONTADOR;

    // Constructor 1
    public DBse() {

    }

    // Constructor 2
    public DBse(String EXPORDSIT, int CONTADOR) {
        this.EXPORDSIT = EXPORDSIT;
        this.CONTADOR = CONTADOR;
    }

    // Setters y getters
    public String getEXPORDSIT() {
        return EXPORDSIT;
    }

    public void setEXPORDSIT(String EXPORDSIT) {
        this.EXPORDSIT = EXPORDSIT;
    }

    public int getCONTADOR() {
        return CONTADOR;
    }

    public void setCONTADOR(int CONTADOR) {
        this.CONTADOR = CONTADOR;
    }

    // Obtener matriz con la situación 
    // de las expediciones
    public ArrayList<DBse> SitExp(Connection DBcon,
            HttpServletRequest request, String ym)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBse> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBse>();

        // Obtener matriz con la situación 
        // de las expediciones 
        try {
            pst = DBcon.prepareStatement(
                    "SELECT (CASE EXPORDSIT"
                    + " " + "WHEN 'PD'   THEN 'Pendientes'"
                    + " " + "WHEN 'AC'   THEN 'En curso'"
                    + " " + "WHEN 'PP'   THEN 'En curso'"
                    + " " + "WHEN 'PR'   THEN 'Preparadas'"                    
                    + " " + "WHEN 'ANUL' THEN 'Anuladas'"
                    + " " + "END) AS EXPORDSIT,"
                    + " " + "COUNT(*) AS CONTADOR"
                    + " " + "FROM EXPORDCAB"
                    + " " + "WHERE TO_CHAR(FECORD, 'YYYYMM') =" + " " + "'" + ym + "'"
                    + " " + "AND EXPORDSIT <> 'CE'"
                    + " " + "GROUP BY (CASE EXPORDSIT"
                    + " " + "WHEN 'PD'   THEN 'Pendientes'"
                    + " " + "WHEN 'AC'   THEN 'En curso'"
                    + " " + "WHEN 'PP'   THEN 'En curso'"
                    + " " + "WHEN 'PR'   THEN 'Preparadas'"                    
                    + " " + "WHEN 'ANUL' THEN 'Anuladas'"
                    + " " + "END)");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBse(
                        rs.getString("EXPORDSIT"),
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
