/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package querys;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author egimenez
 */
public class DBconnection {

    // Atributos
    private Connection DBcon;

    // Obtener la conexión a la Base de Datos
    public Connection conBDD(HttpServletRequest request)
            throws ServletException, IOException {

        // Definiciones locales
        Properties p;
        String webAppPath;
        String jdbcURL;
        String DBuser;
        String DBpass;

        // Init.conexión a la Base de Datos
        this.DBcon = null;

        // Determinar info.conexión a la Base de Datos    
        p = new Properties();
        webAppPath = System.getProperty("webAppPath");
        try {
            p.load(new FileInputStream(webAppPath + "/config/config.ini"));
        } catch (FileNotFoundException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "No hallado el archivo config.ini</div>");
        } catch (IOException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "Error de lectura del archivo config.ini</div>");
        }
        jdbcURL = p.getProperty("JDBC_URL");
        DBuser = p.getProperty("DB_USER");
        DBpass = p.getProperty("DB_PASS");

        // Registrar la clase JDBC driver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "Error en lectura del archivo config.ini</div>");
        }

        // Abrir la conexión a la Base de Datos
        try {
            this.DBcon = DriverManager.getConnection(jdbcURL, DBuser, DBpass);
        } catch (SQLException e) {
            request.setAttribute("txmsj",
                    "<div class=\"alert alert-danger\" role=\"alert\">"
                    + "No fue posible abrir la Base de Datos</div>");
        }

        // Devolver la conexión a la Base de Datos
        return this.DBcon;
    }
}
