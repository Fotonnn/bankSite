package com.bank.bank.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/login")
  public String login() {
    return "loginpage"; // Nome do template sem a extensão .html
  }

  @GetMapping("/cadastro")
  public String cadastro() {
    return "cadasterpage";
  }

  @GetMapping("home")
  public String homepage(Model model) {
    return "homepage";
  }
}
