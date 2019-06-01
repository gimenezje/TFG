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
public class DBtdo {

    // Atributos
    private String RECOPEDES;
    private int CONTADOR;

    // Constructor 1
    public DBtdo() {

    }

    // Constructor 2
    public DBtdo(String RECOPEDES, int CONTADOR) {
        this.RECOPEDES = RECOPEDES;
        this.CONTADOR = CONTADOR;
    }

    // Setters y getters
    public String getRECOPEDES() {
        return RECOPEDES;
    }

    public void setRECOPEDES(String RECOPEDES) {
        this.RECOPEDES = RECOPEDES;
    }

    public int getCONTADOR() {
        return CONTADOR;
    }

    public void setCONTADOR(int CONTADOR) {
        this.CONTADOR = CONTADOR;
    }

    // Obtener matriz con el top 10
    // de los operarios
    public ArrayList<DBtdo> Top10Oper(Connection DBcon,
            HttpServletRequest request, String y, String m)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBtdo> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBtdo>();

        // Obtener matriz con el top 10
        // de los operarios 
        try {
            pst = DBcon.prepareStatement(
                    "SELECT RECOPEDES,"
                    + " " + "CONTADOR"
                    + " " + "FROM MV_PMOP"
                    + " " + "WHERE AÑO =" + " " + "'" + y + "'"
                    + " " + "AND MES =" + " " + "'" + m + "'"
                    + " " + "ORDER BY CONTADOR DESC"
                    + " " + "FETCH FIRST 10 ROWS ONLY");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBtdo(
                        rs.getString("RECOPEDES"),
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
