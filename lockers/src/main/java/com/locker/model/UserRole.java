package com.locker.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by randyr on 2/20/16.
 */

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @NotNull
    @Column(name = "user_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_role_id;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "role")
    private String role;

    public UserRole() {
    }

    public UserRole(String username, String role) {
        this.username = username;
        this.role = role;
    }


    public long getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(long user_role_id) {
        this.user_role_id = user_role_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
