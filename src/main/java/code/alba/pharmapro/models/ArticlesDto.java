package code.alba.pharmapro.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ArticlesDto {

    @NotEmpty(message = "le titre est obligatoire")
    private String titre_art;

    @Size(min = 10,message = "le petit resumé de l'article ne doit pas etre moins de 10")
    @Size(max = 100,message = "le petit resumé ne doit pas depassé 100 mots")
    private String petit_resume_art;

    @Size(min = 10,message = "le contenu ne doit pas avoir moins de 10 mots")
    private String contenu_art;

    private String datecreation_art;

    private MultipartFile imageUrl_art;


    public String getTitre_art() {
        return titre_art;
    }

    public void setTitre_art(String titre_art) {
        this.titre_art = titre_art;
    }

    public String getPetit_resume_art() {
        return petit_resume_art;
    }

    public void setPetit_resume_art(String petit_resume_art) {
        this.petit_resume_art = petit_resume_art;
    }

    public String getContenu_art() {
        return contenu_art;
    }

    public void setContenu_art(String contenu_art) {
        this.contenu_art = contenu_art;
    }

    public String getDatecreation_art() {
        return datecreation_art;
    }

    public void setDatecreation_art(String datecreation_art) {
        this.datecreation_art = datecreation_art;
    }

    public MultipartFile getImageUrl_art() {
        return imageUrl_art;
    }

    public void setImageUrl_art(MultipartFile imageUrl_art) {
        this.imageUrl_art = imageUrl_art;
    }
}
