package com.locker.model;

import javax.persistence.*;

/**
 * Created by randyr on 19-4-16.
 */
@Entity
@Table(name = "user_roles", schema = "mydb", catalog = "")
public class UserRolesEntity {
    private long userRoleId;
    private String role;
    private String username;
//    private UserEntity fk_username;

    public UserRolesEntity(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserRolesEntity() {
    }

    @Id
    @Column(name = "user_role_id")
    public long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(long userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRolesEntity that = (UserRolesEntity) o;

        if (userRoleId != that.userRoleId) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userRoleId ^ (userRoleId >>> 32));
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
//
//    @OneToOne
//    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
//    public UserEntity getFk_username() {
//        return fk_username;
//    }
//
//    public void setFk_username(UserEntity fk_username) {
//        this.fk_username = fk_username;
//    }

}
