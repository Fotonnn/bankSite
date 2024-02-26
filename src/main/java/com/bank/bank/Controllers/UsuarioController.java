package com.bank.bank.Controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bank.bank.DadosUser.DadosCadastro;
import com.bank.bank.DadosUser.DadosLogin;
import com.bank.bank.DadosUser.DadosTransferencia;
import com.bank.bank.DataBase.TransactionsData;
import com.bank.bank.DataBase.UserData;
import com.bank.bank.Entity.Transactions;
import com.bank.bank.Entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController //indica que é um controller
@RequestMapping("usuarios") //mapear o request exemplo site.com/cadastro
public class UsuarioController {

    @Autowired
    private UserData userData;

    @Autowired
    private TransactionsData transactionsData;

    Logger log = Logger.getLogger("Controller de Usuario");

    private boolean isLogged;
    private User user;

    
    @PostMapping("cadastro") //indica que é um metodo post
    @Transactional //é ativo, algo assim
    public void cadastro(@RequestBody @Valid DadosCadastro dados) { //tem que avisar pro spring que recebe como rbody
        log.info("Cadastro feito");
        userData.save(new User(dados));
    }

    @PostMapping("login")
    @Transactional
    public ResponseEntity<String> getMethodName(@RequestBody DadosLogin loginReq) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        log.info("ip login: " + ip);
        user = userData.getReferenceById(loginReq.user_id());
        if (!loginReq.userpassword().equals(user.getUserpassword())) {
            log.info("senha errada");
            isLogged = false;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Senha errada");
        }
        isLogged = true;
        return ResponseEntity.ok("Login bem sucedido");
    }

    @GetMapping("balance")
    public double getBalance() throws Exception{
        if (!isLogged) {
            log.info("Usuario tentando acessar sem login");
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Sem login.");
            throw new Exception("Sem login");
        }
        double balance = user.getUserbalance();
        log.info("balance retornado.");
        return balance;
    }

    @PostMapping("payment")
    @Transactional
    public ResponseEntity<String> payment(@RequestBody @Valid DadosTransferencia dados) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Pagador nao logado");
        }
        try {
        User payer = userData.getReferenceById(dados.payer_id());
        User receiver = userData.getReferenceById(dados.receiver_id());
        Transactions transactions = new Transactions(dados);
        transactions.makeTransfer(dados.amount(), payer, receiver);
        transactionsData.save(transactions);
        userData.save(payer);
        userData.save(receiver);
        return ResponseEntity.ok("Pagamento bem sucedido.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e.getMessage());
        }
    }
    
}
