package com.bank.bank.DadosUser;

import jakarta.validation.constraints.NotNull;

public record DadosTransferencia(@NotNull int payer_id,@NotNull int receiver_id,@NotNull double amount) {
} 
