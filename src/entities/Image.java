/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;

/**
 *
 * @author Mohamed Ali
 */
public class Image {
    private int id;
    private String image_url;
    private int produit_id;

    public Image() {
    }

    public Image(String image_url) {
        this.image_url = image_url;
    }

    
    
    public Image(int id, String image_url, int produit_id) {
        this.id = id;
        this.image_url = image_url;
        this.produit_id = produit_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.image_url);
        hash = 17 * hash + this.produit_id;
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
        final Image other = (Image) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.produit_id != other.produit_id) {
            return false;
        }
        if (!Objects.equals(this.image_url, other.image_url)) {
            return false;
        }
        return true;
    }
    
}
