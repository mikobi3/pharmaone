package code.alba.pharmapro.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {


    @GetMapping("/adminis")
    public String admin(){
        return "admin";
    }

    @GetMapping("/adminsup")
    public String adminsuperviseur(){
        return "adminSuperviseur";
    }
}
