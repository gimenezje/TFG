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
public class DBzep {

    // Atributos
    private String DIR3DES;
    private String AÑO;
    private String MES;
    private int CANSER;

    // Constructor 1
    public DBzep() {

    }

    // Constructor 2
    public DBzep(String DIR3DES, String AÑO, String MES, int CANSER) {
        this.DIR3DES = DIR3DES;
        this.AÑO = AÑO;
        this.MES = MES;
        this.CANSER = CANSER;
    }

    // Setters y getters 
    public String getDIR3DES() {
        return DIR3DES;
    }

    public void setDIR3DES(String DIR3DES) {
        this.DIR3DES = DIR3DES;
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

    public int getCANSER() {
        return CANSER;
    }

    public void setCANSER(int CANSER) {
        this.CANSER = CANSER;
    }

    // Obtener matriz con las zonas 
    // de expediciones
    public ArrayList<DBzep> ZonasExp(Connection DBcon,
            HttpServletRequest request, String y)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBzep> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBzep>();

        // Obtener matriz con las zonas 
        // de expediciones
        try {
            pst = DBcon.prepareStatement(
                    "SELECT DIR3DES,"
                    + " " + "AÑO,"
                    + " " + "MES,"
                    + " " + "CANSER"
                    + " " + "FROM MV_ZEP"
                    + " " + "WHERE AÑO <=" + " " + "'" + y + "'"        
                    + " " + "AND CANSER > 2000"
                    + " " + "ORDER BY AÑO,"
                    + " " + "MES,"
                    + " " + "CANSER DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBzep(
                        rs.getString("DIR3DES"),
                        rs.getString("AÑO"),
                        rs.getString("MES"),
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
