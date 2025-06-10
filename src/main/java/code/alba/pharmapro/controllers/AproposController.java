package code.alba.pharmapro.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AproposController {

    @GetMapping("/aproposall")
    public String apropos(){
        return "apropos";
    }
}
