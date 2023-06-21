package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userServiceImpl;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userServiceImpl, RoleService roleService, RoleRepository roleRepository) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;

    }

    @GetMapping
    public String showUsers(Model model) {
        List<User> listUsers = userServiceImpl.getUsers();
        model.addAttribute("users", listUsers);
        return "users/showAllUsers";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.findAll());
        return "users/saveUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/saveUser";
        }
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update/{id}")
    public String updateUserForId(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        model.addAttribute("rolesList", roleService.findAll());
        return "users/saveUser";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }
}
