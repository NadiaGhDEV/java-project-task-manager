package exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(int id) {
        super("Task with id " + id + " not found.");
    }
}