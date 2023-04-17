package Services;


import Interfaces.AssociationService;
import Models.Association;
import Models.Don;
import Models.Evenement;
import Tools.LotuscareConnexion;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AssociationServiceImp implements AssociationService {
    Connection connection;


    public AssociationServiceImp() {
      connection  = LotuscareConnexion.getInstance().getCnx();
    }
    EvenementServiceImp evenementServiceImp = new EvenementServiceImp();


    @Override
    public void addAssociation(Association association) throws SQLException {

        PreparedStatement ps =connection.prepareStatement(
                "insert into association(nom,description) values(?,?)");
        ps.setString(1,association.getNom());
        ps.setString(2,association.getDescription());
        ps.executeUpdate();
        ps.close();


    }

    @Override
    public void deleteAssociation(int id) throws SQLException{

            PreparedStatement ps =connection.prepareStatement(
                    "delete from association where id=?");
            ps.setInt(1,id);

            ps.executeUpdate();
            ps.close();
            System.out.println("Suppression d une association avec succés");


    }

    @Override
    public void updateAssociation(int id,String nom,String description) throws SQLException {

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE association SET nom=?,description=? where id = ?");
            ps.setString(1,nom);
            ps.setString(2,description);
            ps.setInt(3,id);
            ps.executeUpdate();
            ps.close();
            System.out.println("update d une association avec succés");


    }

    @Override
    public void pdfAssociation(Association association) throws IOException, SQLException {
        File outputFile = new File("C:/Users/info tech sud/Desktop/pdf/reçu "+association.getNom()+".pdf");
        // Create a new PDF document
        Document document = new Document(new PdfDocument(new PdfWriter(outputFile)));

        // Load a font for the document
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

        // Add details
        document.add(new Paragraph("Association Details")
                .setFont(font)
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .setWidth(UnitValue.createPercentValue(80))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);


        ObservableList<Don> list = FXCollections.observableArrayList(getListDons(association));

       float total = 0;

        for (Don don : list) {

            table.addCell(don.getEvenement().get(0).getTitre())
                    .setFont(font)
                    .setBold();

            table.addCell(String.valueOf(don.getSomme()))
                    .setFont(font);
            total+=don.getSomme();

        }


        table.addCell("Total")
                .setFont(font)
                .setBold();
        table.addCell(String.valueOf(total))
                .setFont(font);


        document.add(table);
        // Close pdf
        document.close();

        //Open pdf
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(outputFile);
        }
    }

    @Override
    public Association getAssociation(int id) throws SQLException {
        Association association = null;

            PreparedStatement ps = connection.prepareStatement(
                    "select * from association where id = ?");
            ps.setInt(1,id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                association = new Association();
                association.setId(rs.getInt("id"));
                association.setNom(rs.getString("nom"));
                association.setDescription(rs.getString("description"));



            }
            ps.close();



        return association;
    }

    @Override
    public List<Don> getListDons(Association association) throws SQLException {
        List<Don> dons = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select * from don where association_id =  ? ");
        ps.setInt(1,association.getId());

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Don don =new Don();
            don.setId(rs.getInt("id"));
            don.setSomme(rs.getFloat("Somme"));

            don.setAssociation(association);

            int event_id=rs.getInt("evenement_id");
            Evenement evenement=evenementServiceImp.getEvent(event_id);

            List<Evenement> list = new ArrayList<>();
            list.add(evenement);

            don.setEvenement(list);
            dons.add(don);
        }
        ps.close();



        return dons;
    }

    @Override
    public List<Association> getAllAssociations() throws SQLException {
        List<Association> associations = new ArrayList<>();

            PreparedStatement ps = connection.prepareStatement("select * from association");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Association association=new Association();
                association.setId(rs.getInt("id"));
                association.setNom(rs.getString("nom"));
                association.setDescription(rs.getString("description"));



                associations.add(association);
            }
            ps.close();



        return associations;
    }

    @Override
    public List<Association> getAssociationsParMC(String motcle) throws SQLException {
        System.out.println("***********");
        List<Association> associations=new ArrayList<>();
        PreparedStatement preparedStatement=connection.prepareStatement("SELECT * FROM association where nom like ?");
        preparedStatement.setString(1,"%"+motcle+"%");
        ResultSet resultSet= preparedStatement.executeQuery();
        while (resultSet.next()){
            Association association=new Association();
            association.setId(resultSet.getInt("id"));
            association.setNom(resultSet.getString("nom"));
            association.setDescription(resultSet.getString("description"));

            associations.add(association);
        }
        preparedStatement.close();
        return associations;
    }
}
