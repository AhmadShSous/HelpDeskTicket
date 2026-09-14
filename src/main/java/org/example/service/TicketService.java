package org.example.service;
import org.example.dto.CreateTicketRequest;
import org.example.dto.TicketResponse;
import org.example.mongo.model.ActivityType;
import org.example.mySql.model.*;
import org.example.mySql.repository.TicketRepository;
import org.example.mySql.repository.EmployeeRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EmployeeRepository employeeRepository;
    private final ActivityLogsService activityLogsService;

    public TicketService(TicketRepository ticketRepository, EmployeeRepository employeeRepository, ActivityLogsService activityLogsService) {
        this.ticketRepository = ticketRepository;
        this.employeeRepository = employeeRepository;
        this.activityLogsService = activityLogsService;
    }


    public TicketResponse createTicket(CreateTicketRequest request, Long requesterId) {

        Employee requester = employeeRepository.findById(requesterId).orElseThrow(() ->
                        new RuntimeException("emp not found"));
        if (requester.getRole() != Role.REQUESTER) {
            throw new RuntimeException("just the REQUESTER can create tickets");
        }

        // DTO to Entity
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setRequester(requester);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setDueAt(calculateDueAt(request.getPriority()));
        Ticket savedTicket = ticketRepository.save(ticket);
        activityLogsService.createActivity(savedTicket.getId(), requesterId, ActivityType.TICKET_CREATED, "Ticket created");
        // Entity to DTO
        return toResponse(savedTicket);
    }


    public TicketResponse findById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Ticket not found"));
        return toResponse(ticket);
    }


    public List<TicketResponse> findAll() {
        return ticketRepository.findAll().stream()
                .map(ticket -> toResponse(ticket))
                .toList();
    }


    private LocalDateTime calculateDueAt(Priority priority) {
        LocalDateTime now = LocalDateTime.now();
        if (priority.equals(Priority.HIGH)){
            return now.plusHours(4);
        } else if (priority.equals(Priority.MEDIUM)) {
            return now.plusHours(24);
        }
        else {
            return now.plusHours(72);
        }
    }


    private TicketResponse toResponse(Ticket ticket) {

        TicketResponse response = new TicketResponse();

        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setPriority(ticket.getPriority());
        response.setStatus(ticket.getStatus());

        response.setRequesterId(ticket.getRequester().getId());

        if (ticket.getAssignedAgent() != null) {

            response.setAssignedAgentId(ticket.getAssignedAgent().getId());
        }

        response.setDueAt(ticket.getDueAt());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());
        return response;
    }

    public TicketResponse claimTicket(Long ticketId, Long employeeId) {
        Employee agent = employeeRepository.findById(employeeId).orElseThrow(() ->
                        new RuntimeException("Emp not found"));
        if (agent.getRole() != Role.AGENT) {
            throw new RuntimeException("just AGENT can claim tickets");
        }
        if (!agent.isActive()) {
            throw new RuntimeException("agent is not active");
        }

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
                        new RuntimeException("Ticket ot found"));
        if (ticket.getAssignedAgent() != null) {
            throw new RuntimeException("Ticket is assigned");
        }

        ticket.setAssignedAgent(agent);
        Ticket savedTicket = ticketRepository.save(ticket);

        activityLogsService.createActivity(savedTicket.getId(), employeeId, ActivityType.TICKET_CLAIMED, "Ticket claimed by agent");
        return toResponse(savedTicket);
    }

    public List<TicketResponse> findTickets(){
        return ticketRepository.findTickets().stream()
                .filter(ticket -> {
                    switch (ticket.getPriority()){
                        case HIGH -> {
                            return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)-ticket.getCreatedAt().toEpochSecond(ZoneOffset.UTC))>4*60*60*1000;
                        }
                        case MEDIUM -> {
                            return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ticket.getCreatedAt().toEpochSecond(ZoneOffset.UTC))>24*60*60*1000;
                        }
                        case LOW -> {
                            return (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)-ticket.getCreatedAt().toEpochSecond(ZoneOffset.UTC))>48*60*60*1000;
                        }
                    }
                    return false;
                }).map(this::toResponse)
                .toList();

    }



    public TicketResponse assignTicket(Long ticketId, Long agentId, Long adminId) {
        Employee admin = employeeRepository.findById(adminId).orElseThrow(() ->
                        new RuntimeException("Admin not found"));
        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("just ADMIN assign ticket");
        }

        Employee agent = employeeRepository.findById(agentId).orElseThrow(() ->
                        new RuntimeException("Agent not found"));
        if (agent.getRole() != Role.AGENT) {
            throw new RuntimeException("Emp must AGENT");
        }
        if (!agent.isActive()) {
            throw new RuntimeException("Agent is not active");
        }

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
                        new RuntimeException("Ticket not found"));

        ticket.setAssignedAgent(agent);

        ticket.setUpdatedAt(LocalDateTime.now());


        Ticket savedTicket = ticketRepository.save(ticket);
        activityLogsService.createActivity(savedTicket.getId(),adminId, ActivityType.TICKET_ASSIGNED, "Ticket assigned by Admin to AGENTID "+ agentId);
        return toResponse(savedTicket);
    }
}