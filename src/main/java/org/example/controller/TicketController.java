package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.ChangeStatusRequest;
import org.example.dto.CreateTicketRequest;
import org.example.dto.TicketResponse;
import org.example.mySql.model.*;
import org.example.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService){
        this.ticketService= ticketService;
    }
    /*
    @GetMapping
    public List<TicketResponse> findAll() {
        return ticketService.findAll();
    }*/

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

    @PutMapping("/{ticketId}/status")
    public TicketResponse changeStatus(@PathVariable Long ticketId, @RequestHeader("employeeId") Long employeeId, @Valid @RequestBody ChangeStatusRequest request) {
        return ticketService.changeStatus(ticketId, employeeId, request);
    }


    @PostMapping("/{ticketId}/close")
    public TicketResponse closeTicket( @PathVariable Long ticketId, @RequestHeader("employeeId") Long requesterId) {
        return ticketService.closeTicket(ticketId, requesterId);
    }

    @GetMapping
    public Page<Ticket> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ticketService.findAll(pageable);
    }




}
