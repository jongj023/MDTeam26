package com.locker.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by randyr on 5/10/16.
 */

@Entity
@Table(name = "lockerhistory")
public class LockerHistoryEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historyid")
    public Long historyid;

    @JoinColumn(name = "locker", referencedColumnName = "lockerid")
    @ManyToOne
    private LockerEntity locker;

    @JoinColumn(name = "user", referencedColumnName = "username")
    @OneToOne(optional = true)
    private UserEntity user;

    @Basic(optional = false)
    @Column(name = "date_acquired")
    private Timestamp timestamp;

    @Basic(optional = false)
    @Column(name = "date_expired")
    private Date date;

    @Basic
    @Column(name = "date_updated")
    private Timestamp date_updated;

    @Basic
    @Column(name = "action")
    @Size(min = 0, max = 255)
    private String action;

    public LockerHistoryEntity() {
    }

    public LockerHistoryEntity(Long historyid) {
        this.historyid = historyid;
    }

    public Long getHistoryid() {return historyid;}

    public LockerEntity getLocker() {return locker;}

    public void setLocker(LockerEntity locker) {this.locker = locker;}

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

    public void setDate_updated(Timestamp timestamp) {this.date_updated = timestamp;}

    public Timestamp getDate_updated() {return this.date_updated;}

    public String getAction() {return action;};

    public void setAction(String action) {this.action = action;}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyid != null ? historyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LockerEntity)) {
            return false;
        }
        LockerEntity other = (LockerEntity) object;
        if ((this.historyid == null && other.getLockerid() != null) || (this.historyid != null && !this.historyid.equals(other.getLockerid()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return historyid +"\t"+ locker +"\t"+ user +"\t"+ timestamp +"\t"+ date;
    }
}
