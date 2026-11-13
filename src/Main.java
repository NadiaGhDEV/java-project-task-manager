import service.ProjectService;
import service.TaskService;
import ui.Console;

public class Main {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectService();
        TaskService taskService = new TaskService();
        Console console = new Console(projectService, taskService);
        console.run();
    }
}
