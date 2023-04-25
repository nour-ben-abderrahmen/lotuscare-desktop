/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.DBConnection;
import entities.Image;
import entities.Produit;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import services.InterfaceImage;

/**
 *
 * @author Mohamed Ali
 */
public class CRUDImage implements InterfaceImage  {
    Statement ste;
    Connection conn = DBConnection.getInstance().getConnection();
    
    public void ajouterImage(Image g,Produit p) {
    try {
        ste = conn.createStatement();
        String req = "Insert into image_produit(`image_url`, `produit_id`) values('"+g.getImage_url()+"','" +p.getId()+"')";
        System.out.println(req);
        ste.executeUpdate(req);
        System.out.println("Image ajouté");
    } catch (SQLException ex) {
            System.out.println("Image non ajouté!!!!");   
    System.out.println(ex.getMessage());  
    }
    }
}
