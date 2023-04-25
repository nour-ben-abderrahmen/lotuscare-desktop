/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.DBConnection;
import entities.Categorie;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.SendSMS;
import services.InterfaceCategorie;



public class CRUDCategorie implements InterfaceCategorie {
    Statement ste;
    Connection conn = DBConnection.getInstance().getConnection();
    private final String Select_Categorie ="SELECT * FROM `Categories`";
 @Override
public void ajoutercategorie(Categorie c) {
    try {
        ste = conn.createStatement();
        String req = "Insert into categories(`code_cat`,`nom_cat`) values('"+ c.getCode_cat()+"','"+ c.getNom_cat()+"')";
        ste.executeUpdate(req);
        System.out.println("Categorie ajouté");
    } catch (SQLException ex) {
            System.out.println("Categorie non ajouté!!!!");    }
     try {
         SendSMS sm = new SendSMS();
         sm.sendSMS(c);
         System.out.println("SMS envoyé avec succès");
     } catch (Exception e) {
         // handle the exception here
         e.printStackTrace();
     }
    }

 @Override
    public List<Categorie> afficherCategories() {
    List<Categorie> cats = new ArrayList<Categorie>();
        try {
            ste=conn.createStatement();
            ResultSet result = ste.executeQuery(Select_Categorie);
        
        while (result.next()) {
            Categorie resultCategories = new Categorie(result.getInt("id"), result.getString("code_cat"), result.getString("nom_cat"));
            cats.add(resultCategories);
        }
        System.out.println(cats);
      
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
   return cats;
 }
 @Override
    public List<String> getNomsCat() throws SQLException {
        String req = "SELECT nom_cat FROM categories";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(req);

        List<String> noms = new ArrayList<>();
        while (rs.next()) {
            noms.add(rs.getString("nom_cat"));
        }
        return noms;

    }
    public int getIdCategorieByName(String nom_cat) {
        try {
            this.ste = this.conn.createStatement();
            String req = "SELECT id FROM categories WHERE nom_cat='" + nom_cat + "'";
            ResultSet result = this.ste.executeQuery(req);
            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException var3) {
            System.out.println(var3);
        }
        return -1;
    }

}
