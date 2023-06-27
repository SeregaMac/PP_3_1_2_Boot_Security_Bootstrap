package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           UserRepository userRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.findAll());
        return "users/saveUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("userOne") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/allUserBoot";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping
    public String pageBoot(Principal principal, Model model) {

        //пользователь
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("thisUser", user);

        //все юзеры
        List<User> listUsers = userService.getUsers();
        model.addAttribute("users", listUsers);

        //роли
        model.addAttribute("rolesList", roleService.findAll());

//        //роли пользователя
//        Authentication authentication = (Authentication) principal;
//        List<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .map(role -> role.replace("ROLE_", ""))
//                .collect(Collectors.toList());
//        model.addAttribute("rolesUser", roles);
        return "users/allUserBoot";
    }
}
