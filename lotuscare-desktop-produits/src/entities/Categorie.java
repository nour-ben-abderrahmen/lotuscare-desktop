package entities;

public class Categorie {

private int id;
private String code_cat, nom_cat;

    public Categorie() {
    }

    public Categorie(String code_cat, String nom_cat) {
        this.code_cat = code_cat;
        this.nom_cat = nom_cat;
    }

    public Categorie(int id, String code_cat, String nom_cat) {
        this.id = id;
        this.code_cat = code_cat;
        this.nom_cat = nom_cat;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", code_cat='" + code_cat + '\'' +
                ", nom_cat='" + nom_cat + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode_cat() {
        return code_cat;
    }

    public void setCode_cat(String code_cat) {
        this.code_cat = code_cat;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }
}
