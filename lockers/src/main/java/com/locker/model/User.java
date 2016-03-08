package com.locker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by randyr on 2/20/16.
 */

@Entity
@Table(name = "user")
public class User {

    @Id
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "email")
    private String email;
    @NotNull
    @Column(name = "firstname")
    private String firstname;
    @NotNull
    @Column(name = "lastname")
    private String lastname;
    @NotNull
    @Column(name = "enabled")
    private int enabled;

    public User() {}

    public User(String username, String password, String email, String firstname, String lastname, int enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "username";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEnabled(int enabled) {this.enabled = enabled;}

    public int getEnabled() {return enabled;}
}
