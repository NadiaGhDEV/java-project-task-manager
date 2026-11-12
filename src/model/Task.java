package model;

import java.time.LocalDate;

public class Task {
    private static int counter = 1;
    private final int taskId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate deadline;
    private int projectId;

    public Task(String title, String description, Priority priority, LocalDate deadline, int projectId) {
        this.taskId = counter++;
        this.title = title;
        this.description = description;
        this.status = Status.TODO;
        //for security , can set default value
        this.priority = priority;
        this.deadline = deadline;
        this.projectId = projectId;
    }


    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

//    deadline
    @Override
    public String toString() {
        return String.format("Task[%d] '%s' | status=%s | priority=%s | deadline=%s | project=%d",
                taskId, title, status, priority, deadline, projectId);
    }
}
