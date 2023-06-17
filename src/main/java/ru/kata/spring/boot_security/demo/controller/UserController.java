package ru.kata.spring.boot_security.demo.controller;

//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    UserRepository userRepository;



    @GetMapping("/")
    public @ResponseBody String homePage(Principal principal){
        principal.getName();
        return "home";
    }

//    @GetMapping("/authentificated")
//    public String pageForAuthentificated(Principal principal){ //principal короткая информация о пользователе
//        return "autenfic " + principal.getName();              //хранится в контексте спринг секюрити
//    }


    @GetMapping("/profile")
    public String pageForProfile(Principal principal, Model model) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("user", user);
            return "users/profileUser";
    }




}