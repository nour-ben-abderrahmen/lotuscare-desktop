package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Evenement {
    private int id;
    private Date date;
    private String lieu;
    private int nbr_participant;
    private String titre;
    private String description;
    private float total;
    private float prix;
    private String url_image;

    private List<Don> DonEvent;

    private String lat;
    private String lon;
    private String gouv ;



}
