/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.util.Objects;


public class Produit {
    private int id;
    private String ref;
    private String description;
    private float prix;
    private String image;
    private int qte_stock;
    private Categorie categorie;
private   int id_cat;

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    //ref,description,prix,image,qte_stock,CategorieId


    public Produit( String ref, String description,  float prix,String image, int qte_stock,int id_cat ) {
        this.id = id;
        this.ref = ref;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.qte_stock = qte_stock;
        this.categorie = categorie;
    }

    public Produit(int id, String ref, String description,  float prix,String image, int qte_stock, Categorie categorie) {
        this.id = id;
        this.ref = ref;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.qte_stock = qte_stock;
        this.categorie = categorie;
    }

    public Produit(int id, String ref, String description, float prix, String image, int qte_stock) {
        this.id = id;
        this.ref = ref;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.qte_stock = qte_stock;
    }

    public Produit(String ref, String description, float prix, String image , int qte_stock, Categorie categorie) {
        this.ref = ref;
        this.description = description;
        this.prix = prix;
        this.image = image;

        this.qte_stock = qte_stock;
        this.categorie = categorie;
    }


    public Produit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte_stock() {
        return qte_stock;
    }

    public void setQte_stock(int qte_stock) {
        this.qte_stock = qte_stock;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", qte_stock=" + qte_stock +
                ", categorie=" + categorie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id == produit.id && ref.equals(produit.ref) && categorie.equals(produit.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref, categorie);
    }
}
