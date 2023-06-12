package ru.kata.spring.boot_security.demo.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserRepository /*extends JpaRepository<User, Long>*/ {

//    User findByUsername (String username);

    List<User> getUsers();

    void save(User user);

    User getUser(long id);

    void deleteUser(long id);
}
