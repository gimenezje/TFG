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
public class DBfe {

    // Atributos
    private String ARTDES;
    private int CANPED;
    private int CANSER;
    private int CANFAL;

    // Constructor 1
    public DBfe() {

    }

    // Constructor 2
    public DBfe(String ARTDES, int CANPED, int CANSER, int CANFAL) {
        this.ARTDES = ARTDES;
        this.CANPED = CANPED;
        this.CANSER = CANSER;
        this.CANFAL = CANFAL;
    }

    // Setters y getters 
    public String getARTDES() {
        return ARTDES;
    }

    public void setARTDES(String ARTDES) {
        this.ARTDES = ARTDES;
    }

    public int getCANPED() {
        return CANPED;
    }

    public void setCANPED(int CANPED) {
        this.CANPED = CANPED;
    }

    public int getCANSER() {
        return CANSER;
    }

    public void setCANSER(int CANSER) {
        this.CANSER = CANSER;
    }

    public int getCANFAL() {
        return CANFAL;
    }

    public void setCANFAL(int CANFAL) {
        this.CANFAL = CANFAL;
    }

    // Obtener matriz con las faltas 
    // en las expediciones
    public ArrayList<DBfe> FaltasExp(Connection DBcon,
            HttpServletRequest request, String y, String m)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBfe> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBfe>();

        // Obtener matriz con las faltas 
        // en las expediciones 
        try {
            pst = DBcon.prepareStatement(
                    "SELECT ARTDES,"
                    + " " + "CANPED,"
                    + " " + "CANSER,"
                    + " " + "CANFAL"
                    + " " + "FROM MV_AEP"
                    + " " + "WHERE AÑO =" + " " + "'" + y + "'"
                    + " " + "AND MES =" + " " + "'" + m + "'"
                    + " " + "AND CANFAL > 0"
                    + " " + "ORDER BY CANPED DESC"
                    + " " + "FETCH FIRST 10 ROWS ONLY");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBfe(
                        rs.getString("ARTDES"),
                        rs.getInt("CANPED"),
                        rs.getInt("CANSER"),
                        rs.getInt("CANFAL")));
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
