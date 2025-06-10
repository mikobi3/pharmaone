package code.alba.pharmapro.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {

    @NotEmpty(message = "le nom est obligatoire")
    private String nomProd;

    @Size(min = 10,message = "la description ne devrait pas depasser 10")
    @Size(max = 400,message = "la description ne devrait pas depasser 400")
    private String  descriptionProd;

    private String categorieProd;

    @Min(0)
    private double prixProd;

    @Min(0)
    private double fauxPrixProd;

    private int quantiteenstockProd;

    private MultipartFile imageFileProd;

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDescriptionProd() {
        return descriptionProd;
    }

    public void setDescriptionProd(String descriptionProd) {
        this.descriptionProd = descriptionProd;
    }

    public String getCategorieProd() {
        return categorieProd;
    }

    public void setCategorieProd(String categorieProd) {
        this.categorieProd = categorieProd;
    }

    public double getPrixProd() {
        return prixProd;
    }

    public void setPrixProd(double prixProd) {
        this.prixProd = prixProd;
    }

    public double getFauxPrixProd() {
        return fauxPrixProd;
    }

    public void setFauxPrixProd(double fauxPrixProd) {
        this.fauxPrixProd = fauxPrixProd;
    }

    public int getQuantiteenstockProd() {
        return quantiteenstockProd;
    }

    public void setQuantiteenstockProd(int quantiteenstockProd) {
        this.quantiteenstockProd = quantiteenstockProd;
    }

    public MultipartFile getImageFileProd() {
        return imageFileProd;
    }

    public void setImageFileProd(MultipartFile imageFileProd) {
        this.imageFileProd = imageFileProd;
    }
}
