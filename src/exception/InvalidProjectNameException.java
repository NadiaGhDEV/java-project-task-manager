package exception;

public class InvalidProjectNameException extends RuntimeException {
    public InvalidProjectNameException(String name) {
        super(name + "cannot be a project name");
    }

}
