package ru.kata.spring.boot_security.demo.controller;

//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userServiceImpl;

//    @GetMapping(value = "/")
//    public String showUsers(Model model) {
//        List<User> listUsers = userServiceImpl.getUsers();
//        model.addAttribute("users", listUsers);
//        return "users/showAllUsers";
//    }

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/authentificated")
    public String pageForAuthentificated(Principal principal){ //principal короткая информация о пользователе
        return "autenfic " + principal.getName();              //хранится в контексте спринг секюрити
    }

    @GetMapping("/admin")
    public String pageForAdmin(Principal principal){
        return "admin: " + principal.getName();
    }

    @GetMapping("/profile")
    public String pageForProfile(Principal principal){
        return "user: " + principal.getName();
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "users/saveUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/saveUser";
        }
        userServiceImpl.save(user);
        return "redirect:/";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "users/saveUser";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/";
    }
}