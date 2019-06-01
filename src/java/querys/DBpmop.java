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
public class DBpmop {

    // Atributos
    private String RECOPEDES;
    private String AÑO;
    private String MES;
    private int CONTADOR;

    // Constructor 1
    public DBpmop() {

    }

    // Constructor 2
    public DBpmop(String RECOPEDES, String AÑO, String MES, int CONTADOR) {
        this.RECOPEDES = RECOPEDES;
        this.AÑO = AÑO;
        this.MES = MES;
        this.CONTADOR = CONTADOR;
    }

    // Setters y getters
    public String getRECOPEDES() {
        return RECOPEDES;
    }

    public void setRECOPEDES(String RECOPEDES) {
        this.RECOPEDES = RECOPEDES;
    }

    public String getAÑO() {
        return AÑO;
    }

    public void setAÑO(String AÑO) {
        this.AÑO = AÑO;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public int getCONTADOR() {
        return CONTADOR;
    }

    public void setCONTADOR(int CONTADOR) {
        this.CONTADOR = CONTADOR;
    }

    // Obtener matriz con los pallets movidos
    // por los operarios
    public ArrayList<DBpmop> PalletsMovXOper(Connection DBcon,
            HttpServletRequest request, String y)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBpmop> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBpmop>();

        // Obtener matriz con los pallets movidos
        // por los operarios       
        try {
            pst = DBcon.prepareStatement(
                    "SELECT RECOPEDES,"
                    + " " + "AÑO,"
                    + " " + "MES,"
                    + " " + "CONTADOR"
                    + " " + "FROM MV_PMOP"
                    + " " + "WHERE AÑO <=" + " " + "'" + y + "'"
                    + " " + "ORDER BY AÑO,"
                    + " " + "MES,"
                    + " " + "CONTADOR DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBpmop(
                        rs.getString("RECOPEDES"),
                        rs.getString("AÑO"),
                        rs.getString("MES"),
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
