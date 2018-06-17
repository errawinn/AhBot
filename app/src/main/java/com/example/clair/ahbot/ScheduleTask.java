package com.example.clair.ahbot;

import java.io.Serializable;

/**
 * Created by clair on 11/3/2018.
 */

public class ScheduleTask implements Serializable {
    private String userID, taskName, taskDueDate,taskDueTime;

    public ScheduleTask(String userID,String taskName, String taskDueDate, String taskDueTime) {
        this.userID         = userID;
        this.taskName       = taskName;
        this.taskDueDate    = taskDueDate;
        this.taskDueTime    = taskDueTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(String taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskDueTime() {
        return taskDueTime;
    }

    public void setTaskDueTime(String taskDueTime) {
        this.taskDueTime = taskDueTime;
    }
}
