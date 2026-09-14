package org.example.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ticket_activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLogs {
    @Id
    private String id;
    private Long ticketId;
    private Long empId;
    private ActivityType action;
    private String message;
    private LocalDateTime createdAt;
}