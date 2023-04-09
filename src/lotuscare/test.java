/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lotuscare;

import Services.ServiceCommentaire;
import java.sql.SQLException;

/**
 *
 * @author Omar
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       ServiceCommentaire com= new ServiceCommentaire();
        System.out.println(com.affcom());
    }
    
}
