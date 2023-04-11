package entities;

public class Produit {

    private String ref,description,image;
    private float prix;
    private int qte_stock,id;
    private Categorie  code_cat;

    public Produit() {
    }

    public Produit(String ref, String description, String image, float prix, int qte_stock) {
        this.ref = ref;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.qte_stock = qte_stock;
        //this.code_cat = code_cat;
    }


    public Produit( int id, String ref, String description, String image, float prix, int qte_stock,Categorie code_cat) {
        this.id = id;
        this.ref = ref;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.qte_stock = qte_stock;
        this.code_cat = code_cat;
    }

    @Override
    public String toString() {
        return "Produit{" +
                ", id=" + id +
                "ref='" + ref + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                ", qte_stock=" + qte_stock +

               // ", code_cat='" + code_cat + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getQte_stock() {
        return qte_stock;
    }

    public void setQte_stock(int qte_stock) {
        this.qte_stock = qte_stock;
    }


}
