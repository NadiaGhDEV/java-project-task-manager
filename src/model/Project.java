package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private static int counter = 1;
    private final int projectId;
    private String name;
    private String description;
    private final List<Task> tasks = new ArrayList<>();

    public Project(String name, String description) {
        this.projectId = counter++;
        this.name = name;
        this.description = description;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Project.counter = counter;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    @Override
    public String toString() {
        return String.format("Project[%d] '%s' | tasks=%d", projectId, name, tasks.size());
    }

}
