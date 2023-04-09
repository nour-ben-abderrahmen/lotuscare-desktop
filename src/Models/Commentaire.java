/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;

/**
 *
 * @author Omar
 */
public class Commentaire {
     private int id;
   //  private int 
    private String contenu_comm;
    private Date date_com;
    private Publication publication_id ;

    public Commentaire(int id, String contenu_comm, Date date_com, Publication publication_id) {
        this.id = id;
        this.contenu_comm = contenu_comm;
        this.date_com = date_com;
        this.publication_id = publication_id;
    }

    public Commentaire(String contenu_comm, Date date_com, Publication publication_id) {
        this.contenu_comm = contenu_comm;
        this.date_com = date_com;
        this.publication_id = publication_id;
    }

    public Commentaire(String contenu_comm, Publication publication_id) {
        this.contenu_comm = contenu_comm;
        this.publication_id = publication_id;
    }

    public Commentaire(int id_R_modif, String text, String text0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu_comm() {
        return contenu_comm;
    }

    public void setContenu_comm(String contenu_comm) {
        this.contenu_comm = contenu_comm;
    }

    public Date getDate_com() {
        return date_com;
    }

    public void setDate_com(Date date_com) {
        this.date_com = date_com;
    }

    public Publication getPublication_id() {
        return publication_id;
    }

    public void setPublication_id(Publication publication_id) {
        this.publication_id = publication_id;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", contenu_comm=" + contenu_comm + ", date_com=" + date_com + ", publication_id=" + publication_id + '}';
    }
    
    
    }

   
    
    

