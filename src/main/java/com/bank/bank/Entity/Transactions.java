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

    public void makeTransfer(double amount, User payer, User receiver) throws Exception{
        //User payer = userData.getReferenceById(payer_id);
        //User receiver = userData.getReferenceById(receiver_id);
        double saldo = payer.getUserbalance() - amount;
        if (saldo < 0) {
            throw new Exception("Saldo insuficiente");
        }
        payer.setUserbalance(saldo);
        receiver.setUserbalance(receiver.getUserbalance() + amount);
    }

    public Transactions(DadosTransferencia data) {
        this.payer_id = data.payer_id();
        this.receiver_id = data.receiver_id();
        this.amount = data.amount();
    }
}
