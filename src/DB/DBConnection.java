/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private  Connection  conn;
   String url = "jdbc:mysql://localhost:3306/lotuscare_db";
   String user = "root";
   String pwd = "";
   private static DBConnection instance;
    private DBConnection() {

        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion etablie!!!");
        } catch (SQLException ex) {
            System.out.println("probleme de Connexion");
        }}

  public static DBConnection getInstance(){
        if (DBConnection.instance == null) {
            DBConnection.instance = new DBConnection();
        }
        return DBConnection.instance;

    }
  public static Connection getConnection() {
        return DBConnection.getInstance().conn;
    }
}
