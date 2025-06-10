package code.alba.pharmapro.models;

public class PanierItem {

    private Integer productId;
    private int quantity;

    public PanierItem(){

    }

    public PanierItem(Integer productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if (o==null || getClass() != o.getClass()) return false;
        PanierItem panierItem=(PanierItem) o;
        return productId.equals(panierItem.productId);
    }

    @Override
    public int hashCode(){
        return productId.hashCode();
    }
}
