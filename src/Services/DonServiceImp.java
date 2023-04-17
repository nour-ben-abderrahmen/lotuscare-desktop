package Services;

import Interfaces.DonService;
import Models.Association;
import Models.Don;
import Models.Evenement;
import Tools.LotuscareConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonServiceImp implements DonService {
    Connection connection;

    public DonServiceImp() {
        connection  = LotuscareConnexion.getInstance().getCnx();
    }

    AssociationServiceImp associationServiceImp = new AssociationServiceImp();
    EvenementServiceImp evenementServiceImp= new EvenementServiceImp();

    @Override
    public void addDon(Don don) throws SQLException {
        PreparedStatement ps =connection.prepareStatement(
                "insert into don(somme,evenement_id,association_id) values(?,?,?)");
       System.out.println(don.getEvenement().get(0).getId()+"      ");
        System.out.println(don.getAssociation().getId());
        ps.setFloat(1,don.getSomme());
        ps.setInt(2,don.getEvenement().get(0).getId());
        ps.setInt(3,don.getAssociation().getId());

        ps.executeUpdate();
        ps.close();
    }

    @Override
    public List<Don> getAllDons() throws SQLException {
        List<Don> dons = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select * from don");
        ResultSet rs = ps.executeQuery();

        while (rs.next()){

            Don don=new Don();
            don.setId(rs.getInt("id"));
            don.setSomme(rs.getFloat("somme"));

            int association_id = rs.getInt("association_id");
            Association association=associationServiceImp.getAssociation(association_id);
            don.setAssociation(association);

            int event_id=rs.getInt("evenement_id");
            Evenement evenement=evenementServiceImp.getEvent(event_id);
            List<Evenement> list = new ArrayList<>();
            list.add(evenement);

            don.setEvenement(list);
            dons.add(don);
        }
        ps.close();

        return dons;    }



}
