/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Commentaire;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Omar
 */
public interface Iservicecommentaire <C>{
    
    
    public void addcom(C c);
   public List<Commentaire> affcom() throws SQLException;
    public void supcom(int id)throws SQLException;
    public void editcom(C c) throws SQLException;
    
    
}
