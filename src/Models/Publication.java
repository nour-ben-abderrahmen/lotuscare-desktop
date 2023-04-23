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
public class Publication {
     private int id;
    private String code_pub;
    private String contenu_pub;
    private String url_image_pub;

    public Publication() {
    }
    

    public Publication(String code_pub, String contenu_pub, String url_image_pub) {
        this.code_pub = code_pub;
        this.contenu_pub = contenu_pub;
        this.url_image_pub = url_image_pub;
    }

    
    
    public Publication(int id, String code_pub, String contenu_pub, String url_image_pub) {
        this.id = id;
        this.code_pub = code_pub;
        this.contenu_pub = contenu_pub;
        this.url_image_pub = url_image_pub;
    }

    

    
    public Publication(String code_pub, String contenu_pub, Date date_pub) {
        this.date_pub = date_pub;
        this.code_pub = code_pub;
        this.contenu_pub = contenu_pub;
        
    }

    
    
    public Publication(String code_pub, String contenu_pub) {
        this.code_pub = code_pub;
        this.contenu_pub = contenu_pub;
    }

    
    
    public Publication(int id, String code_pub, String contenu_pub) {
        this.id = id;
        this.code_pub = code_pub;
        this.contenu_pub = contenu_pub;
    }

    

    
    
    
    
 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_image_pub() {
        return url_image_pub;
    }

    public void setUrl_image_pub(String url_image_pub) {
        this.url_image_pub = url_image_pub;
    }

    public Date getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(Date date_pub) {
        this.date_pub = date_pub;
    }
    private Date date_pub;
    
    public String getCode_pub() {
        return code_pub;
    }

    public void setCode_pub(String code_pub) {
        this.code_pub = code_pub;
    }

    public String getContenu_pub() {
        return contenu_pub;
    }

    public void setContenu_pub(String contenu_pub) {
        this.contenu_pub = contenu_pub;
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", code_pub=" + code_pub + ", contenu_pub=" + contenu_pub + ", url_image_pub=" + url_image_pub + ", date_pub=" + date_pub + '}';
    }


   
    
    
}
