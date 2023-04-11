/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class LotuscareConnexion {
    public static final String url = "jdbc:mysql://localhost:3306/lotuscare_db";
    public static final String user = "root";
    public static final String pwd = "";
    private Connection cnx;
    public static LotuscareConnexion ct;

    private LotuscareConnexion() {
        try {
            this.cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/lotuscare_db", "root", "");
            System.out.println("Connection etablie !!");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

    }

    public static LotuscareConnexion getInstance() {
        if (ct == null) {
            ct = new LotuscareConnexion();
        }

        return ct;
    }

    public Connection getCnx() {
        return this.cnx;
    }
}