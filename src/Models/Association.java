package Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Association {
    public Association(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
    private int id;
private String  nom;
private String description;
private List<Don> dons;




}
