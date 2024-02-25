package com.bank.bank.DataBase;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.bank.Entity.User;

public interface UserData extends JpaRepository<User, Integer>{ //o jpareposityory tem varios metodos pro sql
    
}
