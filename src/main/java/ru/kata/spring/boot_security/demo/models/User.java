package ru.kata.spring.boot_security.demo.models;


//import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements UserDetails{  //надо добавить имплемент UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Введите имя")
    @Size(min = 3, max = 12, message = "Размер имени от 3 до 12")
    @Column
    private String name;
    @NotEmpty(message = "Введите фамилию")
    @Size(min = 3, max = 12, message = "Размер фамилии от 3 до 12")
    @Column
    private String surname;
    @Min(value = 7, message = "Количество лет не должно быть меньше 7")
    @Column
    private int age;

    @Column(name = "user_name")
    private String username;

    @Column
    private String password;

    @ManyToMany(cascade = CascadeType.ALL) //означает, что все действия, которые мы выполняем с родительским объектом,
    @JoinTable(name = "user_role",          // нужно повторить и для его зависимых объектов.
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public User() {
    }

    public User(String name, String surname, int age) {
        this.surname = surname;
        this.name = name;
        this.age = age;
    }

    public void addRollToUser(Role role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //представляет права доступа пользователя.
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {  //не истек ли срок действия учетной записи пользователя.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {  //заблокирована ли учетная запись пользователя.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {  //истек ли срок действия учетных данных пользователя
        return true;
    }

    @Override
    public boolean isEnabled() { //указывающее, включен ли пользователь в системе.
        return true;
    }

}
