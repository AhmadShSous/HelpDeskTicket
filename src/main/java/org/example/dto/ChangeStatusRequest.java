package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.mySql.model.TicketStatus;

@Getter
@Setter
public class ChangeStatusRequest {
    @NotNull(message = "Status is required")
    private TicketStatus status;
}


