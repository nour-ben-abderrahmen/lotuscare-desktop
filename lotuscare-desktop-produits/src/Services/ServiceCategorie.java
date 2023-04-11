package Services;

import Interfaces.CategorieInterface;
import Tools.LotuscareConnexion;
import entities.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategorie implements CategorieInterface<Categorie> {
    Connection cnx;

    public ServiceCategorie() {
        cnx = LotuscareConnexion.getInstance().getCnx();
    }
    @Override
    public Categorie addCategory(Categorie c) {
        String sql = "INSERT INTO `categorie`(`code_cat`, `nom_cat`) VALUES (?,?)";
        PreparedStatement ste;
        try {
            ste = cnx.prepareStatement(sql);
            ste.setString(1, c.getCode_cat());
            ste.setString(2, c.getNom_cat());

            ste.executeUpdate();
            System.out.println("Categorie Ajoutée ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return c;    }

    @Override
    public void modifyCategory(Categorie c) {
        try {
            String qry ="UPDATE `categories` SET  `code_cat= '"+ c.getCode_cat()+ "' , `nom_cat`='  "+ c.getNom_cat() + "',  `image`= '"+"' WHERE `id`='" + c.getId()+ "'";
            cnx = LotuscareConnexion.getInstance().getCnx();

            Statement stm =cnx.createStatement();

            stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Categorie> showCategory() {
        List<Categorie> categories = new ArrayList<>();
        try {
            String qry = "SELECT * FROM `categories` ";
            cnx = LotuscareConnexion.getInstance().getCnx();
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                Categorie a = new Categorie();
                a.setId(rs.getInt(1));
                a.setCode_cat(rs.getString("code_cat"));
                a.setNom_cat(rs.getString("nom_cat"));

                categories.add(a);
            }
            return categories;


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }
}
