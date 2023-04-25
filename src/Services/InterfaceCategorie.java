
package services;

import entities.Categorie;
import entities.Produit;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceCategorie {
    public void ajoutercategorie(Categorie c);
      public List<Categorie> afficherCategories();

    List<String> getNomsCat() throws SQLException;
}
