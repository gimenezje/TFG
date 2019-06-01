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
public class DBmex {

    // Atributos
    private String ESTREF;
    private int CANSER;

    // Constructor 1
    public DBmex() {

    }

    // Constructor 2
    public DBmex(String ESTREF, int CANSER) {
        this.ESTREF = ESTREF;
        this.CANSER = CANSER;
    }

    // Setters y getters 
    public String getESTREF() {
        return ESTREF;
    }

    public void setESTREF(String ESTREF) {
        this.ESTREF = ESTREF;
    }

    public int getCANSER() {
        return CANSER;
    }

    public void setCANSER(int CANSER) {
        this.CANSER = CANSER;
    }

    // Obtener matriz con las expediciones 
    // por muelle
    public ArrayList<DBmex> ExpXMuelle(Connection DBcon,
            HttpServletRequest request, String y, String m)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBmex> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBmex>();

        // Obtener matriz con las expediciones 
        // por muelle 
        try {
            pst = DBcon.prepareStatement(
                    "SELECT ESTREF,"
                    + " " + "CANSER"
                    + " " + "FROM MV_MEX"
                    + " " + "WHERE AÑO =" + " " + "'" + y + "'"
                    + " " + "AND MES =" + " " + "'" + m + "'"        
                    + " " + "ORDER BY CANSER DESC"
                    + " " + "FETCH FIRST 11 ROWS ONLY");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBmex(
                        rs.getString("ESTREF"),
                        rs.getInt("CANSER")));
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
