
package org.example.service;

import org.example.mongo.model.ActivityLogs;
import org.example.mongo.model.ActivityType;
import org.example.mongo.repository.ActivityLogsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogsService {

    private final ActivityLogsRepository activityLogsRepository;

    public ActivityLogsService(
            ActivityLogsRepository activityLogsRepository) {
        this.activityLogsRepository = activityLogsRepository;
    }

    public void createActivity(Long ticketId, Long empId, ActivityType action, String message) {

        ActivityLogs activity = new ActivityLogs();

        activity.setTicketId(ticketId);
        activity.setEmpId(empId);
        activity.setAction(action);
        activity.setMessage(message);
        activity.setCreatedAt(LocalDateTime.now());
        activityLogsRepository.save(activity);
    }

    public List<ActivityLogs> findByTicketId(Long ticketId) {
        return activityLogsRepository
                .findByTicketId(ticketId).stream().toList();
    }
}