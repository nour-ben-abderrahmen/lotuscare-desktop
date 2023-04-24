/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Back;

import Models.Publication;
import Services.ServicePublication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author Omar
 */
public class StatsController implements Initializable {

    @FXML
private PieChart pieChart;
          
    @FXML
  private BarChart<String, Number> barChart;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
       ServicePublication es= new ServicePublication();
       ObservableList<Publication> list = es.Stat();
         System.out.println("list ::: "+list);
       
       XYChart.Series<String, Number> series = new XYChart.Series<>();
series.setName("les 3 publications avec le plus de commentaires");

int count = 0;
for (Publication publication : list) {
    if (count >= 3) { // only show top 3 formations
        break;
    }
    String titre = publication.getCode_pub();
    int nombreParticipants = es.countParticipations(publication.getId()); // appel à la fonction countParticipations
    series.getData().add(new XYChart.Data<>(titre, nombreParticipants));
    count++;
}
barChart.getData().add(series);

      afficherStatistiques();
      
    }    

    private void afficherStatistiques() {
    }

  
   
    
}
