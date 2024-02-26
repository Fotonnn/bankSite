package com.bank.bank.DataBase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.bank.Entity.Transactions;

public interface TransactionsData extends JpaRepository<Transactions, Integer>{ //o jpareposityory tem varios metodos pro sql
    
}