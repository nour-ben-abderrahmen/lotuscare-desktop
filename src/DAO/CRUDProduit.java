/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DB.DBConnection;
import entities.Categorie;
import entities.GenerateQRCode;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import services.InterfaceProduit;

import javax.mail.*;

public class CRUDProduit implements InterfaceProduit {
    Statement ste;
    Connection conn = DBConnection.getInstance().getConnection();
    List<Categorie>categorie=new ArrayList<>();
    public final String SELECT_CATEGORIE_BY_ID = "SELECT * FROM categories WHERE id = ? LIMIT 1";
    private final String Select_Produit="SELECT * FROM `produits`";
    private final String SELECT_PRODUIT_BY_CATEGORIE="SELECT * FROM produits WHERE categorie_id=? ";
    private final String SELECT_PRODUIT_BY_REF="SELECT *FROM produits WHERE ref= ?";
    private final String UPDATE_PRODUCT="UPDATE `produits` SET `ref` = ?, `description` = ?,`prix`=?, `image` = ?, `qte_stock` = ?, WHERE `produits`.`id` = ? ";
    //private final String DELETE_PRODUCT="DELETE FROM `produits` WHERE (id = ? )";
    private final String SELECT_PRODUCT_DEC="SELECT * FROM `produits` group BY prix DESC";
    @Override
    public void ajouterProduit(Produit p ) {
    try {
        ste = conn.createStatement();
        String req = "Insert into produits (`ref`,`description`,`prix`,`image`,`qte_stock`,`categorie_id`) values('"+p.getRef()+"','"+p.getDescription()+"','"+p.getPrix()+"','"+p.getImage()+ "', '"+ p.getQte_stock()+"','"+ p.getCategorie().getId()+"')";

        ste.executeUpdate(req);
        System.out.println("Produit ajouté");
    } catch (SQLException ex) {
            System.out.println("produit non ajouté!!!!");
            System.out.println(ex.getMessage());
    }
    }


    public void ajouterProduit(Produit p, int categorieId) throws MessagingException {
        try {
            String req = "INSERT INTO produits (`ref`,`description`,`prix`,`image`,`qte_stock`,`categorie_id`) " +
                    "SELECT '"+p.getRef()+"','"+p.getDescription()+"','"+p.getPrix()+"','"+p.getImage()+"','"+p.getQte_stock()+"','"+categorieId+"' " +
                    "FROM categories " +
                    "WHERE categories.id = ?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setInt(1, categorieId);
            ps.executeUpdate();
            System.out.println("Produit ajouté");
        } catch (SQLException ex) {
            System.out.println("produit non ajouté!!!!");
            System.out.println(ex.getMessage());
        }


        String outputPath = "C:/Users/ASUS/Documents/qr/Output.png";
        try {
            GenerateQRCode.generateQRcode(p, outputPath);
            System.out.println("QR code generated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to generate QR code: " + e.getMessage());
        }
        // Send an email notification
        //Your Twilio Password is : gnuFA^6L63DNs!j1234




    }


    public void modifierProduit(Produit p) {
        try {
            String req = "UPDATE `produits` SET `ref` = ?, `description` = ?,`prix`=?, `image` = ?, `qte_stock` = ?, WHERE `produits`.`id` = ? " + p.getId();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_PRODUCT);
            stmt.setString(1, p.getRef());
            stmt.setString(2,p.getDescription());
            stmt.setFloat(3, p.getPrix());
            stmt.setString(4, p.getImage());
            stmt.setInt(5,p.getQte_stock());
            Statement st = conn.createStatement();
            stmt.executeUpdate();
            System.out.println("Produit updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     @Override
    public void supprimerProduit(Produit P) {
         String req = "delete * from produits where id=?";

         try {
             PreparedStatement stmt = conn.prepareStatement(req);
             stmt.setInt(1, (int) P.getId());
             int i = stmt.executeUpdate();
             System.out.println(i + " produit suprimé");
         } catch (SQLException ex) {
             System.out.println(ex.getMessage());
         }
     }



    public Categorie getCategorieById(int id) {
        
        Categorie categorie = new Categorie();
        
        try{
            ste = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement(SELECT_CATEGORIE_BY_ID);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                categorie.setId(rs.getInt("id"));
                categorie.setCode_cat(rs.getString("Code Categorie"));
                categorie.setNom_cat(rs.getString("Nom Categorie"));

            }
            
        }catch(SQLException sQLException){
            System.out.println(sQLException.getMessage());
        }catch(IllegalArgumentException illegalArgumentException){
            System.out.println(illegalArgumentException.getMessage());
        }catch(NullPointerException nullPointerException){
            System.out.println(nullPointerException.getMessage());
        }
        
        return categorie;
    }
    public List<Produit> afficherProduit() {
    List<Produit> prod = new ArrayList<Produit>();
        try {
        ste = conn.createStatement();
        ResultSet result = ste.executeQuery(Select_Produit);

        while (result.next()) {
            Produit resultProduit = new Produit(
                    //result.getInt("id"),
                    result.getString("ref"),
                    result.getString("description"),
                    result.getFloat("prix"),
                    result.getString("image"),
                    result.getInt("qte_stock"),
                    result.getInt("categorie_id"));

            prod.add(resultProduit);
        }
        System.out.println(prod);
      
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
   return prod;
 }
    public List<Produit> getProduitByCategorie(int id) {
        
        List<Produit> prod = new ArrayList<Produit>();
        
        try{
            ste = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUIT_BY_CATEGORIE);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            
          while (result.next()) {
            Produit resultProduit = new Produit(
                    result.getInt("id"),
                    result.getString("ref"),
                    result.getString("description"),
                    result.getFloat("prix"),
                    result.getString("image"),
                    result.getInt("qte_stock"),
                    getCategorieById(result.getInt("categorie_id")));
            prod.add(resultProduit);
        }
        System.out.println(prod);
      
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
   return prod;
    }
    public List<Produit> getProduitByREF(String titre) {
        
        List<Produit> prod = new ArrayList<Produit>();
        
        try{
            ste = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement(SELECT_PRODUIT_BY_REF);
            stmt.setString(1, titre);
            ResultSet result = stmt.executeQuery();
            
          while (result.next()) {
            Produit resultProduit = new Produit(
                    result.getInt("id"),
                    result.getString("ref"),
                    result.getString("description"),
                    result.getFloat("prix"),
                    result.getString("image"),
                    result.getInt("qte_stock"),
                    getCategorieById(result.getInt("categorie_id")));
            prod.add(resultProduit);
        }
        System.out.println(prod);
      
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
   return prod;
    }
//    public List<Produit> AfficherProduitDECroissant(){
//        List<Produit> produitCoissant = new ArrayList<Produit>();
//        produitCoissant = afficherProduit().stream().
//                filter(List(o1, o2) -> o1. - o2.getPrice() )
//                .collect(Collectors.toList());
//      return produitCoissant;
//      
//    }
    
}
