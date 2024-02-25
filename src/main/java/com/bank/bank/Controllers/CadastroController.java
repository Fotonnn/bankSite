package com.bank.bank.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank.DadosUser.DadosCadastro;
import com.bank.bank.DataBase.UserData;
import com.bank.bank.Entity.User;

@RestController
@RequestMapping("cadastro")
public class CadastroController {

    @Autowired
    private UserData userData;
    
    @PostMapping
    @Transactional
    public void cadastro(@RequestBody DadosCadastro dados) {
        userData.save(new User(dados));
    }
}
