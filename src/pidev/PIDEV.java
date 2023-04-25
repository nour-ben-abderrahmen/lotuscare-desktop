/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import DB.DBConnection;


public class PIDEV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBConnection connexion = DBConnection.getInstance();
        /**
        Categorie c = new Categorie(2,"Cat1");
        User u =new User(1,"sousse");
        User i =new User(2,"ala");
        Produit p =new Produit("pc","lenevo",600,c,u);
        Produit p1 =new Produit(33,"ala","bardo",400,c,u);
        CRUDCategorie crud = new CRUDCategorie();
        CRUDProduit crudp=new CRUDProduit() ;
        CRUDImage crudi=new CRUDImage();
        //crud.ajoutercategorie(p);
        crud.afficherCategories();
        crudp.afficherProduit();
        //crudp.ajouterProduit(p);
        //crudp.getProduitByTitre("hamza");
        //crudp.validateProduit(70);
        //crudp.modifierProduit(p1);
        //crudp.AfficherProduitCroissant();
        //Image m=new Image("ggg");
        //crudi.ajouterImage(m,p);
        //System.out.println(crudp.validateProduit(p1));
        //crudp.supprimerProduit(33,u);
        // crudp.modifierProduit(p);
     
    String to = "mohamedalimoula@gmail.com";
    String from = "moula.mohamedali@esprit.tn";
    String password = "223JMT2830";

    String host = "smtp.gmail.com";
    String port = "587";

    // Set up properties for the SMTP connection
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
    props.put("mail.smtp.starttls.enable", "true");

    // Create a new Session object
    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, password);
          }
        });

    try {
      // Create a new message
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(to));
      message.setSubject("Produit Valider");
      message.setText("Votre Produit est validé");

      // Send the message
      Transport.send(message);

      System.out.println("Message sent successfully!");

    } catch (MessagingException e) {
      System.out.println("Failed to send message: " + e.getMessage());
    }
  
    }

         */
    }
}