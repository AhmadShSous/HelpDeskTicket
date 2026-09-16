package org.example.Schedule;

import org.example.dto.TicketResponse;
import org.example.mongo.model.ActivityType;
import org.example.mySql.repository.TicketRepository;
import org.example.service.ActivityLogsService;
import org.example.service.TicketService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Component
public class Scheduling {
    private final TicketService ticketService;
    private final ActivityLogsService activityLogsService;
    public Scheduling(TicketService ticketService, ActivityLogsService activityLogsService){
        this.ticketService = ticketService;
        this.activityLogsService = activityLogsService;
    }

    @Scheduled(fixedDelay = 1000000)
    public void scheduleFixedDelayTask() {
        List<TicketResponse> tickets = ticketService.findTickets();
        for (TicketResponse t : tickets) {
            activityLogsService.createActivity(t.getId(),t.getRequesterId(), ActivityType.WARNING,"TMYM");
            }
        }
    }