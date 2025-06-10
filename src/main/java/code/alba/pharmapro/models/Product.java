package code.alba.pharmapro.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomProduct;

    @Column(columnDefinition = "TEXT")
    private String descriptionProduct;

    private String categorieProduct;

    private double prixProduct;

    private double fauxPrixProduct;

    private int quantiteEnStockProduct;

    private String imageUrlProduct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomProduct() {
        return nomProduct;
    }

    public void setNomProduct(String nomProduct) {
        this.nomProduct = nomProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public String getCategorieProduct() {
        return categorieProduct;
    }

    public void setCategorieProduct(String categorieProduct) {
        this.categorieProduct = categorieProduct;
    }

    public double getPrixProduct() {
        return prixProduct;
    }

    public void setPrixProduct(double prixProduct) {
        this.prixProduct = prixProduct;
    }

    public double getFauxPrixProduct() {
        return fauxPrixProduct;
    }

    public void setFauxPrixProduct(double fauxPrixProduct) {
        this.fauxPrixProduct = fauxPrixProduct;
    }

    public int getQuantiteEnStockProduct() {
        return quantiteEnStockProduct;
    }

    public void setQuantiteEnStockProduct(int quantiteEnStockProduct) {
        this.quantiteEnStockProduct = quantiteEnStockProduct;
    }

    public String getImageUrlProduct() {
        return imageUrlProduct;
    }

    public void setImageUrlProduct(String imageUrlProduct) {
        this.imageUrlProduct = imageUrlProduct;
    }

    @Override
    public boolean equals(Object o){
        if (this==o)
            return true;
        if (o==null || getClass() != o.getClass())
            return false;

        Product product=(Product) o;
        return Objects.equals(id,product.id);
    }

    @Override
    public int hashCode(){

        return Objects.hash(id);
    }
}
