/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Models.Publication;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Tools.Statics;
import Tools.LotuscareConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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
    public ObservableList<Publication> affpub() {
        ObservableList<Publication>pub=FXCollections.observableArrayList();
        String sql="select * from publication";
        try {
            Statement ste=cnx.createStatement();
            ResultSet sp= ste.executeQuery(sql);
            while(sp.next()){
                Publication p=new Publication(sp.getString(1), sp.getString(2));
                pub.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pub;  
    }

    @Override
    public void suppub(Publication p) {
        String sql="delete from publication where id=?";
        try {
            PreparedStatement ste=cnx.prepareStatement(sql);
            ste.setInt(1, p.getId());
            ste.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }  
    }

    public void supppub(int id) {
        String sql="delete from publication where id=?";
        try{
            PreparedStatement ste=cnx.prepareStatement(sql);
            ste.setInt(1, id);
            ste.executeUpdate();
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}