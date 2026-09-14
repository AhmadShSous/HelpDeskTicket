package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.mySql.model.Priority;
import org.example.mySql.model.TicketStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private TicketStatus status;
    private Long requesterId;
    private Long assignedAgentId;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}