package code.alba.pharmapro.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    @GetMapping("/contactall")
    public String contactenous(){
        return "contact";
    }
}
