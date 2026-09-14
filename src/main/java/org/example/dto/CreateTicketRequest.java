package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.mySql.model.Priority;

@Getter
@Setter
public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Desc is required")
    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;
}