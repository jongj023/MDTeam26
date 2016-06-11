package com.locker.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by rtheuns on 6/11/16.
 */

@Entity
@Table(name = "ticket")
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketid")
    private Long ticketid;

    @JoinColumn(name = "lockerid", referencedColumnName = "lockerid")
    @ManyToOne
    private LockerEntity lockerid;

    @Basic
    @Column(name = "ticket_title")
    private String ticketTitle;

    @Basic
    @Column(name = "ticket_content")
    private String ticketContent;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private int enabled;

    @Basic
    @Column(name = "date_created")
    private Timestamp dateCreated;

    public TicketEntity(){}


    public Long getTicketid() {
        return ticketid;
    }

    public void setTicketid(Long ticketid) {
        this.ticketid = ticketid;
    }

    public LockerEntity getLockerid() {
        return lockerid;
    }

    public void setLockerid(LockerEntity lockerid) {
        this.lockerid = lockerid;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }
}
