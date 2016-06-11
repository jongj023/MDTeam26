package com.locker.service;

import com.locker.dao.TicketRepository;
import com.locker.model.LockerEntity;
import com.locker.model.TicketEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by rtheuns on 6/11/16.
 */

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private LockerService lockerService;

    public void save(TicketEntity ticket) {ticketRepository.save(ticket);}

    public Iterable<TicketEntity> findAllTickets(Long lockerid) {return ticketRepository.findAllTickets(lockerid);}

    public Iterable<TicketEntity> findAllTicketsWithEnabled(int enabled, Long lockerid) {
        return ticketRepository.findAllTicketsWithEnabled(enabled, lockerid);
    }

    public void closeTicket(Long ticketid) {
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        ticket.setEnabled(0);
        ticketRepository.save(ticket);
    }

    public void openTicket(Long ticketid) {
        TicketEntity ticket = ticketRepository.findOne(ticketid);
        ticket.setEnabled(1);
        ticketRepository.save(ticket);
    }

    public TicketEntity save(LockerEntity lockerid, String ticketTitle, String ticketContent) {
        TicketEntity ticket = new TicketEntity();
        ticket.setEnabled(1);
        ticket.setLockerid(lockerid);
        ticket.setTicketTitle(ticketTitle);
        ticket.setTicketContent(ticketContent);
        ticket.setDateCreated(new Timestamp(new java.util.Date().getTime()));
        return ticketRepository.save(ticket);
    }

    public TicketEntity findOne(Long ticketid) {return ticketRepository.findOne(ticketid);}

    public TicketEntity edit(Long ticketid, String newTitle, String newContent) {
        TicketEntity ticket = findOne(ticketid);
        if (ticket == null) return null;
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String edit = "<br/><p class='edit-timestamp'>#Edited by '" + name + "' on " +
                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Timestamp(new java.util.Date().getTime()))+ "</p>"
                 + "<p class='edit-title'>" + ticket.getTicketTitle() + "</p><p class='edit-content'>" + ticket.getTicketContent() + "</p>";

        ticket.setTicketContent(newContent + "\n" + edit);
        ticket.setTicketTitle(newTitle);

        return ticketRepository.save(ticket);
    }
}
