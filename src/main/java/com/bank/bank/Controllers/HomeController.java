package com.bank.bank.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "testing"; // Nome do template sem a extensão .html
    }
}
