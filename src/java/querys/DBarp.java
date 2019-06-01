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
public class DBarp {

    // Atributos
    private String ARTDES;
    private int CANREC_Y1;
    private int CANREC_Y2;

    // Constructor 1
    public DBarp() {

    }

    // Constructor 2
    public DBarp(String ARTDES, int CANREC_Y1, int CANREC_Y2) {
        this.ARTDES = ARTDES;        
        this.CANREC_Y1 = CANREC_Y1;
        this.CANREC_Y2 = CANREC_Y2;
    }

    // Setters y getters
    public String getARTDES() {
        return ARTDES;
    }

    public void setARTDES(String ARTDES) {
        this.ARTDES = ARTDES;
    }

    public int getCANREC_Y1() {
        return CANREC_Y1;
    }

    public void setCANREC_Y1(int CANREC_Y1) {
        this.CANREC_Y1 = CANREC_Y1;
    }

    public int getCANREC_Y2() {
        return CANREC_Y2;
    }

    public void setCANREC_Y2(int CANREC_Y2) {
        this.CANREC_Y2 = CANREC_Y2;
    }

    // Obtener matriz con los artículos recibidos 
    // en los dos últimos ejercicios
    public ArrayList<DBarp> ArtRec(Connection DBcon,
            HttpServletRequest request, String y1, String y2)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBarp> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBarp>();

        // Obtener matriz con los artículos recibidos 
        // en los dos últimos ejercicios      
        try {
            pst = DBcon.prepareStatement(
                    "SELECT ARTDES,"
                    + " " + "CANREC_Y1,"
                    + " " + "CANREC_Y2"
                    + " " + "FROM"        
                    + " " + "(SELECT A.ARTDES,"
                    + " " + "(SELECT SUM(L.CANREC)"
                    + " " + "FROM MV_ARP L"
                    + " " + "WHERE L.ARTDES = A.ARTDES"
                    + " " + "AND L.AÑO =" + " " + "'" + y1 + "')" + " " + "AS CANREC_Y1,"
                    + " " + "(SELECT SUM(M.CANREC)"
                    + " " + "FROM MV_ARP M"
                    + " " + "WHERE M.ARTDES = A.ARTDES"
                    + " " + "AND M.AÑO =" + " " + "'" + y2 + "')" + " " + "AS CANREC_Y2"
                    + " " + "FROM MV_ARP A"
                    + " " + "GROUP BY A.ARTDES)"
                    + " " + "WHERE CANREC_Y1 IS NOT NULL"
                    + " " + "AND CANREC_Y2 IS NOT NULL"
                    + " " + "ORDER BY CANREC_Y2 DESC"        
                    + " " + "FETCH FIRST 20 ROWS ONLY");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBarp(
                        rs.getString("ARTDES"),                        
                        rs.getInt("CANREC_Y1"),
                        rs.getInt("CANREC_Y2")));
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
