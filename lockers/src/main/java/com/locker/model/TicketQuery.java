package com.locker.model;

/**
 * Created by rtheuns on 6/11/16.
 */
public class TicketQuery {

    private Long id;
    private String ticketTitle;
    private String ticketContent;

    public TicketQuery() {

    }

    public TicketQuery(Long id, String ticketTitle, String ticketContent) {
        this.setId(id);
        this.setTicketTitle(ticketTitle);
        this.setTicketContent(ticketContent);
    }


    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getTicketContent() {
        return ticketContent;
    }

    public void setTicketContent(String ticketContent) {
        this.ticketContent = ticketContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
