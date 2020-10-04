package com.mdrok._spring_rest.config;

import com.mdrok._spring_rest.AccountRepository;
import com.mdrok._spring_rest.AccountService;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.mdrok._spring_rest")
public class SpringConfig {
/*
    @Bean
    AccountRepository accountRepository(){
        return new AccountRepository();
    }

    @Bean
    public AccountService accountService(){
        return new AccountService(accountRepository());
    }
*/
}
