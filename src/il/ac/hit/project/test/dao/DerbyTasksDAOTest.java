package dao;

import il.ac.hit.project.main.model.dao.ITasksDAO;
import il.ac.hit.project.main.model.dao.TasksDAOException;
import il.ac.hit.project.main.model.dao.TasksDAODerby;
import il.ac.hit.project.main.model.task.ITask;
import il.ac.hit.project.main.model.task.Task;
import il.ac.hit.project.main.model.task.TaskState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TasksDAODerby}.
 * <p>
 * This class ensures that tasks are correctly added to and retrieved
 * from the Derby-backed DAO, validating that their {@link TaskState}
 * is preserved across persistence operations.
 */
public class DerbyTasksDAOTest {

    /**
     * Verifies that a task created with {@link TaskState#ToDo}
     * is correctly persisted and retrieved.
     *
     * @throws TasksDAOException if the DAO operation fails
     */
    @Test
    void testAddAndGetTask_TODO() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test TODO", "Task in TODO", TaskState.ToDo);
        dao.addTask(task);

        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.ToDo, loaded.getState());
    }

    /**
     * Verifies that a task created with {@link TaskState#InProgress}
     * is correctly persisted and retrieved.
     *
     * @throws TasksDAOException if the DAO operation fails
     */
    @Test
    void testAddAndGetTask_IN_PROGRESS() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test IN_PROGRESS", "Task in progress", TaskState.InProgress);
        dao.addTask(task);

        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.InProgress, loaded.getState());
    }

    /**
     * Verifies that a task created with {@link TaskState#Completed}
     * is correctly persisted and retrieved.
     *
     * @throws TasksDAOException if the DAO operation fails
     */
    @Test
    void testAddAndGetTask_COMPLETED() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test COMPLETED", "Task done", TaskState.Completed);
        dao.addTask(task);

        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.Completed, loaded.getState());
    }
}
