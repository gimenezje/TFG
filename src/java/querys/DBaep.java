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
public class DBaep {

    // Atributos
    private String ARTDES;
    private int CANSER_Y1;
    private int CANSER_Y2;

    // Constructor 1
    public DBaep() {

    }

    // Constructor 2
    public DBaep(String ARTDES, int CANSER_Y1, int CANSER_Y2) {
        this.ARTDES = ARTDES;
        this.CANSER_Y1 = CANSER_Y1;
        this.CANSER_Y2 = CANSER_Y2;
    }

    // Setters y getters 
    public String getARTDES() {
        return ARTDES;
    }

    public void setARTDES(String ARTDES) {
        this.ARTDES = ARTDES;
    }

    public int getCANSER_Y1() {
        return CANSER_Y1;
    }

    public void setCANSER_Y1(int CANSER_Y1) {
        this.CANSER_Y1 = CANSER_Y1;
    }

    public int getCANSER_Y2() {
        return CANSER_Y2;
    }

    public void setCANSER_Y2(int CANSER_Y2) {
        this.CANSER_Y2 = CANSER_Y2;
    }

    // Obtener matriz con los artículos expedidos 
    // en los dos últimos ejercicios
    public ArrayList<DBaep> ArtExp(Connection DBcon,
            HttpServletRequest request, String y1, String y2)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBaep> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBaep>();

        // Obtener matriz con los artículos expedidos 
        // en los dos últimos ejercicios      
        try {
            pst = DBcon.prepareStatement(
                    "SELECT ARTDES,"
                    + " " + "CANSER_Y1,"
                    + " " + "CANSER_Y2"
                    + " " + "FROM"
                    + " " + "(SELECT A.ARTDES,"
                    + " " + "(SELECT SUM(L.CANSER)"
                    + " " + "FROM MV_AEP L"
                    + " " + "WHERE L.ARTDES = A.ARTDES"
                    + " " + "AND L.AÑO =" + " " + "'" + y1 + "')" + " " + "AS CANSER_Y1,"
                    + " " + "(SELECT SUM(M.CANSER)"
                    + " " + "FROM MV_AEP M"
                    + " " + "WHERE M.ARTDES = A.ARTDES"
                    + " " + "AND M.AÑO =" + " " + "'" + y2 + "')" + " " + "AS CANSER_Y2"
                    + " " + "FROM MV_AEP A"
                    + " " + "GROUP BY A.ARTDES)"
                    + " " + "WHERE CANSER_Y1 IS NOT NULL"
                    + " " + "AND CANSER_Y2 IS NOT NULL"
                    + " " + "ORDER BY CANSER_Y2 DESC"
                    + " " + "FETCH FIRST 20 ROWS ONLY");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBaep(
                        rs.getString("ARTDES"),
                        rs.getInt("CANSER_Y1"),
                        rs.getInt("CANSER_Y2")));
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
