package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //все настройки безопасности, доступа
        http
                .authorizeRequests()
                .antMatchers("/authentificated/**")
                //.permitAll()//разрешает всем пользователям доступ к главной странице и странице "index"
                //.anyRequest()//что для любых других запросов (кроме главной страницы и страницы "index"), пользователь должен быть аутентифицирован
                .authenticated() //Если пользователь не аутентифицирован, он будет перенаправлен на страницу входа в систему.
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/profile/**").hasAnyRole("USER", "ADMIN")
                .and()//для создания цепочки настроек, несколько условий безопасности
                .formLogin()////стандартная форма спринг для аунтефикации
                .successHandler(successUserHandler)// настройки действий, после успешной аутентификации пользователя,
                // например, перенаправление на определенную страницу или выполнение каких-то других действий
                //.permitAll()//что любой пользователь, имеет доступ к этому URL-адресу или ресурсу без учетных данные
                .and()
                .logout().logoutSuccessUrl("/login")//для выхода пользователя из системы
                .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //шифрование
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService((UserDetailsService) userService);
        return daoAuthenticationProvider;
    }

}