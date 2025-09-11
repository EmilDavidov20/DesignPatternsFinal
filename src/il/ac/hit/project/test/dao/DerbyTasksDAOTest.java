package dao;

import il.ac.hit.project.main.model.dao.ITasksDAO;
import il.ac.hit.project.main.model.dao.TasksDAOException;
import il.ac.hit.project.main.model.dao.TasksDAODerby;
import il.ac.hit.project.main.model.task.ITask;
import il.ac.hit.project.main.model.task.Task;
import il.ac.hit.project.main.model.task.TaskState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DerbyTasksDAOTest {
    @Test
    void testAddAndGetTask_TODO() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test TODO", "Task in TODO", TaskState.ToDo);
        dao.addTask(task);
        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.ToDo, loaded.getState());
    }

    @Test
    void testAddAndGetTask_IN_PROGRESS() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test IN_PROGRESS", "Task in progress", TaskState.InProgress);
        dao.addTask(task);
        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.InProgress, loaded.getState());
    }

    @Test
    void testAddAndGetTask_COMPLETED() throws TasksDAOException {
        ITasksDAO dao = TasksDAODerby.getInstance();
        Task task = new Task(0, "Test COMPLETED", "Task done", TaskState.Completed);
        dao.addTask(task);
        ITask loaded = dao.getTask(task.getId());
        assertEquals(TaskState.Completed, loaded.getState());
    }

}
