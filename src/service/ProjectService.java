package service;

import exception.InvalidProjectNameException;
import exception.ProjectNotFoundException;
import model.Project;
import model.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private final List<Project> projects = new ArrayList<>();

    public Project creatProj(String name, String description) {
        if (checkName(name)) {
            throw new InvalidProjectNameException(name);
        }
        Project project = new Project(name, description);
        projects.add(project);
        return project;
    }

    public boolean checkName(String name) {
        if (name == null) {
            return true;
        }

        return projects.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }

    //use stream
    public Project findById(int id) {
        for (Project project : projects) {
            if (project.getProjectId() == id) {
                return project;
            }
        }
        throw new ProjectNotFoundException(id);
    }

    public void deleteProj(int id) {
        Project project = findById(id);
        projects.remove(project);
    }

    public List<Project> getAllProjects() {
        return new ArrayList<>(projects);
    }

    public void updateProj(int id, String name, String description) {
        Project project = findById(id);
        if (checkName(name)) {
            throw new InvalidProjectNameException(name);
        }
        project.setName(name);
        project.setDescription(description);
    }

    public void addTaskToProject(int projectId, Task task) {
        Project p = findById(projectId);
        p.addTask(task);
    }


    public List<Project> searchByName(String keyword) {
        String k = keyword.toLowerCase();
        return projects.stream()
                .filter(p -> p.getName().toLowerCase().contains(k))
                .collect(Collectors.toList());
    }

    public List<Project> searchByTaskCountBetween(int minNum1, int maxNum2) {
        List<Project> tempProj = new ArrayList<>();
        for (Project project : projects) {
            if (project.getTasks().size() > minNum1 && project.getTasks().size() < maxNum2) {
                tempProj.add(project);
            }
        }
        return new ArrayList<>(tempProj);
    }

    public List<Project> sortBy(String field, boolean ascending) {
        Comparator<Project> cmp;
        switch (field.toLowerCase()) {
            case "id":
                cmp = Comparator.comparingInt(Project::getProjectId);
                break;
            case "name":
                cmp = Comparator.comparing(Project::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case "tasks":
                cmp = Comparator.comparingInt(p -> p.getTasks().size());
                break;
            default:
                throw new IllegalArgumentException("Unknown field");
        }
        if (!ascending) cmp = cmp.reversed();
        return projects.stream().sorted(cmp).collect(Collectors.toList());
    }
}

