package com.locker.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by randyr on 3/30/16.
 */

@Entity
@Table(name = "locker")
public class Locker {

    @Id
    @NotNull
    @Column(name = "lockerid", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
//    @Column(name = "user_username", unique = true)
    @OneToOne(orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @NotNull
    @Column(name = "locker_tower")
    @Size(min = 1, max = 1)
    private String tower;

    @NotNull
    @Column(name = "locker_floor")
    private int floor;

    @NotNull
    @Column(name = "locker_number")
    private int number;


    public long getLocker_id() {
        return id;
    }

    public void setLocker_id(int locker_id) {
        this.id = locker_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocker_tower() {
        return tower;
    }

    public void setLocker_tower(String locker_tower) {
        this.tower = locker_tower;
    }

    public int getLocker_floor() {
        return floor;
    }

    public void setLocker_floor(int locker_floor) {
        this.floor = locker_floor;
    }

    public int getLocker_number() {
        return number;
    }

    public void setLocker_number(int locker_number) {
        this.number = locker_number;
    }
}
