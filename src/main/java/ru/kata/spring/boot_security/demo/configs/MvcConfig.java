package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MvcConfig implements WebMvcConfigurer {
//    это интерфейс в Spring Framework, который позволяет настроить конфигурацию
//        приложения, использующего Spring MVC.
    public void addViewControllers(ViewControllerRegistry registry) {
        //ViewControllerRegistry - это интерфейс предоставляет методы для добавления контроллеров и
        // настройки их свойств, таких как путь к представлению (view) и параметры запроса
        // (request parameters). Контроллеры представлений могут использоваться для отображения
        // статических страниц или страниц, которые не требуют обработки данных.
        registry.
                addViewController("/user")
//                addViewControllers - это метод, определенный в интерфейсе WebMvcConfigurer,
//                который позволяет добавлять контроллеры для простых представлений (view) без
//                необходимости создавать отдельный класс контроллера. Этот метод позволяет связать
//                URL-адрес с конкретным представлением, которое будет отображаться при обращении к
//                этому URL-адресу. Например, можно использовать этот метод для настройки страницы
//                приветствия или страницы ошибки 404.
                .setViewName("user");
        //Это метод, который устанавливает имя представления (view name), которое будет использоваться
        // для отображения данных возвращаемых контроллером представления. В данном случае, имя
        // представления установлено как "user".
    }
}
