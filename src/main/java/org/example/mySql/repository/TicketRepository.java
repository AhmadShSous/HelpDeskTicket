package org.example.mySql.repository;

import org.example.mySql.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = """
                    SELECT *  FROM tickets
                    WHERE status NOT IN ('RESOLVED', 'CLOSED')
                    """, nativeQuery = true
    )
    List<Ticket> findTickets();


}