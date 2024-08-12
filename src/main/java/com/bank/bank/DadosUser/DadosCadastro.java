package com.bank.bank.DadosUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

//nao nulo nem vazio (apenas para strings)                                                                    //11 digitos
public record DadosCadastro(
  @NotBlank String username,
  @NotNull int userage,
  @NotNull double userbalance,
  @NotBlank String userpassword,
  @NotBlank @Pattern(regexp = "\\d{11}") String usercpf,
  @NotBlank @Email String useremail
) {
  //o record é util para nao precisar por varios getters e setters, construtores, etc. Classe imutavel
}
