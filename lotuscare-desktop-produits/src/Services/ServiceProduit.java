package Services;

import Interfaces.ProduitInterface;
import Tools.LotuscareConnexion;
import entities.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceProduit implements ProduitInterface<Produit> {
    Connection cnx;
    Statement ste;
    public ServiceProduit() {
        cnx = LotuscareConnexion.getInstance().getCnx();
    }



    @Override
    public void modifyProduct(Produit t) {
        try {
            String qry ="UPDATE `produits` SET  `ref= '"+ t.getRef()+ "' , `description`='  "+ t.getDescription() + "',  `image`= '"+ t.getImage()+ "' ,  `prix`= '"+ t.getPrix()+ "' ,`qte_stock`= '"+ t.getQte_stock()+"' WHERE `id`='" + t.getId()+ "'";
            cnx = LotuscareConnexion.getInstance().getCnx();

            Statement stm =cnx.createStatement();

            stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Produit> showProduct() {
        List<Produit> p = new ArrayList<Produit>();
        try {
            String req = "SELECT * FROM `game`";
            ste = cnx.createStatement();
            ResultSet result = ste.executeQuery(req);
            System.out.println(result);
            while (result.next()) {
                Produit resultP = new Produit( result.getString("ref"), result.getString("description"),result.getString("image"),result.getFloat("prix"),result.getInt("qte_stock"));
                p.add(resultP);
            }
            System.out.println(p);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;
    }



    @Override
    public Produit addProduct(Produit t) {

        String sql = "INSERT INTO `produits`(`ref`, `description`, `image`, `prix`, `qte_stock`) VALUES (?,?,?,?,?)";
        PreparedStatement ste;
        try {
            ste = cnx.prepareStatement(sql);
            ste.setString(1, t.getRef());
            ste.setString(2, t.getDescription());
            ste.setString(3, t.getImage());
            ste.setFloat(4, t.getPrix());
            ste.setInt(5, t.getQte_stock());
            //ste.setString(1, c.getCode_cat());

            ste.executeUpdate();
            System.out.println("Produit Ajouté ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    @Override
    public void removeProduct(int id) {
        try {
            String req = "DELETE FROM `produits` WHERE id = " + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("product deleted!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}
