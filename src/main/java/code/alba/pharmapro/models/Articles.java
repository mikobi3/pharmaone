package code.alba.pharmapro.models;

import jakarta.persistence.*;


@Entity
@Table(name = "lesarticles")
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre_articles;

    @Column(columnDefinition = "TEXT")
    private String petite_description_articles;

    @Column(columnDefinition = "TEXT")
    private String contenu_articles;

    private String date_creation_articles;

    private String imageUrl_articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre_articles() {
        return titre_articles;
    }

    public void setTitre_articles(String titre_articles) {
        this.titre_articles = titre_articles;
    }

    public String getPetite_description_articles() {
        return petite_description_articles;
    }

    public void setPetite_description_articles(String petite_description_articles) {
        this.petite_description_articles = petite_description_articles;
    }

    public String getContenu_articles() {
        return contenu_articles;
    }

    public void setContenu_articles(String contenu_articles) {
        this.contenu_articles = contenu_articles;
    }

    public String getDate_creation_articles() {
        return date_creation_articles;
    }

    public void setDate_creation_articles(String date_creation_articles) {
        this.date_creation_articles = date_creation_articles;
    }

    public String getImageUrl_articles() {
        return imageUrl_articles;
    }

    public void setImageUrl_articles(String imageUrl_articles) {
        this.imageUrl_articles = imageUrl_articles;
    }
}
