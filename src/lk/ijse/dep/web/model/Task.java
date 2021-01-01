package lk.ijse.dep.web.model;

import lk.ijse.dep.web.util.Priority;

public class Task {

    private int taskId;
    private String userName;
    private String taskName;
    private String description;
    private Priority Priority;
    private Boolean completed;

    public Task() {
    }

    public Task(int taskId, String userName, String taskName, String description, String priority, Boolean completed) {
        this.taskId = taskId;
        this.userName = userName;
        this.taskName = taskName;
        this.description = description;
        Priority =Priority.valueOf(priority);
        this.completed = completed;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public lk.ijse.dep.web.util.Priority getPriority() {
        return Priority;
    }

    public void setPriority(Priority priority) {
        Priority = priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", userName='" + userName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", Priority=" + Priority +
                ", completed=" + completed +
                '}';
    }

}
