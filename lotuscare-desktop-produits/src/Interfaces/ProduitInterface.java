package Interfaces;
import entities.Produit;

import java.util.List;
public interface ProduitInterface <T> {
    public Produit addProduct(T t);
    public void removeProduct(int id);
    public void modifyProduct(T t);
    public List<T> showProduct();
    //public List<T> getByName(String description);
    //public List<T> getByCategory(String nom_cat);
    //public T getById(int id);
}
