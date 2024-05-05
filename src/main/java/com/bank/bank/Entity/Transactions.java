package com.bank.bank.Entity;


import com.bank.bank.DadosUser.DadosTransferencia;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "transactions")
@Entity(name = "Transactions")
@Getter //getters automatico
@NoArgsConstructor //construtor sem argumentos
@AllArgsConstructor //autoexplicativo
@EqualsAndHashCode(of = "transaction_id") //seguranca para ids
public class Transactions {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que é um valor gerado automaticamente, tanto que nao precisa por ele no construtor
    private int transaction_id;

    private int payer_id;
    private int receiver_id;
    private double amount;

//tava dando erro pq o userData tava aqui, aparentemente Jpa somente no controller

    public double checkIfHaveMoney(double amount, User user) throws Exception {
        double result = user.getUserbalance() - amount;
        if (result < 0) {
            throw new Exception("No suficient money");
        }
        return result;
    }

    public Transactions(DadosTransferencia data, int payer_id) {
        this.payer_id = payer_id;
        this.receiver_id = data.receiver_id();
        this.amount = data.amount();
    }
}
