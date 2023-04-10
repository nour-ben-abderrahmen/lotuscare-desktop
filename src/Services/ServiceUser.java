/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Interfaces.IServiceUser;
import Models.User;
import Tools.LocalStorage;
import Tools.LotuscareConnexion;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 *
 * @author Nour
 */
public class ServiceUser implements IServiceUser {

    Connection cnx;
    public static User currentUser = new User();

    public ServiceUser() {
        cnx = LotuscareConnexion.getInstance().getCnx();
    }
//Fonction de hachage mot de passe md5 

    @Override
    public String login(String email, String password) {

        String sql = "Select * from user where email =?";
        String result = "failed";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);

            ste.setString(1, email);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);

                if (passwordEncoder.matches(password, hashedPassword)) {
                    result = "success";

                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setTelephone(rs.getString("telephone"));
                    user.setCin(rs.getString("cin"));
                    user.setRoles(rs.getString("roles"));
                    user.setVerified(rs.getInt("verified"));
                    user.setImage(rs.getString("image"));
                    user.setVerification_code(rs.getString("verification_code"));

                    updateCurrentUser(user);
                    try {
                        LocalStorage localStorage = new LocalStorage();
                        localStorage.writeToStorage(user); //bech yokood dima connecté
                    } catch (IOException ex) {
                        System.out.println("failed init storage :" + ex.getMessage());
                    }

                } else {
                    System.out.println("mdp incorrecte");
                }

            } else {

                System.out.println("email introuvable");

            }

        } catch (SQLException ex) {
            System.out.println("exeption sql :" + ex.getMessage());
        }
        return result;
    }

    public void updateCurrentUser(User user) {
        currentUser.setId(user.getId());
        currentUser.setNom(user.getNom());
        currentUser.setPrenom(user.getPrenom());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setTelephone(user.getTelephone());
        currentUser.setCin(user.getCin());
        currentUser.setRoles(user.getRoles());
        currentUser.setVerified(user.getVerified());
        currentUser.setImage(user.getImage());
        currentUser.setVerification_code(user.getVerification_code());
    }

    @Override
    public void addUser(User user) {
        


	
	
	
	

        try {
            Statement stm = cnx.createStatement();
            String query = "INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `password`, `telephone`, `cin`, `roles`, `verified`, `image`, `verification_code`)"
                    + "     VALUES (NULL, '" + user.getNom()+ "', '" + user.getPrenom()+ "', '" + user.getEmail()+ "', '" + user.getPassword()+ "', '" + user.getTelephone()+ "', '" + user.getCin()+ "', '" + user.getRoles()+ "', '0','" + user.getImage()+ "' ,' ')";

            stm.executeUpdate(query);
            System.out.println("User ajoutée");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
