package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(long id) {
        return userRepository.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteUser(id);
    }
}
