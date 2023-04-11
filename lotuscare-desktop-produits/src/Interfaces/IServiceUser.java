/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Models.User;
import java.sql.SQLException;

/**
 *
 * @author Nour
 */
public interface IServiceUser {


    public String login(String email, String password);
    public void addUser(User user);
}
