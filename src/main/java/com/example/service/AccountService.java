package com.example.service;
import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;

import com.example.exception.UsernamePasswordException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    
    public Account registerAccount(String username, String password){
        if (username.isBlank() || password.length()<3){
            throw new UsernamePasswordException("Username/Password Error Please Change accordingly");
        }
        Account othAccount=accountRepository.findByUsername(username);
        if(othAccount!=null){
            throw new DuplicateUsernameException("Username is Already in Use");
        }
        Account account =new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountRepository.save(account);

    }
    public Account loginAccount(String username, String password){
       Account optAccount= accountRepository.findByUsername(username);

       if(optAccount==null || !optAccount.getPassword().equals(password)){
        throw new UsernamePasswordException("Invalid Login Change Username/Password");
       }
       return optAccount;
    }
    
}
