package il.ac.hit.project.main.model.dao;

/**
 * A checked exception that represents errors related to
 * {@link ITasksDAO} implementations (database, file, etc.).
 * <p>
 * This exception is thrown whenever a DAO operation fails
 * due to SQL issues, I/O errors, or other persistence problems.
 */
public class TasksDAOException extends Exception {

    /**
     * Constructs a new {@code TasksDAOException} with the specified detail message.
     *
     * @param m the detail message that explains the cause of the error
     */
    public TasksDAOException(String m) {
        super(m);
    }

    /**
     * Constructs a new {@code TasksDAOException} with the specified detail message
     * and cause.
     *
     * @param m the detail message that explains the error
     * @param c the cause of the error (maybe {@code null} if unknown)
     */
    public TasksDAOException(String m, Throwable c) {
        super(m, c);
    }
}
