package com.recody.recodybackend.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class RecodyUsersConfig {
    
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
//    @Bean(value = "userTopic")
//    @Profile( "test" )
//    NewTopic userTopic(){
//        return new NewTopic( RecodyTopics.USER, 3, (short) 3);
//    }
    
//    @Bean
//    @DependsOn(value = "userTopic")
//    @Profile({"local", "test"})
//    @Order( Ordered.LOWEST_PRECEDENCE )
//    AdminRegistrar adminRegistrar(@Autowired RecodyUserRepository recodyUserRepository){
//        return new AdminRegistrar( recodyUserRepository );
//    }
}
