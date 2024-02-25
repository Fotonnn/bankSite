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

import jakarta.validation.Valid;

@RestController //indica que é um controller
@RequestMapping("cadastro") //mapear o request exemplo site.com/cadastro
public class CadastroController {

    @Autowired
    private UserData userData;
    
    @PostMapping //indica que é um metodo post
    @Transactional //é ativo, algo assim
    public void cadastro(@RequestBody @Valid DadosCadastro dados) { //tem que avisar pro spring que recebe como rbody
        userData.save(new User(dados));
    }
}
