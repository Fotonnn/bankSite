package com.bank.bank.Entity;

import com.bank.bank.DadosUser.DadosCadastro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    private String username;
    private int userage;
    private double userbalance;
    private String userpassword;
    private String usercpf;
    private String useremail;


    public User(DadosCadastro dados) {
        this.username = dados.username();
        this.userage = dados.userage();
        this.userbalance = dados.userbalance();
        this.userpassword = dados.userpassword();
        this.usercpf = dados.usercpf();
        this.useremail = dados.useremail();
    }
}
