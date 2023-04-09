/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Publication;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author Omar
 */
public interface Iservicepublication<P> {
  
    public void addpub(P p);
  // public List<P> affpub();
   public List<Publication> affpub() throws SQLException;
  //  public void suppub(P p);
    public void suppub(int id)throws SQLException;
    public void editpub(P p) throws SQLException;
}
