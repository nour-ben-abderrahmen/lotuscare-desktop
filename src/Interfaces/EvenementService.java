package Interfaces;

import Models.Evenement;


import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface EvenementService {
    public void addEvent(Evenement event) throws SQLException;
    public void deleteEvent(int id) throws SQLException;
    public void updateEvent(int id, String titre, String lieu, int nbr_participant, Date date, String description,  Float prix, String url_image) throws SQLException;
public void updateEventTotal(int id) throws SQLException;
    public Evenement getEvent(int id) throws SQLException;
    public List<Evenement> getAllEvents() throws SQLException;
    public List<Evenement> getAllEventsDate() throws SQLException;


}
