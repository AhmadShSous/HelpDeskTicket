package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.CreateTicketRequest;
import org.example.dto.TicketResponse;
import org.example.mySql.model.*;
import org.example.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService){
        this.ticketService= ticketService;
    }
    @GetMapping
    public List<TicketResponse> findAll() {
        return ticketService.findAll();
    }

    @GetMapping("/{id}")
    public TicketResponse findById(@PathVariable Long id){
        return ticketService.findById(id);
    }

    @PostMapping
    public TicketResponse createTicket(@RequestHeader("employeeId") Long id,@Valid @RequestBody CreateTicketRequest createTicketRequest){
        return  ticketService.createTicket(createTicketRequest,id);
    }

    @PostMapping("/{ticketId}/claim")
    public TicketResponse claimTicket(@PathVariable Long ticketId, @RequestHeader("employeeId") Long employeeId) {
        return ticketService.claimTicket(ticketId, employeeId);
    }

    @PutMapping("/{ticketId}/assign/{agentId}")
    public TicketResponse assignTicket(@PathVariable Long ticketId, @PathVariable Long agentId,
            @RequestHeader("employeeId") Long adminId) {
        return ticketService.assignTicket(ticketId, agentId, adminId);
    }

}
