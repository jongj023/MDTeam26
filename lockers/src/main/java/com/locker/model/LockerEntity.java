package com.locker.model;

import javax.persistence.*;

/**
 * Created by randyr on 19-4-16.
 */
@Entity
@Table(name = "locker", schema = "mydb", catalog = "")
public class LockerEntity {
    private long lockerid;
    private int lockerFloor;
    private int lockerNumber;
    private String lockerTower;
    private UserEntity fk_username;

    @Id
    @Column(name = "lockerid")
    public long getLockerid() {
        return lockerid;
    }

    public void setLockerid(long lockerid) {
        this.lockerid = lockerid;
    }

    @Basic
    @Column(name = "locker_floor")
    public int getLockerFloor() {
        return lockerFloor;
    }

    public void setLockerFloor(int lockerFloor) {
        this.lockerFloor = lockerFloor;
    }

    @Basic
    @Column(name = "locker_number")
    public int getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(int lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    @Basic
    @Column(name = "locker_tower")
    public String getLockerTower() {
        return lockerTower;
    }

    public void setLockerTower(String lockerTower) {
        this.lockerTower = lockerTower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockerEntity that = (LockerEntity) o;

        if (lockerid != that.lockerid) return false;
        if (lockerFloor != that.lockerFloor) return false;
        if (lockerNumber != that.lockerNumber) return false;
        if (lockerTower != null ? !lockerTower.equals(that.lockerTower) : that.lockerTower != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (lockerid ^ (lockerid >>> 32));
        result = 31 * result + lockerFloor;
        result = 31 * result + lockerNumber;
        result = 31 * result + (lockerTower != null ? lockerTower.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "username", nullable = false)
    public UserEntity getFk_username() {
        return fk_username;
    }

    public void setFk_username(UserEntity fk_username) {
        this.fk_username = fk_username;
    }
}
