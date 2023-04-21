package Services;

import Interfaces.EvenementService;
import Models.Evenement;
import Tools.LotuscareConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementServiceImp implements EvenementService {
    Connection connection;

    public EvenementServiceImp() {
        connection  = LotuscareConnexion.getInstance().getCnx();
    }

    @Override
    public void addEvent(Evenement event) throws SQLException {
        PreparedStatement ps =connection.prepareStatement(
                "insert into evenement(titre,lieu,nbr_participant,date,description,total,prix,url_image,lat,lon) values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,event.getTitre());
        ps.setString(2,event.getLieu());
        ps.setInt(3,event.getNbr_participant());
        ps.setDate(4, (Date) event.getDate());
        ps.setString(5,event.getDescription());
        ps.setFloat(6,event.getTotal());
        ps.setFloat(7,event.getPrix());
        ps.setString(8,event.getUrl_image());
        ps.setString(9,event.getLat());
        ps.setString(10,event.getLon());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void deleteEvent(int id) throws SQLException {
        PreparedStatement ps =connection.prepareStatement(
                "delete from evenement where id=?");
        ps.setInt(1,id);

        ps.executeUpdate();
        ps.close();
        System.out.println("Suppression d un evenement avec succés");

    }

    @Override
    public void updateEvent(int id,String titre,String lieu, int nbr_participant,Date date,String description,Float prix,String url_image) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE evenement SET titre=?,lieu=?,nbr_participant=?,date=?,description=?,prix=?,url_image=? where id = ?");
        ps.setString(1,titre);
        ps.setString(2,lieu);
        ps.setInt(3,nbr_participant);
        ps.setDate(4,date);
        ps.setString(5,description);

        ps.setFloat(6,prix);
        ps.setString(7,url_image);
        ps.setInt(8,id);
        ps.executeUpdate();
        ps.close();
        System.out.println("update d un evenement avec succés");

    }



    @Override
    public void participerEvent(int id,int nbr_participant,float total) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE evenement SET nbr_participant=?,total=? where id = ?");
        ps.setInt(1,nbr_participant);
        ps.setFloat(2,total);

        ps.setInt(3,id);
        ps.executeUpdate();
        ps.close();


    }

    @Override
    public void updateEventTotal(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "UPDATE evenement SET total=0 where id = ?");

        ps.setInt(1,id);
        ps.executeUpdate();
        ps.close();


    }

    @Override
    public Evenement getEvent(int id) throws SQLException {
        Evenement evenement = null;

        PreparedStatement ps = connection.prepareStatement(
                "select * from evenement where id = ?");
        ps.setInt(1,id);
        ResultSet rs=ps.executeQuery();
        if (rs.next()){
            evenement = new Evenement();
            evenement.setId(rs.getInt("id"));
            evenement.setTitre(rs.getString("titre"));
            evenement.setDescription(rs.getString("description"));
            evenement.setLieu(rs.getString("lieu"));
            evenement.setLat(rs.getString("lat"));
            evenement.setLon(rs.getString("lon"));
            evenement.setGouv(rs.getString("gouv"));
            evenement.setUrl_image(rs.getString("url_image"));
            evenement.setDate(rs.getDate("date"));

            evenement.setTotal(rs.getFloat("total"));
            evenement.setPrix(rs.getFloat("prix"));
            evenement.setNbr_participant(rs.getInt("nbr_participant"));


        }
        ps.close();



        return evenement;
    }

    @Override
    public List<Evenement> getAllEvents() throws SQLException {
        List<Evenement> events = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select * from evenement ");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Evenement event=new Evenement();
            event.setId(rs.getInt("id"));
            event.setTitre(rs.getString("titre"));
            event.setDescription(rs.getString("description"));
            event.setDate(rs.getDate("date"));
            event.setLieu(rs.getString("lieu"));
            event.setNbr_participant(rs.getInt("nbr_participant"));
            event.setTotal(rs.getFloat("total"));
            event.setPrix(rs.getFloat("prix"));
            event.setUrl_image(rs.getString("url_image"));
            event.setLat(rs.getString("lat"));
            event.setLon(rs.getString("lon"));
            event.setGouv(rs.getString("gouv"));


            events.add(event);
        }
        ps.close();
        return events;
    }

    @Override
    public List<Evenement> getAllEventsDate() throws SQLException {
        List<Evenement> events = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select * from evenement where evenement.date < CURDATE() and evenement.total > 0");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Evenement event=new Evenement();
            event.setId(rs.getInt("id"));
            event.setTitre(rs.getString("titre"));
            event.setDescription(rs.getString("description"));
            event.setDate(rs.getDate("date"));
            event.setLieu(rs.getString("lieu"));
            event.setNbr_participant(rs.getInt("nbr_participant"));
            event.setTotal(rs.getFloat("total"));
            event.setPrix(rs.getFloat("prix"));
            event.setUrl_image(rs.getString("url_image"));
            event.setLat(rs.getString("lat"));
            event.setLon(rs.getString("lon"));
            event.setGouv(rs.getString("gouv"));


            events.add(event);
        }
        ps.close();
        return events;
    }
}
