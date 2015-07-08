package io.pivotal.accounts.domain;

import io.pivotal.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Component
public class AccountInitializer {

    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    public void insertDemoUser() {
        Account account = new Account();
        account.setId(1);
        account.setAddress("One @ Changi City");
        account.setBalance(new BigDecimal("100000000"));
        account.setCreationdate(new Date());
        account.setEmail("demo@pivotal.io");
        account.setFullname("Demo Pivotal");
        account.setOpenbalance(new BigDecimal("100000000"));
        account.setPasswd("password");
        account.setUserid("demo");
        account.setLogoutcount(new Integer(0));
        account.setLogincount(new Integer(0));
        accountRepository.save(account);
    }

}
