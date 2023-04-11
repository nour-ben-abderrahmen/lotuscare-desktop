/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Models.Publication;
import Tools.LotuscareConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omar
 */

public class ServicePublication implements Iservicepublication <Publication>{
    Connection cnx;
   
    public ServicePublication(){
        cnx=LotuscareConnexion.getInstance().getCnx();
    }
   
    @Override
    public void addpub(Publication p) {
        String sql="insert into publication (Code_pub,Contenu_pub) values (?,?)";
        PreparedStatement ste;
        try {
            ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getCode_pub());
            ste.setString(2, p.getContenu_pub());
            ste.executeUpdate();
            System.out.println("Publication Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }
    
     @Override
     public List<Publication> affpub() throws SQLException {
        List<Publication> Publications = new ArrayList<>();
        String req = "select * from publication";
        Statement ste = cnx.createStatement();
     
        ResultSet rst = ste.executeQuery(req);

        while (rst.next()) {
            Publication re = new Publication( 
                    rst.getInt("id"),
                    rst.getString("code_pub"),
                    rst.getString("contenu_pub")
          );
            Publications.add(re);
        }
        return Publications;
    }
    
    /**
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void suppub(int id) throws SQLException {
     
     
        int num  = id;

         String req = "DELETE FROM publication WHERE id = ?";
         PreparedStatement ps = cnx.prepareStatement(req);
         ps.setInt(1, num);

      // Exécution de la requête
      int nbLignesSupprimees = ps.executeUpdate();
      System.out.println("Nombre de lignes supprimées : " + nbLignesSupprimees);
    }

    @Override
    public void editpub(Publication p) throws SQLException {
    try {
            String req = "UPDATE publication SET code_pub = '"+ p.getCode_pub()+ "',`contenu_pub` = '" +p.getContenu_pub()+ "' WHERE publication.`id` = " +p.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("publication updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
}
    }
    
    
    
    
    public Publication getPubParId(int id) {
    String sql = "SELECT * FROM publication WHERE id = ?";
    PreparedStatement ste;
    try {
        ste = cnx.prepareStatement(sql);
        ste.setInt(1, id);
        ResultSet rs = ste.executeQuery();
        if (rs.next()) {
            Publication c = new Publication(
                    rs.getInt("id"),
                    rs.getString("code_pub"),
                    rs.getString("contenu_pub")
            );
            return c;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
}

    
