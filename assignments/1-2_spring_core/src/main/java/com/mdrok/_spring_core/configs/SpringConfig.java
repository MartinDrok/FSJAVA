package com.mdrok._spring_core.configs;

import com.mdrok._spring_core.repositories.StringRepository;
import com.mdrok._spring_core.services.StringService;
import org.springframework.context.annotation.*;

@Profile("!test")
@Configuration
@ComponentScan("com.mdrok._spring_core")
public class SpringConfig {

    @Bean
    StringRepository stringRepository(){
        return new StringRepository();
    }

    @Bean
    public StringService stringService(){
        return new StringService(stringRepository());
    }

}
