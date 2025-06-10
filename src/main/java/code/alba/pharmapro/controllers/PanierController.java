package code.alba.pharmapro.controllers;


import code.alba.pharmapro.models.Cart;
import code.alba.pharmapro.models.PanierItem;
import code.alba.pharmapro.models.Product;
import code.alba.pharmapro.repository.ProductRepository;
import code.alba.pharmapro.services.PanierService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PanierController {


    @Autowired
    private  ProductRepository productRepository;

    private static final String CART_SESSION_KEY="cart";

    private Cart getOrCreateCart(HttpSession session){
        return (Cart) session.getAttribute(CART_SESSION_KEY);
    }

    private void updateCartInSession(HttpSession session, Cart cart){
        session.setAttribute(CART_SESSION_KEY,cart);
    }

    @PostMapping("/ajouterAuPanier")
    public String ajouterAuPanier(@RequestParam("productId") Integer productId,
                                  @RequestParam(value = "quantity",defaultValue = "1") int quantity,HttpSession session){
        Optional<Product> productOptional=productRepository.findById(productId);
        if (productOptional.isPresent()){
            Cart cart=(Cart) session.getAttribute(CART_SESSION_KEY);
            if (cart==null){
                cart=new Cart();
            }
            cart.addItem(new PanierItem(productId,quantity));
            updateCartInSession(session,cart);
        }
        return "redirect:/lepanier";
    }

    @GetMapping("/showcart")
    public String afficherPanier(HttpSession session,Model model){
        Cart cart=(Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart != null && !cart.getItems().isEmpty()){
            List<Integer> productIds=cart.getItems().stream()
                    .map(PanierItem::getProductId)
                    .collect(Collectors.toList());
            List<Product> productsInCart=productRepository.findAllById(productIds);
            model.addAttribute("cartItems",cart.getItems());
            model.addAttribute("products", productsInCart);
            model.addAttribute("total",cart.getTotal(productsInCart));
        }else {
            model.addAttribute("cartItems",new ArrayList<>());
            model.addAttribute("products",new ArrayList<>());
            model.addAttribute("total",0.0);
        }
        return "lepanier";
    }

    @PostMapping("/supprimerDuPanier")
    public String supprimerDuPanier(@RequestParam("productId") Integer productId, HttpSession session){
        Cart cart=(Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart != null){
            cart.removeItem(productId);
            updateCartInSession(session,cart);
        }
        return "redirect:/lepanier";
    }

    @PostMapping("/mettreAJourPanier")
    public String mettreAJourPanier(@RequestParam("productId") Integer productId,
                                    @RequestParam("quantity") int quantity,
                                    HttpSession session){
        Cart cart=(Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart != null){
            cart.updateQuantity(productId,quantity);
            updateCartInSession(session,cart);
        }
        return "redirect:/lepanier";
    }

}
