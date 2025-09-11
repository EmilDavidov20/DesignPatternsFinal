package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.ITask;

/**
 * Data-access contract for working with tasks in a persistence layer.
 * <p>
 * Implementations of this interface can use different storage backends,
 * such as databases (e.g., Derby) or flat files (CSV). The contract
 * ensures a consistent API for the rest of the application.
 * <ul>
 *   <li>Never return {@code null} arrays (return an empty array instead).</li>
 *   <li>Throw {@link TasksDAOException} to signal persistence-related failures.</li>
 * </ul>
 */
public interface ITasksDAO {

    /**
     * Retrieves all tasks from the underlying data source.
     *
     * @return an array of tasks (never {@code null}, may be empty).
     * @throws TasksDAOException if the retrieval fails.
     */
    ITask[] getTasks() throws TasksDAOException;

    /**
     * Retrieves a specific task by its ID.
     *
     * @param id the unique identifier of the task.
     * @return the task with the given ID, or {@code null} if not found.
     * @throws TasksDAOException if the retrieval fails.
     */
    ITask getTask(int id) throws TasksDAOException;

    /**
     * Adds a new task to the data source.
     *
     * @param task the task to add.
     * @throws TasksDAOException if the operation fails.
     */
    void addTask(ITask task) throws TasksDAOException;

    /**
     * Updates an existing task in the data source.
     *
     * @param task the task with updated values.
     * @throws TasksDAOException if the operation fails.
     */
    void updateTask(ITask task) throws TasksDAOException;

    /**
     * Deletes all tasks from the data source.
     *
     * @throws TasksDAOException if the operation fails.
     */
    void deleteTasks() throws TasksDAOException;

    /**
     * Deletes a single task by its ID.
     *
     * @param id the unique identifier of the task to delete.
     * @throws TasksDAOException if the operation fails.
     */
    void deleteTask(int id) throws TasksDAOException;
}
