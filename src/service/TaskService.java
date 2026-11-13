package service;

import exception.TaskNotFoundException;
import model.Priority;
import model.Status;
import model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public Task createTask(String title, String description, Priority priority, int projectId) {
        Task t = new Task(title, description, priority, projectId);
        tasks.add(t);
        return t;
    }

    public Task findById(int id) {
        for (Task task : tasks) {
            if (task.getProjectId() == id) {
                return task;
            }
        }
        throw new TaskNotFoundException(id);
    }

    public void updateTask(int id, String title, String description) {
        Task t = findById(id);
        if (title != null) {
            t.setTitle(title);
        }
        t.setDescription(description);
    }

    public void deleteTask(int id) {
        tasks.remove(findById(id));
    }

    public List<Task> getTasksByProject(int projectId) {
        return tasks.stream()
                .filter(t -> t.getProjectId() == projectId)
                .collect(Collectors.toList());
    }

    public void changeStatus(int id, Status status) {
        findById(id).setStatus(status);
    }

    public void setPriority(int id, Priority priority) {
        findById(id).setPriority(priority);
    }

    public void setDeadline(int id, LocalDate deadline) {
        findById(id).setDeadline(deadline);
    }

    public List<Task> searchByTitle(String keyword) {
        String k = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Task> searchByStatus(Status status) {
        return tasks.stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> searchByPriority(Priority priority) {
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }


    public List<Task> sortBy(String field, boolean ascending) {
        Comparator<Task> cmp;
        switch (field.toLowerCase()) {
            case "id":
                cmp = Comparator.comparingInt(Task::getTaskId);
                break;
            case "title":
                cmp = Comparator.comparing(Task::getTitle, String.CASE_INSENSITIVE_ORDER);
                break;
            case "status":
                cmp = Comparator.comparing(Task::getStatus);
                break;
            case "priority":
                cmp = Comparator.comparing(Task::getPriority);
                break;
            case "deadline":
                cmp = Comparator.comparing(Task::getDeadline,
                        Comparator.nullsLast(Comparator.naturalOrder()));
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
        if (!ascending) cmp = cmp.reversed();
        return tasks.stream().sorted(cmp).collect(Collectors.toList());
    }
}