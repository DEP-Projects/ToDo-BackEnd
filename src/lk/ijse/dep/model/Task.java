package lk.ijse.dep.model;

import lk.ijse.dep.util.Priority;

public class Task {
    private String userName;
    private String taskName;
    private String description;
    private Priority Priority;
    private Boolean completed;

    public Task() {
    }

    public Task(String userName, String taskName, String description, lk.ijse.dep.util.Priority priority, Boolean completed) {
        this.userName = userName;
        this.taskName = taskName;
        this.description = description;
        Priority = priority;
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

    public lk.ijse.dep.util.Priority getPriority() {
        return Priority;
    }

    public void setPriority(lk.ijse.dep.util.Priority priority) {
        Priority = priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "username='" + userName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", Priority=" + Priority +
                ", completed=" + completed +
                '}';
    }
}
