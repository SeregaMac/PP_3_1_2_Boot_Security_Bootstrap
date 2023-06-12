package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration  //можно не писать
@EnableWebSecurity  //включаем безопасность
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  //унаследоваться от конф.безоп.спринг
//    private final SuccessUserHandler successUserHandler;
//
//    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
//        this.successUserHandler = successUserHandler;
//    }
//    @Autowired
//    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {  //все настройки безопасности, доступа
        http
                .authorizeRequests()                     //строкой мы говорим предоставить разрешения для следующих url.
                .antMatchers("/authentificated/**"/*, "/index"*/)
                //.permitAll()//разрешает всем пользователям доступ к главной странице и странице "index"
                //.anyRequest()//что для любых других запросов (кроме главной страницы и страницы "index"), пользователь должен быть аутентифицирован
                .authenticated() //Если пользователь не аутентифицирован, он будет перенаправлен на страницу входа в систему.
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/profile/**").hasAnyRole("USER", "ADMIN")
                .and()//для создания цепочки настроек, несколько условий безопасности
                .formLogin()////стандартная форма спринг для аунтефикации
//                .successHandler(successUserHandler)// настройки действий, после успешной аутентификации пользователя,
                // например, перенаправление на определенную страницу или выполнение каких-то других действий
                //.permitAll()//что любой пользователь, имеет доступ к этому URL-адресу или ресурсу без учетных данные
                .and()
                .logout().logoutSuccessUrl("/")//для выхода пользователя из системы
                .permitAll();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource);
//    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =                   //минимальная информация о пользователе
//                User.withDefaultPasswordEncoder()  //стандартный кодировщик паролей для хеширования пароля, для создания тестовых юзеров
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build(); //вызывается для создания и возврата готового объекта.
//        UserDetails admin =                   //минимальная информация о пользователе
//                User.withDefaultPasswordEncoder()  //стандартный кодировщик паролей для хеширования пароля, для создания тестовых юзеров
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN", "USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin); //возвращает юзера из памяти без БД
//    }
     // аутентификация из БД
    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager (DataSource dataSorce){
//        UserDetails user =                   //минимальная информация о пользователе
//                User.withDefaultPasswordEncoder()  //стандартный кодировщик паролей для хеширования пароля, для создания тестовых юзеров
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//        UserDetails admin =                   //минимальная информация о пользователе
//                User.withDefaultPasswordEncoder()  //стандартный кодировщик паролей для хеширования пароля, для создания тестовых юзеров
//                        .username("admin")
//                        .password("admin")
//                        .roles("ADMIN", "USER")
//                        .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSorce);
//        if(jdbcUserDetailsManager.userExists(user.getUsername())){
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if(jdbcUserDetailsManager.userExists(admin.getUsername())){
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){ //шифрование
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        return daoAuthenticationProvider;
//    }

}