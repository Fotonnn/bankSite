package com.bank.bank.DataBase;

import com.bank.bank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserData extends JpaRepository<User, Integer> { //o jpareposityory tem varios metodos pro sql
}
