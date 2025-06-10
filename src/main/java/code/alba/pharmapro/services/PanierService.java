package code.alba.pharmapro.services;

import code.alba.pharmapro.models.PanierItem;
import code.alba.pharmapro.models.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class PanierService {

    private List<PanierItem> items=new ArrayList<>();

    //ajouter le produit du panier


    
}
