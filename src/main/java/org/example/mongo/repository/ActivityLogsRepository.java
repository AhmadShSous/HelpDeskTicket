package org.example.mongo.repository;

import org.example.mongo.model.ActivityLogs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityLogsRepository extends MongoRepository<ActivityLogs,String> {
    public List<ActivityLogs> findByTicketId(long id);
}
