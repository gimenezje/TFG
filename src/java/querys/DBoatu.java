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
public class DBoatu {

    // Atributos
    private String UBITIP;
    private int UBILL;
    private int UBIVA;

    // Constructor 1
    public DBoatu() {

    }

    // Constructor 2
    public DBoatu(String UBITIP, int UBILL, int UBIVA) {
        this.UBITIP = UBITIP;
        this.UBILL = UBILL;
        this.UBIVA = UBIVA;
    }

    // Setters y getters 
    public String getUBITIP() {
        return UBITIP;
    }

    public void setUBITIP(String UBITIP) {
        this.UBITIP = UBITIP;
    }

    public int getUBILL() {
        return UBILL;
    }

    public void setUBILL(int UBILL) {
        this.UBILL = UBILL;
    }

    public int getUBIVA() {
        return UBIVA;
    }

    public void setUBIVA(int UBIVA) {
        this.UBIVA = UBIVA;
    }

    // Obtener matriz con la ocupación del almacén
    // por tipo de ubicación
    public ArrayList<DBoatu> OcupAlmXTipoUbic(Connection DBcon,
            HttpServletRequest request)
            throws ServletException, IOException {

        // Definiciones locales
        ArrayList<DBoatu> data;
        PreparedStatement pst;
        ResultSet rs;

        // Init.matriz a devolver
        data = new ArrayList<DBoatu>();

        // Obtener matriz con la ocupación del almacén
        // por tipo de ubicación 
        try {
            pst = DBcon.prepareStatement(
                    "(SELECT (CASE UBITIP"
                    + " " + "WHEN 'PI' THEN 'PICKING'"
                    + " " + "WHEN 'CM' THEN 'NORMALES'"
                    + " " + "WHEN 'PR' THEN 'MUELLES EXP.'"
                    + " " + "WHEN 'RE' THEN 'MUELLES REC.'"
                    + " " + "END) AS UBITIP,"
                    + " " + "SUM(CASE UBIEST WHEN 'LL' THEN 1 ELSE 0 END) AS UBILL,"
                    + " " + "SUM(CASE UBIEST WHEN 'VA' THEN 1 ELSE 0 END) AS UBIVA"
                    + " " + "FROM ALMUBI"
                    + " " + "WHERE UBITIP NOT LIKE 'F%'"
                    + " " + "GROUP BY UBITIP)"
                    + " " + "UNION ALL"
                    + " " + "(SELECT 'TOTAL' AS UBITIP,"
                    + " " + "SUM(CASE UBIEST WHEN 'LL' THEN 1 ELSE 0 END) AS UBILL,"
                    + " " + "SUM(CASE UBIEST WHEN 'VA' THEN 1 ELSE 0 END) AS UBIVA"
                    + " " + "FROM ALMUBI"
                    + " " + "WHERE UBITIP NOT LIKE 'F%')");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DBoatu(
                        rs.getString("UBITIP"),
                        rs.getInt("UBILL"),
                        rs.getInt("UBIVA")));
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
