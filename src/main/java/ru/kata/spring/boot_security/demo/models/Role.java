package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {  //надо добавить имплемент GrantedAuthority


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

//    @ManyToMany(cascade = CascadeType.ALL) //означает, что все действия, которые мы выполняем с родительским объектом,
//    @JoinTable(name = "user_role",          // нужно повторить и для его зависимых объектов.
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<User> users;

    public Role() {
    }

//    public void addUserToRole(User user) {
//        if (users == null) {
//            users = new ArrayList<>();
//        }
//        users.add(user);
//    }

    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
