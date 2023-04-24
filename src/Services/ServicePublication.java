/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Models.Publication;
import Tools.LotuscareConnexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

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
        String sql="insert into publication (Code_pub,Contenu_pub,Url_image_pub) values (?,?,?)";
        PreparedStatement ste;
        try {
            ste = cnx.prepareStatement(sql);
            ste.setString(1, p.getCode_pub());
            ste.setString(2, p.getContenu_pub());
            ste.setString(3, p.getUrl_image_pub());
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
     
     
        public ObservableList<Publication> afficher2() {
      ObservableList<Publication> Publications = FXCollections.observableArrayList();
            String qry = "SELECT * FROM `publication`";
        
    try {
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(qry);
         
        while (rs.next()){
            
                Publication h = new Publication();
                h.setId(rs.getInt("id"));
                h.setCode_pub(rs.getString("code_pub"));
                h.setContenu_pub(rs.getString("contenu_pub"));
                h.setUrl_image_pub(rs.getString("Url_image_pub"));                
                Publications.add(h);
        }
                 
        return Publications;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
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
            String req = "UPDATE publication SET code_pub = '"+ p.getCode_pub()+ "',`contenu_pub` = '" +p.getContenu_pub()+ "',`url_image_pub` = '" +p.getUrl_image_pub()+ "' WHERE publication.`id` = " +p.getId();
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
                    rs.getString("contenu_pub"),
                    rs.getString("url_image_pub")
            );
            return c;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
    
    public List<Publication> rechercherpub(String critere) throws SQLException {
   
    String req = "SELECT * FROM publication WHERE code_pub LIKE ? OR contenu_pub LIKE ? ";
    Statement ps = cnx.createStatement();
       
    PreparedStatement ste = cnx.prepareStatement(req);
    ste.setString(1, "%" + critere + "%");
    ste.setString(2, "%" + critere + "%");
    ResultSet rs = ste.executeQuery();
    List<Publication> Publications = new ArrayList<>();
   
    while (rs.next()) {
        Publication r = new Publication(rs.getString("code_pub"),
                    rs.getString("contenu_pub")
                    );
        Publications.add(r);
    }
    return Publications;
}
   
    
    
    public ObservableList<Publication> Stat() {
    ObservableList<Publication> list = FXCollections.observableArrayList();
    try {
        String sql = "SELECT p.id,p.code_pub, p.contenu_pub " +
                     "FROM publication p " +
                     "JOIN commentaire c ON p.id = c.publication_id " +
                     "GROUP BY p.id " +
                     "ORDER BY COUNT(p.id) DESC " +
                     "LIMIT 3";
        PreparedStatement statement = cnx.prepareStatement(sql);
        ResultSet s = statement.executeQuery();

        // Récupération des valeurs de chaque ligne de résultat
        while (s.next()) {
            int pubId = s.getInt("id");
            String titre = s.getString("code_pub");
            String description = s.getString("contenu_pub");
            Publication e = new Publication(pubId , titre, description);
            list.add(e);
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}
  
  public int countParticipations(int id) {
    int count = 0;
    try {
        String sql = "SELECT COUNT(*) AS count FROM commentaire WHERE publication_id  = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            count = rs.getInt("count");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return count;
}
  
  
}

    
