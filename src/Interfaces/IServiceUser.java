/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Models.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nour
 */
public interface IServiceUser {


    public String login(String email, String password);
    public void addUser(User user);
    
    public List<User> getUsers() throws SQLException;
    public void modifierUtilisateur(User U);
    public void ModifierRoleById(String role, int id);
    public void supprimerUtilisateur(int id);
    public void changePassword(String email,String password);
}
