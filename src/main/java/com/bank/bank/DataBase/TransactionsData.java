package com.bank.bank.DataBase;

import com.bank.bank.Entity.Transactions;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionsData extends JpaRepository<Transactions, Integer> {
  //o jpareposityory tem varios metodos pro sql
  @Query("SELECT t FROM Transactions t WHERE t.payer_id = :payer_id")
  List<Transactions> findTransactionsByPayerId(
    @Param("payer_id") Integer payer_id
  );
}
