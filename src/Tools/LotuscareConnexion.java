/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author Nour
 */
public class LotuscareConnexion {

    public static LotuscareConnexion instance;
    private Connection cnx;

    private LotuscareConnexion() {
        try {
            cnx = DriverManager.getConnection(Statics.url, Statics.user, Statics.pwd);
            System.out.println("Connection etablie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static LotuscareConnexion getInstance() {
        if (instance == null) {
            instance = new LotuscareConnexion();
        }
        return instance;

    }

    public Connection getCnx() {
        return cnx;
    }
}
