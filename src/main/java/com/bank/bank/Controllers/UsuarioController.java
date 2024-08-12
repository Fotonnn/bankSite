package com.bank.bank.Controllers;

import com.bank.bank.DadosUser.DadosCadastro;
import com.bank.bank.DadosUser.DadosLogin;
import com.bank.bank.DadosUser.DadosTransferencia;
import com.bank.bank.DataBase.TransactionsData;
import com.bank.bank.DataBase.UserData;
import com.bank.bank.Entity.Transactions;
import com.bank.bank.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController //indica que é um controller
@RequestMapping("api") //mapear o request exemplo site.com/cadastro
public class UsuarioController {

  @Autowired
  private UserData userData;

  @Autowired
  private TransactionsData transactionsData;

  Logger log = Logger.getLogger("Controller de Usuario");

  private User user;

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
  public ResponseEntity<Object> login(@RequestBody DadosLogin loginReq) {
    HttpServletRequest request =
      ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String ip = request.getRemoteAddr();
    if (user != null) {
      return ResponseEntity.badRequest()
        .body("Please get out of the account to login in another account.");
    }
    log.info("ip login: " + ip);
    user = userData.getReferenceById(loginReq.user_id());
    if (!loginReq.userpassword().equals(user.getUserpassword())) {
      log.info("senha errada");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha errada");
    }
    return ResponseEntity.ok().build();
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
  // @PostMapping
  //public ResponseEntity<Object> updateUser() {

  //}
}
