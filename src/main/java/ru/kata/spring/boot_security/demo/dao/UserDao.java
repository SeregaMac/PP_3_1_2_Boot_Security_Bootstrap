package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    void save(User user);

    User getUser(long id);

    void deleteUser(long id);
}
