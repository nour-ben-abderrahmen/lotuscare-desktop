/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Commentaire;
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
public class ServiceCommentaire implements Iservicecommentaire <Commentaire>{
    Connection cnx;
   
    public ServiceCommentaire(){
        cnx=LotuscareConnexion.getInstance().getCnx();
    }
   
    @Override
    public void addcom(Commentaire c) {
        String sql="insert into commentaire (contenu_comm,publication_id) values (?,?)";
        PreparedStatement ste;
        try {
            ste = cnx.prepareStatement(sql);
            ste.setString(1, c.getContenu_comm());
            ste.setInt(2, c.getPublication_id().getId());
            ste.executeUpdate();
            System.out.println("commentaire Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    
    
    
    @Override
    public List<Commentaire> affcom() throws SQLException {    
         List<Commentaire> Commentaires = new ArrayList<>();
        String req = "select * from commentaire";
        Statement ste = cnx.createStatement();
     
        ResultSet rst = ste.executeQuery(req);
         
        ServicePublication pub= new ServicePublication();
        while (rst.next()) {
            Commentaire re = new Commentaire( 
                    rst.getString("contenu_comm"),
                    pub.getPubParId(rst.getInt("publication_id"))
          );
            Commentaires.add(re);
        }
        return Commentaires;
        
    }

  
    @Override
    public void supcom(int id) throws SQLException {
        
        int num  = id;

         String req = "DELETE FROM commentaire WHERE id = ?";
         PreparedStatement ps = cnx.prepareStatement(req);
         ps.setInt(1, num);

      // Exécution de la requête
      int nbLignesSupprimees = ps.executeUpdate();
      System.out.println("Nombre de lignes supprimées : " + nbLignesSupprimees);
    }

    @Override
    public void editcom(Commentaire c) throws SQLException {
       try {
            String req = "UPDATE commentaire SET contenu_comm = '"+ c.getContenu_comm()+ "',`publication_id` = '" +c.getPublication_id().getId()+ "' WHERE commentaire.`id` = " +c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("commentaire updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
    }
}
}