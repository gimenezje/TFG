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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author enrique
 */
public class DBchkCred {

    // Verificar credenciales
    public boolean chkCred(Connection DBcon, String usr,
            String pas, HttpServletRequest request)
            throws ServletException, IOException {

        // Definiciones locales
        String DBpas;
        boolean ok;
        PreparedStatement pst;
        ResultSet rs;

        // Init.flag
        ok = false;

        // Obtener y verificar password
        DBpas = null;
        try {
            // Obtener password
            pst = DBcon.prepareStatement(
                    "SELECT PAS"
                    + " " + "FROM CRED"
                    + " " + "WHERE USR = '" + usr + "'");
            rs = pst.executeQuery();
            while (rs.next()) {
                DBpas = rs.getString("PAS");
            }
            pst.close();
            rs.close();

            // Verificar password        
            if (DBpas != null && pas.equals(DBpas.trim())) {
                ok = true;
            } else {
                request.setAttribute("txmsj",
                        "<div class=\"alert alert-danger\" role=\"alert\">"
                        + "Credenciales inválidas</div>");
            }
        } catch (SQLException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "No fue posible verificar las credenciales en la Base de Datos</div>");
        }

        // Devolución flag
        return ok;
    }    
}
