package com.locker.dao;

import com.locker.model.TicketEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by rtheuns on 6/11/16.
 */

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, Long> {

    @Query(value = "SELECT * FROM ticket t WHERE t.lockerid = :lockerid ORDER BY t.date_created DESC", nativeQuery = true)
    Iterable<TicketEntity> findAllTickets(@Param("lockerid") Long lockerid);

    @Query(value = "SELECT * FROM ticket t WHERE t.enabled = :enabled AND t.lockerid = :lockerid ORDER BY t.date_created DESC", nativeQuery = true)
    Iterable<TicketEntity> findAllTicketsWithEnabled(@Param("enabled") int enabled, @Param("lockerid") Long lockerid);

//    @Query(value = "UPDATE ticket t SET t.enabled = 0 WHERE t.ticketid = :ticketid", nativeQuery = true)
//    void closeTicket(@Param("ticketid") Long ticketid);
//
//    @Query(value = "UPDATE ticket t SET t.enabled = 1 WHERE t.ticketid = :ticketid", nativeQuery = true)
//    void openTicket(@Param("ticketid") Long ticketid);
}
