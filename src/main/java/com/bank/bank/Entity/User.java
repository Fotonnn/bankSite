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
import lombok.Setter;

@Table(name = "users")
@Entity(name = "User")
@Getter //getters automatico
@Setter
@NoArgsConstructor //construtor sem argumentos
@AllArgsConstructor //autoexplicativo
@EqualsAndHashCode(of = "user_id") //seguranca para ids
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que é um valor gerado automaticamente, tanto que nao precisa por ele no construtor
  private int user_id;

  private String username;
  private int userage;
  private double userbalance;
  private String userpassword;
  private String usercpf;
  private String useremail;

  public User(DadosCadastro dados) { //construtor pra cadastrar por meio da req post
    this.username = dados.username();
    this.userage = dados.userage();
    this.userbalance = dados.userbalance();
    this.userpassword = dados.userpassword();
    this.usercpf = dados.usercpf();
    this.useremail = dados.useremail();
  }

  public String getInfoAsString() { //feito com gpt, salva tempo
    return (
      "User ID: " +
      user_id +
      "\n" +
      "Username: " +
      username +
      "\n" +
      "User Age: " +
      userage +
      "\n" +
      "User Balance: " +
      userbalance +
      "\n" +
      "User Password: " +
      userpassword +
      "\n" +
      "User CPF: " +
      usercpf +
      "\n" +
      "User Email: " +
      useremail
    );
  }
}
