package Interfaces;

import entities.Categorie;

import java.util.List;

public interface CategorieInterface<C> {
    public Categorie addCategory(C c);
    //public void removeCategory();
    public void modifyCategory(C c);
    public List<C> showCategory();
}
