package code.alba.pharmapro.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcceuilController {


    @GetMapping({"","/"})
    public String acceuil(){
        return "index";
    }

    @GetMapping("/accueil")
    public String accueilaveclien(){
        return "index";
    }


    @GetMapping("/admin")
    public String adminPage(){
        return "adminSuperviseur";
    }

    @GetMapping("/superadmin")
    public String superAdminPage(){
        return "admin";
    }

    @GetMapping("/manager")
    public String managerPage(){
        return "adminManager";
    }

    @GetMapping("/login")
    public String login(){
        return "connexion";
    }

}
