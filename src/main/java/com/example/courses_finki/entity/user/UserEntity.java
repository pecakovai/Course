package com.example.courses_finki.entity.user;

import com.example.courses_finki.entity.cart.CoursesCart;
import com.example.courses_finki.entity.subject.SubjectEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;


    @Column(name = "email")
    private String email;
    @Column(name = "index", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SubjectEntity> subjects;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<CoursesCart> carts;


    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName,String username, String password, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@finki.ukim.mk";
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String index) {
        this.username = index;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }
}
