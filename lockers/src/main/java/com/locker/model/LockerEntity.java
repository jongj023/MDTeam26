/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.locker.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Moenish Baladien
 */
@Entity
@Table(name = "locker")
public class LockerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lockerid")
    public Long lockerid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "locker_floor")
    private int lockerFloor;

    @Basic(optional = false)
    @NotNull
    @Column(name = "locker_number")
    @Min(0)
    private String lockerNumber;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "locker_tower")
    private String lockerTower;

    @OneToOne(optional = true)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private UserEntity user;

    @Basic(optional = false)
    @Column(name = "date_acquired")
    private Timestamp timestamp;

    @Basic(optional = false)
    @Column(name = "date_expired")
    private Date date;

    @Basic(optional = true)
    @Column(name = "comment")
    private String comment;

    public LockerEntity() {
    }

    public LockerEntity(Long lockerid) {
        this.lockerid = lockerid;
    }

    public LockerEntity(Long lockerid, int lockerFloor, String lockerNumber, String lockerTower) {
        this.lockerid = lockerid;
        this.lockerFloor = lockerFloor;
        this.lockerNumber = lockerNumber;
        this.lockerTower = lockerTower;
    }

    public LockerEntity(int lockerFloor, String lockerNumber, String lockerTower) {
        this.lockerFloor = lockerFloor;
        this.lockerNumber = lockerNumber;
        this.lockerTower = lockerTower;
    }

    public Long getLockerid() {
        return lockerid;
    }

    public void setLockerid(Long lockerid) {
        this.lockerid = lockerid;
    }

    public int getLockerFloor() {
        return lockerFloor;
    }

    public void setLockerFloor(int lockerFloor) {
        this.lockerFloor = lockerFloor;
    }

    public String getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(String lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public String getLockerTower() {
        return lockerTower;
    }

    public void setLockerTower(String lockerTower) {
        this.lockerTower = lockerTower;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Timestamp getTimestamp() {return timestamp;}

    public void setTimestamp(Timestamp timestamp) {this.timestamp = timestamp;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lockerid != null ? lockerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LockerEntity)) {
            return false;
        }
        LockerEntity other = (LockerEntity) object;
        if ((this.lockerid == null && other.lockerid != null) || (this.lockerid != null && !this.lockerid.equals(other.lockerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return lockerid +"\t"+ lockerTower +"\t"+ lockerFloor +"\t"+ lockerNumber +"\t"+ user +"\t"+ timestamp +"\t"+ date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
