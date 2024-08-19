package com.bank.bank.Controllers;

import com.bank.bank.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  UsuarioController user;

  @GetMapping("/login")
  public String login() {
    return "loginpage"; // Nome do template sem a extensão .html
  }

  @GetMapping("/cadastro")
  public String cadastro() {
    return "cadasterpage";
  }

  @GetMapping("home")
  public String homepage() {
    return "homepage";
  }

  @GetMapping("user")
  public String userpage(
    Model model,
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException, ClassNotFoundException, SQLException, ServletException {
    HttpSession userSession = request.getSession(false);
    User user = (User) userSession.getAttribute("user");
    model.addAttribute("balance", user.getUserbalance());
    model.addAttribute("age", user.getUserage());
    model.addAttribute("username", user.getUsername());
    model.addAttribute("id", user.getUser_id());
    model.addAttribute("cpf", user.getUsercpf());
    model.addAttribute("email", user.getUseremail());
    model.addAttribute("password", user.getUserpassword());
    model.addAttribute("transactions", user.getTransactions().size());
    return "userinfo";
  }
}
