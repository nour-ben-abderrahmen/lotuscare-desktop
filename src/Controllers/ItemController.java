package Controllers;

import Interfaces.Mylistener;
import Models.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ItemController {
    @FXML
    private Label titreLabel;

    @FXML
    private Label lieuLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView img;
    @FXML
    private void click(MouseEvent mouseEvent){
        mylistener.onClickListener(event);
    }
    private Evenement event;
    private Mylistener mylistener;
    public void setData(Evenement event,Mylistener mylistener){
        this.event=event;
        this.mylistener=mylistener;
        titreLabel.setText(event.getTitre());
        lieuLabel.setText(event.getLieu());
        dateLabel.setText(String.valueOf(event.getDate()));
        Image image = new Image("file:"+event.getUrl_image());
        img.setImage(image);




    }
}
