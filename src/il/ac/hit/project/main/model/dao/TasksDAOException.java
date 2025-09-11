package il.ac.hit.project.main.model.dao;

public class TasksDAOException extends Exception {
    public TasksDAOException(String m) {
        super(m);
    }

    public TasksDAOException(String m, Throwable c) {
        super(m, c);
    }
}