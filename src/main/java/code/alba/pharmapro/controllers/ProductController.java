package code.alba.pharmapro.controllers;

import code.alba.pharmapro.models.Product;
import code.alba.pharmapro.models.ProductDto;
import code.alba.pharmapro.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/allproducts")
    public String afficherlesproduits(Model model){
        List<Product> nosproduits=productRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("nosproduits",nosproduits);
        return "produits";
    }

    @GetMapping("/allprodadmin")
    public String montreproduitsadmin(Model model){
        List<Product> productisadmin=productRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("productisadmin",productisadmin);
        return "updateproduit";
    }

    @GetMapping("/products/{id}")
    public String ShowProduct(@PathVariable Integer id,
                              Model model){
        Product product=productRepository.findById(id).get();
        model.addAttribute("product",product);
        return "lepanier";
    }

    @GetMapping("/creationprod")
    public String montrepagecreationproduit(Model model){
        ProductDto productDto=new ProductDto();
        model.addAttribute("productDto",productDto);
        return "creationproduit";
    }

    @PostMapping("/creationprod")
    public String ajouterunproduit(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    ){
        if (productDto.getImageFileProd().isEmpty()){
            result.addError(new FieldError("productDto","imageFile_prod","cette image n'existe pas"));
        }
        if (result.hasErrors()){
            return "creationproduit";
        }

        MultipartFile image=productDto.getImageFileProd();
        Date createdAt=new Date();
        String storageFileName=createdAt.getTime()+'_'+image.getOriginalFilename();

        try {
            String uploadDir="public/images/";
            Path uploadPath= Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream=image.getInputStream()) {
                Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }

        Product product=new Product();
        product.setNomProduct(productDto.getNomProd());
        product.setCategorieProduct(productDto.getCategorieProd());
        product.setDescriptionProduct(productDto.getDescriptionProd());
        product.setPrixProduct(productDto.getPrixProd());
        product.setFauxPrixProduct(productDto.getFauxPrixProd());
        product.setQuantiteEnStockProduct(productDto.getQuantiteenstockProd());
        product.setImageUrlProduct(storageFileName);

        productRepository.save(product);

        return "redirect:/allprodadmin";
    }

    @GetMapping("/editprod")
    public String montrelapagedemodification(
            Model model,
            @RequestParam int id
    ){
        try {
            Product product= productRepository.findById(id).get();
            model.addAttribute("product",product);

            ProductDto productDto=new ProductDto();
            productDto.setNomProd(product.getNomProduct());
            productDto.setCategorieProd(product.getCategorieProduct());
            productDto.setDescriptionProd(product.getDescriptionProduct());
            productDto.setPrixProd(product.getPrixProduct());
            productDto.setFauxPrixProd(product.getFauxPrixProduct());

            model.addAttribute("productDto",productDto);

        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }

        return "modifierproduit";
    }

    @PostMapping("/editprod")
    public String updateprod(
        Model model,
        @RequestParam int id,
        @Valid @ModelAttribute ProductDto productDto,
        BindingResult result
    ){
        try {
            Product product=productRepository.findById(id).get();
            model.addAttribute("product",product);

            if (result.hasErrors()){
                return "modifierproduit";
            }
            if (!productDto.getImageFileProd().isEmpty()){
                //delete image
                String uploadDir="public/images/";
                Path oldimagePath=Paths.get(uploadDir+product.getImageUrlProduct());

                try {
                    Files.delete(oldimagePath);
                }catch (Exception ex){
                    System.out.println("Exception:"+ex.getMessage());
                }

                //save image
                MultipartFile image=productDto.getImageFileProd();
                Date createdAt=new Date();
                String storageFileName=createdAt.getTime() +'_'+ image.getOriginalFilename();

                try (InputStream inputStream=image.getInputStream()){
                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageUrlProduct(storageFileName);
            }
            product.setNomProduct(productDto.getNomProd());
            product.setCategorieProduct(productDto.getCategorieProd());
            product.setDescriptionProduct(productDto.getDescriptionProd());
            product.setPrixProduct(productDto.getPrixProd());
            product.setFauxPrixProduct(productDto.getFauxPrixProd());
            product.setQuantiteEnStockProduct(productDto.getQuantiteenstockProd());

            productRepository.save(product);
        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return "redirect:/allprodadmin";
    }

    @GetMapping("/deleteprod")
    public String deleteproduit(
            @RequestParam int id
    ){
        try {
            Product product=productRepository.findById(id).get();

            //delete image produit
            Path imagePath=Paths.get("public/images/"+product.getImageUrlProduct());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception:"+ex.getMessage());
            }

            //delete produit
            productRepository.delete(product);
        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return "redirect:/allprodadmin";
    }

    @GetMapping("/commanderSurWhatsapp/{id}")
    public RedirectView commanderViaWhatsapp(@PathVariable Integer id){
        Product product =productRepository.findById(id).orElseThrow(()->new
                ResponseStatusException(HttpStatus.NOT_FOUND));

        String productName=product.getNomProduct();
        Double productPrice=product.getPrixProduct();

        String message= URLEncoder.encode(
                "Bonjour LabPro Pharma, je souhaite commander le produit: "+ productName +
                        " au prix de "+ productPrice +"$" , StandardCharsets.UTF_8);
        String whatsappUrl="https://wa.me/243857005409?text="+message;
        return new RedirectView(whatsappUrl);
    }

}
