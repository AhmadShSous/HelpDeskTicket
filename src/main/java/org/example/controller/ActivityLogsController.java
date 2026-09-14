
package org.example.controller;

import org.example.mongo.model.ActivityLogs;
import org.example.service.ActivityLogsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class ActivityLogsController {
    private final ActivityLogsService activityLogsService;
    public ActivityLogsController(ActivityLogsService activityLogsService) {
        this.activityLogsService = activityLogsService;
    }

    @GetMapping("/{ticketId}/activities")
    public List<ActivityLogs> getActivities(@PathVariable Long ticketId) {
        return activityLogsService.findByTicketId(ticketId);
    }

}
