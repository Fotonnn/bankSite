package com.bank.bank.Controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank.DadosUser.DadosCadastro;
import com.bank.bank.DataBase.UserData;
import com.bank.bank.Entity.User;
import com.bank.bank.Requests.LoginRequest;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@RestController //indica que é um controller
@RequestMapping("usuarios") //mapear o request exemplo site.com/cadastro
public class UsuarioController {

    @Autowired
    private UserData userData;

    Logger log = Logger.getLogger("Controller de Usuario");
    
    @PostMapping //indica que é um metodo post
    @Transactional //é ativo, algo assim
    public void cadastro(@RequestBody @Valid DadosCadastro dados) { //tem que avisar pro spring que recebe como rbody
        userData.save(new User(dados));
    }

    @GetMapping("balance")
    public double getBalance(@RequestParam int user_id) {
        User user = userData.getReferenceById(user_id);
        double balance = user.getUserbalance();
        log.info("balance retornado.");
        return balance;
    }

    @PostMapping("login")
    public boolean getMethodName(@RequestBody LoginRequest loginReq) {
        User user = userData.getReferenceById(loginReq.getUser_id());
        if (!loginReq.getUserpassword().equals(user.getUserpassword())) {
            log.info("senha errada");
            return false;
        }
        return true;
    }
    
}
