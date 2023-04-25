/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Produit;

import java.util.List;



public interface InterfaceProduit {
    public void ajouterProduit(Produit p );

    public void modifierProduit(Produit p);


    void supprimerProduit(Produit P);

    public List<Produit> afficherProduit();
    public List<Produit> getProduitByCategorie(int id);
    public List<Produit> getProduitByREF(String titre);
}
