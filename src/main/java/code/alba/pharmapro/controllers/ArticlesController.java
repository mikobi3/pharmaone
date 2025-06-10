package code.alba.pharmapro.controllers;

import code.alba.pharmapro.models.Articles;
import code.alba.pharmapro.models.ArticlesDto;
import code.alba.pharmapro.repository.ArticlesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
public class ArticlesController {

    @Autowired
    private ArticlesRepository articlesRepository;

    @GetMapping("/artiblog")
    public String affichelesarticles(Model model){
        List<Articles> articlesdublog=articlesRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("articlesdublog",articlesdublog);
        return "blog";
    }

    @GetMapping("/articlesall")
    public String montrelesarticlesadmin(Model model){
      List<Articles> adminarticles=articlesRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
      model.addAttribute("adminarticles",adminarticles);
      return "updatearticle";
    }

    @GetMapping("/creearticle")
    public String montrepagecreationart(Model model){
        ArticlesDto articlesDto=new ArticlesDto();
        model.addAttribute("articlesDto",articlesDto);
        return "creerarticle";
    }

    @PostMapping("/creearticle")
    public String montrecreationart(
            @Valid @ModelAttribute ArticlesDto articlesDto,
            BindingResult result
    ){
        if (articlesDto.getImageUrl_art().isEmpty()){
            result.addError(new FieldError("articlesDto","imageUrl_art","cette image n'existe pas"));
        }
        if (result.hasErrors()){
            return "creerarticle";
        }

        //enregistrer l'image
        MultipartFile image=articlesDto.getImageUrl_art();
        Date createdAt=new Date();
        String storageFileName=createdAt.getTime()+ '_' +image.getOriginalFilename();

        try {
            String uploadDir="public/images/";
            Path uploadPath= Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream =image.getInputStream()) {
                Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }

        Articles articles=new Articles();
        articles.setTitre_articles(articlesDto.getTitre_art());
        articles.setPetite_description_articles(articlesDto.getPetit_resume_art());
        articles.setContenu_articles(articlesDto.getContenu_art());
        articles.setDate_creation_articles(articlesDto.getDatecreation_art());
        articles.setImageUrl_articles(storageFileName);

        articlesRepository.save(articles);

        return "redirect:/articleall";
    }

    @GetMapping("/editarticle")
    public String montrelapageedition(
            Model model,
            @RequestParam int id
    ){
        try {
            Articles articles=articlesRepository.findById(id).get();
            model.addAttribute("articles",articles);

            ArticlesDto articlesDto=new ArticlesDto();
            articlesDto.setTitre_art(articles.getTitre_articles());
            articlesDto.setPetit_resume_art(articles.getPetite_description_articles());
            articlesDto.setContenu_art(articles.getContenu_articles());
            articlesDto.setDatecreation_art(articles.getDate_creation_articles());

            model.addAttribute("articlesDto",articlesDto);

        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return "modifierarticle";

    }

    @PostMapping("/editarticle")
    public String editarticles(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ArticlesDto articlesDto,
            BindingResult result
    ){
        try {
            Articles articles=articlesRepository.findById(id).get();
            model.addAttribute("articles",articles);

            if (result.hasErrors()){
                return "modifierarticle";
            }
            if (!articlesDto.getImageUrl_art().isEmpty()){
                //delete old image
                String uploaDir="public/images/";
                Path oldimagePath=Paths.get(uploaDir+articles.getImageUrl_articles());

                try {
                    Files.delete(oldimagePath);
                }catch (Exception ex){
                    System.out.println("Exception:"+ex.getMessage());
                }

                //enregistrer la nouvelle image

                MultipartFile image=articlesDto.getImageUrl_art();
                Date createdAt=new Date();
                String storageFileName=createdAt.getTime() +'_'+ image.getOriginalFilename();

                try (InputStream inputStream=image.getInputStream()){
                    Files.copy(inputStream,Paths.get(uploaDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                articles.setImageUrl_articles(storageFileName);
            }
            articles.setTitre_articles(articlesDto.getTitre_art());
            articles.setPetite_description_articles(articlesDto.getPetit_resume_art());
            articles.setContenu_articles(articlesDto.getContenu_art());
            articles.setDate_creation_articles(articlesDto.getDatecreation_art());

            articlesRepository.save(articles);

        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return "redirect:/articleall";
    }

    @GetMapping("/deletearti")
    public String supprimerarti(
            @RequestParam int id
    ){
        try {
            Articles articles=articlesRepository.findById(id).get();

            //supprimer l'image de l'article
            Path imagePath=Paths.get("public/images/"+articles.getImageUrl_articles());
            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception:"+ex.getMessage());
            }
            //supprimer articles
            articlesRepository.delete(articles);

        }catch (Exception ex){
            System.out.println("Exception:"+ex.getMessage());
        }
        return "redirect:/articleall";

    }
}
