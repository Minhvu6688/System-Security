package service;

import model.Log;
import model.User;

import java.util.List;

public interface ILogService {
    void logAction(User user, String action);

    //phuong thuc lay tat ca cac role
    List<Log> getAllLogs();
}
