package code.alba.pharmapro.models;


import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<PanierItem> items=new ArrayList<>();

    public List<PanierItem> getItems(){
        return items;
    }

    public void setItems(List<PanierItem> items){
        this.items=items;
    }

    public void addItem(PanierItem item){
        int index=items.indexOf(item);
        if (index>=0){
            //le produit est déjà dans le panier, augmenter la quantité
            items.get(index).setQuantity(items.get(index).getQuantity()+item.getQuantity());
        }else {
            //Ajouter un nouveau produit au panier
            items.add(item);
        }
    }

    public void removeItem(Integer productId){
       items.removeIf(item -> item.getProductId().equals(productId));
    }

    public void updateQuantity(Integer productId, int quantity){
        for (PanierItem item:items){
            if (item.getProductId().equals(productId)){
                item.setQuantity(quantity);
                break;
            }
        }
    }

    public double getTotal(List<Product> productsInCart){
        double total=0;
        for (PanierItem item:items){
            for (Product product:productsInCart){
                if (item.getProductId().equals(product.getId())){
                    total += item.getQuantity() * product.getPrixProduct();
                    break;
                }
            }
        }
        return total;
    }

    public int getItemCount(){
        return items.stream().mapToInt(PanierItem::getQuantity).sum();
    }

}
