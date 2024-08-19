package com.bank.bank.Controllers;

import com.bank.bank.DadosUser.DadosCadastro;
import com.bank.bank.DadosUser.DadosTransferencia;
import com.bank.bank.DataBase.TransactionsData;
import com.bank.bank.DataBase.UserData;
import com.bank.bank.Entity.Transactions;
import com.bank.bank.Entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //indica que é um controller
@RequestMapping("api") //mapear o request exemplo site.com/cadastro
public class UsuarioController {

  @Autowired
  private UserData userData;

  @Autowired
  private TransactionsData transactionsData;

  Logger log = Logger.getLogger("Controller de Usuario");

  User user;

  @PostMapping("cadastro") //indica que é um metodo post
  @Transactional //é ativo, algo assim
  public ResponseEntity<Object> cadastro(
    @RequestBody @Valid DadosCadastro dados
  ) { //tem que avisar pro spring que recebe como rbody
    log.info("Cadastro feito");
    user = new User(dados);
    userData.save(user);
    log.info("Usuario logado.");
    return ResponseEntity.ok().build();
  }

  @PostMapping("login")
  @Transactional
  public void login(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ClassNotFoundException, SQLException, ServletException {
    if (user != null) {
      response.sendRedirect("/user");
      return;
    }
    String id = request.getParameter("id");
    user = userData.getReferenceById(Integer.valueOf(id));
    if (!request.getParameter("password").equals(user.getUserpassword())) {
      log.info("senha errada");
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong password.");
      return;
    } else {
      List<Transactions> transactions = findTransactionsByPayerId(
        user.getUser_id()
      );
      //for (Transactions t : transactions) {
      //  System.out.println(t.getAmount());
      //}
      user.setTransactions(transactions);
      HttpSession userSession = request.getSession();
      userSession.setAttribute("user", user);
      try {
        response.sendRedirect("/user");
      } catch (Exception e) {
        log.info("Erro ao redirecionar");
      }
      return;
    }
  }

  @PostMapping("logout")
  public ResponseEntity<Object> getOutOfAccount() {
    if (user == null) {
      return ResponseEntity.badRequest().build();
    }
    user = null;
    return ResponseEntity.ok().build();
  }

  @GetMapping("balance")
  public ResponseEntity<Object> getBalance() throws Exception {
    if (user == null) {
      log.info("Usuario tentando acessar sem login");
      ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Sem login.");
      throw new Exception("Sem login");
    }
    double balance = user.getUserbalance();
    log.info("balance retornado.");
    return ResponseEntity.ok(balance);
  }

  @PostMapping("payment")
  public ResponseEntity<Object> payment(
    @RequestBody @Valid DadosTransferencia dados
  ) {
    try {
      if (user != null && user.getUser_id() != dados.receiver_id()) {
        double result = user.getUserbalance() - dados.amount();
        if (result < 0) {
          throw new Exception("No suficient money");
        }
        User receiver = userData.getReferenceById(dados.receiver_id());
        user.setUserbalance(result);
        receiver.setUserbalance(receiver.getUserbalance() + dados.amount());
        userData.save(user);
        userData.save(receiver);
        transactionsData.save(new Transactions(dados, user.getUser_id()));
        return ResponseEntity.ok().build();
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.badRequest().build();
  }

  @GetMapping("userinfo")
  public String getUserInfo() throws Exception {
    if (user == null) {
      throw new Exception();
    }
    return user.getInfoAsString();
  }

  @DeleteMapping("/delete/{id}")
  @Transactional //diz que é um parametro do path
  public ResponseEntity<Object> deleteUser(@PathVariable int id) {
    userData.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  public List<Transactions> findTransactionsByPayerId(Integer payer_id) {
    return transactionsData.findTransactionsByPayerId(payer_id);
  }
}
