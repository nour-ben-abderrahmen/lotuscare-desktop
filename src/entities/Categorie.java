/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.Objects;


public class Categorie {
    private int id;
    private String code_cat;
    private String nom_cat;
    private ArrayList<Produit> produit;

    public Categorie() {
    }

    public Categorie(String code_cat, String nom_cat) {
        this.code_cat = code_cat;
        this.nom_cat = nom_cat;
    }

    public Categorie(String code_cat, String nom_cat, ArrayList<Produit> produit) {
        this.code_cat = code_cat;
        this.nom_cat = nom_cat;
        this.produit = produit;
    }

    public Categorie(int id, String code_cat, String nom_cat) {
        this.id = id;
        this.code_cat = code_cat;
        this.nom_cat = nom_cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode_cat() {
        return code_cat;
    }

    public void setCode_cat(String code_cat) {
        this.code_cat = code_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    @Override
   /* public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code_cat='" + code_cat + '\'' +
                ", nom_cat='" + nom_cat + '\'' +
                '}';
    }
*/
    public String toString() {
        return  code_cat;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.code_cat);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categorie other = (Categorie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.code_cat, other.code_cat)) {
            return false;
        }
        return true;
    }
    
}
