/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.*;

import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Models.Evenement;
import Services.EvenementServiceImp;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


import javax.swing.*;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class DashboardController implements Initializable {

    @FXML
    private StackPane dashPane;

    /**
     * Initializes the controller class.
     */
    EvenementServiceImp evenementServiceImp= new EvenementServiceImp();
    public DefaultPieDataset createDataset(List<Evenement> events) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> governmentCounts = new HashMap<>();

        for (Evenement event : events) {
            String government = event.getGouv();
            int count = governmentCounts.getOrDefault(government, 0) + 1;
            governmentCounts.put(government, count);
        }

        for (Map.Entry<String, Integer> entry : governmentCounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        return dataset;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SwingUtilities.invokeLater(() -> {
            Platform.runLater(() -> {
                DefaultPieDataset dataset = null;
                try {
                    dataset = createDataset(evenementServiceImp.getAllEvents());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                JFreeChart chart = ChartFactory.createPieChart(
                        "Events by Gouvernement",
                        dataset,
                        true,
                        true,
                        false
                );
                ChartViewer chartViewer = new ChartViewer(chart);

                dashPane.getChildren().add(chartViewer);

            });
        });



    }
    
}
