package ui;

import model.Priority;
import model.Project;
import model.Status;
import model.Task;
import service.ProjectService;
import service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);
    private final ProjectService projectService;
    private final TaskService taskService;

    public Console(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    public void run() {
        boolean exit = true;
        while (exit) {
            printMenu();
            int choice = intReader("Enter the choice: ");
            try {
                switch (choice) {
                    case 0 -> exit = false;
                    case 1 -> creatProj();
                    case 2 -> updateProj();
                    case 3 -> removeProj();
                    case 4 -> listProjects();
                    case 5 -> addTask();
                    case 6 -> changeTaskStatus();
                    case 7 -> setTaskPriority();
                    case 8 -> setTaskDeadline();
                    case 9 -> listTasksOfProject();
                    case 10 -> searchProjectsMenu();
                    case 11 -> searchTasksMenu();
                    case 12 -> sortProjectsMenu();
                    case 13 -> sortTasksMenu();
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void creatProj() {
        String name = read("Project name:");
        String description = read("Description:");
        Project project = projectService.creatProj(name, description);
        System.out.println(project + " created successfully");

    }

    private void updateProj() {
        int id = intReader("Project id:");
        String name = read("Project name:");
        String description = read("Description:");
        projectService.updateProj(id, name, description);
        System.out.println("updated successfully");
    }

    private void removeProj() {
        int id = intReader("Project id:");
        projectService.deleteProj(id);
        System.out.println("deleted successfully");
    }

    private void listProjects() {
        projectService.getAllProjects().forEach(System.out::println);
    }

    private void addTask() {
        int id = intReader("Project id:");
        projectService.findById(id);
        String title = read("Task title: ");
        String desc = read("Task description: ");
        System.out.println("Priorities: LOW, MEDIUM, CRITICAL");
        Priority priority = Priority.valueOf(read("Priority: ").toUpperCase());
        Task task = taskService.createTask(title, desc, priority, id);
        projectService.addTaskToProject(id, task);
        System.out.println("Created: " + task);
    }

    private void changeTaskStatus() {
        int id = intReader("Task id:");
        System.out.println("Statuses: TODO, IN_PROGRESS, DONE");
        Status s = Status.valueOf(read("Status:").toUpperCase());
        taskService.changeStatus(id, s);
        System.out.println("Updated.");
    }

    private void setTaskPriority() {
        int id = intReader("Task id:");
        System.out.println("Priorities: LOW, MEDIUM, CRITICAL");
        Priority p = Priority.valueOf(read("Priority: ").toUpperCase());
        taskService.setPriority(id, p);
        System.out.println("Updated.");
    }

    private void setTaskDeadline() {
        int id = intReader("Task id:");
        LocalDate d = LocalDate.parse(read("Deadline (YYYY-MM-DD): "));
        taskService.setDeadline(id, d);
        System.out.println("Updated.");
    }

    private void listTasksOfProject() {
        int pid = intReader("Project id:");
        List<Task> tasks = taskService.getTasksByProject(pid);
        if (tasks.isEmpty()) System.out.println("No tasks.");
        else tasks.forEach(System.out::println);
    }

    private void searchProjectsMenu() {
        System.out.println("1.By name, " +
                "2.By id, " +
                "3.By task count between");
        int c = intReader("Choice:");
        List<Project> result = switch (c) {
            case 1 -> projectService.searchByName(read("Keyword: "));
            case 2 -> List.of(projectService.findById(intReader("Enter id: ")));
            case 3 -> projectService.searchByTaskCountBetween(intReader("Min number: "),intReader("Max number: ")
            );
            default -> List.of();
        };

        result.forEach(System.out::println);
    }

    private void searchTasksMenu() {
        System.out.println("1.By title  2.By status 3.By priority");
        int c = intReader("Choice: ");
        List<Task> result = switch (c) {
            case 1 -> taskService.searchByTitle(read("Keyword: "));
            case 2 ->
                    taskService.searchByStatus(Status.valueOf(read("Status (TODO/IN_PROGRESS/DONE): ").toUpperCase()));
            case 3 ->
                    taskService.searchByPriority(Priority.valueOf(read("Priority (LOW/MEDUME/CRITICAL)").toLowerCase()));
            default -> List.of();
        };
        result.forEach(System.out::println);
    }

    private void sortProjectsMenu() {
        System.out.println("Fields: id, name, tasks");
        String field = read("Field: ");
        boolean asc;
        while (true) {
            String ascending = read("Ascending? (y/n): ");
            if (ascending.equals("y")) {
                asc = true;
                break;
            } else if (ascending.equals("n")) {
                asc = false;
                break;
            }
        }
        projectService.sortBy(field, asc).forEach(System.out::println);
    }

    private void sortTasksMenu() {
        System.out.println("Fields: id, title, status, priority, deadline");
        String field = read("Field: ");
        boolean asc;
        while (true) {
            String ascending = read("Ascending? (y/n): ");
            if (ascending.equals("y")) {
                asc = true;
                break;
            } else if (ascending.equals("n")) {
                asc = false;
                break;
            }
        }
        taskService.sortBy(field, asc).forEach(System.out::println);
    }

    private void printMenu() {
        System.out.println("Consul menu:\n" +
                "1.creat new project\n" +
                "2.update project\n" +
                "3.remove project\n" +
                "4.show all projects\n" +
                "5.add task to project\n" +
                "6.change task status\n" +
                "7.set task priority\n" +
                "8.set deadline for task\n" +
                "9.tasks of the project\n" +
                "10.search project\n" +
                "11.search task\n" +
                "12. Sort projects (by field)\n" +
                "13. Sort tasks (by field)\n" +
                "0.Exit");
    }

    private String read(String inString) {
        System.out.println(inString);
        return scanner.nextLine().trim();
    }

    private int intReader(String inString) {
        System.out.println(inString);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("must be number");
            }
        }
    }
}
