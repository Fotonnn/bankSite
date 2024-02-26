package com.bank.bank.DadosUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosLogin(@NotNull int user_id, @NotBlank String userpassword) {
}