package Interfaces;

import Models.Association;
import Models.Don;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface AssociationService  {
    public void addAssociation(Association association) throws SQLException;
    public void deleteAssociation(int id) throws SQLException;
    public void updateAssociation(int id,String nom,String description) throws SQLException;

    List<Don> getListDons(Association association) throws SQLException;

    public List<Association> getAllAssociations() throws SQLException;

    public void pdfAssociation(Association association) throws IOException, SQLException;
    public Association getAssociation(int id) throws SQLException;

    public List<Association> getAssociationsParMC(String motcle) throws SQLException;

}
