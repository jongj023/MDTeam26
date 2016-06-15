package com.locker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locker.jsonview.Views;
import com.locker.model.*;
import com.locker.service.LockerService;
import com.locker.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rtheuns on 6/11/16.
 */

@RestController
public class TicketRestController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private LockerService lockerService;

    @RequestMapping(value = "/gettickets/{lockerid}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<TicketEntity>> getTickets(@PathVariable("lockerid") Long lockerid) {
        Iterable<TicketEntity> tickets = ticketService.findAllTickets(lockerid);
        return new ResponseEntity<Iterable<TicketEntity>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/getticket/{ticketid}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TicketEntity> getTicket(@PathVariable("ticketid") Long ticketid) {
        TicketEntity ticket = ticketService.findOne(ticketid);
        return new ResponseEntity<TicketEntity>(ticket, HttpStatus.OK);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/getticketswithenabled/{lockerid}", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody<String> getWithStatus(@RequestBody Integer status, @PathVariable("lockerid") Long lockerid) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        Iterable<TicketEntity> tickets = ticketService.findAllTicketsWithEnabled(status, lockerid);

        //Convert the list of history to JSON format.
        ObjectMapper mapper = new ObjectMapper();
        String JsonResult = "Could not JSON-ify history data.";
        try {
            JsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tickets);
            result.setCode("200");
            result.setMessage("Tickets returned");
            result.setResult(JsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setCode("204");
            result.setMessage("Failed to get tickets.");
            result.setResult(JsonResult);
        }

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/ticket/close", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody<String> closeTicket(@RequestBody Long ticketid) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        ticketService.closeTicket(ticketid);
        result.setCode("200");
        result.setMessage("Closed ticket.");

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/ticket/open", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody<String> openTicket(@RequestBody Long ticketid) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        ticketService.openTicket(ticketid);
        result.setCode("200");
        result.setMessage("Opened ticket.");

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/ticket/save", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody<String> saveTicket(@RequestBody TicketQuery ticketQuery) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        if (ticketQuery.getId() == null || ticketQuery.getTicketTitle().isEmpty() || ticketQuery.getTicketContent().isEmpty()) {
            result.setCode("204");
            result.setMessage("Invalid input, or locker was not found.");
        } else {
            LockerEntity locker = lockerService.findLockerById(ticketQuery.getId());
            if (locker == null) {
                result.setCode("204");
                result.setMessage("Cannot find locker for which this ticket was made.");
            } else {
                TicketEntity ticket = ticketService.save(locker, ticketQuery.getTicketTitle(), ticketQuery.getTicketContent());
                if (ticket == null) {
                    result.setCode("204");
                    result.setMessage("Failed to save ticket. Perhaps the input was incorrect.");
                } else {
                    result.setCode("200");
                    result.setMessage("Successfully saved ticket.");
                }
            }
        }

        return result;
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/ticket/edit", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public AjaxResponseBody<String> editTicket(@RequestBody TicketQuery ticketQuery) {
        AjaxResponseBody<String> result = new AjaxResponseBody<String>();

        if (ticketQuery.getId() == null || ticketQuery.getTicketTitle().isEmpty() || ticketQuery.getTicketContent().isEmpty()) {
            result.setCode("204");
            result.setMessage("Invalid input, or ticket was not found.");
        } else {
            TicketEntity ticket = ticketService.edit(ticketQuery.getId(), ticketQuery.getTicketTitle(), ticketQuery.getTicketContent());
            if (ticket == null) {
                result.setCode("204");
                result.setMessage("Failed to save ticket");
            } else {
                result.setCode("200");
                result.setMessage("Successfully edited ticket.");
            }
        }

        return result;
    }
}
