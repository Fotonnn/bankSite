package com.bank.bank.DataBase;

import com.bank.bank.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsData extends JpaRepository<Transactions, Integer> { //o jpareposityory tem varios metodos pro sql
}
