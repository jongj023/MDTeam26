/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locker.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moenish Baladien
 */
@Entity
@Table(name = "user_roles")
public class UserRolesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long userRoleId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "role")
    private String role;

    @JoinColumn(name = "username", referencedColumnName = "username")
    @OneToOne
    private UserEntity username;

    public UserRolesEntity() {
    }

    public UserRolesEntity(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public UserRolesEntity(Long userRoleId, String role) {
        this.userRoleId = userRoleId;
        this.role = role;
    }

    public UserRolesEntity(UserEntity userEntity, String role) {
        this.username = userEntity;
        this.role = role;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserEntity getUsername() {
        return username;
    }

    public void setUsername(UserEntity username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userRoleId != null ? userRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRolesEntity)) {
            return false;
        }
        UserRolesEntity other = (UserRolesEntity) object;
        if ((this.userRoleId == null && other.userRoleId != null) || (this.userRoleId != null && !this.userRoleId.equals(other.userRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.locker.model.UserRoles[ userRoleId=" + userRoleId + " ]";
    }
    
}
