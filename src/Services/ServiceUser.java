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
import java.util.ArrayList;
import java.util.List;
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
                        result = "failed init storage :" + ex.getMessage();
                    }

                } else {
                    System.out.println("mdp incorrecte");
                     result = "mot de passe incorrect";
                }

            } else {
                System.out.println("email introuvable");
                result = "email introuvable";

            }

        } catch (SQLException ex) {
            System.out.println("exeption sql :" + ex.getMessage());
            result = "exeption sql :" + ex.getMessage();
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
                    + "     VALUES (NULL, '" + user.getNom() + "', '" + user.getPrenom() + "', '" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getTelephone() + "', '" + user.getCin() + "', '" + user.getRoles() + "', '0','" + user.getImage() + "' ,' ')";

            stm.executeUpdate(query);
            System.out.println("User ajoutée");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerUtilisateur(int id) {
        try {
            String sql = "DELETE FROM user WHERE id=" + id;
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate();
            System.out.println("User Supprimée ");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifierUtilisateur(User U) {
        String sql = "UPDATE user SET cin = ?,nom = ?, prenom= ?, telephone= ?, roles= ?, email= ?, image= ? WHERE id = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, U.getCin());
            ste.setString(2, U.getNom());
            ste.setString(3, U.getPrenom());
            ste.setString(4, U.getTelephone());
            ste.setString(5, U.getRoles());
            ste.setString(6, U.getEmail());
            ste.setString(7, U.getImage());
            ste.setInt(8, U.getId());
            ste.executeUpdate();
            System.out.println("utilisateur modifié avec succées");
            updateCurrentUser(U);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
        @Override
    public void changePassword(String email,String password) {
        String sql = "UPDATE user SET password = ? WHERE email = ?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
        String hashedPassword = passwordEncoder.encode(password);
            ste.setString(1, hashedPassword);
            ste.setString(2, email);
            ste.executeUpdate();
            System.out.println("utilisateur modifié avec succées");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ModifierRoleById(String role, int id) {//autoincrement
        String sql = "update user set roles=? where  id=?";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, role);
            ste.setInt(2, id);
            int value = ste.executeUpdate();
            if (value > 0) {
                System.out.println("role modifié avec succées");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> getUsers() throws SQLException {
        Statement stm = cnx.createStatement();
        String query = "SELECT  *  FROM `user`";

        ResultSet rs = stm.executeQuery(query);
        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setCin(rs.getString("cin"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setTelephone(rs.getString("telephone"));
            user.setPassword(rs.getString("password"));
            user.setRoles(rs.getString("roles"));
            user.setEmail(rs.getString("email"));
            user.setImage(rs.getString("image"));
            users.add(user);
        }
        return users;

    }

}
