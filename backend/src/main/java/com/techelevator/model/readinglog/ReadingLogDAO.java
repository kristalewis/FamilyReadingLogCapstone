package com.techelevator.model.readinglog;

import java.util.List;

public interface ReadingLogDAO {

    public List<ReadingLog> getChildLogs(int familyId);

    public List<ReadingLog> getAllLogsForUser(int userId);
    
    public void createLog (ReadingLog log);

    public void deleteLog (int logId);

}