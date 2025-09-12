package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.ITask;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A proxy implementation of {@link ITasksDAO} that adds caching capabilities.
 * <p>
 * This class delegates all calls to a real DAO implementation (e.g., Derby or File-based DAO),
 * but caches tasks in memory to reduce database/file access. The cache is invalidated whenever
 * changes are made (add, update, delete).
 */
public class TasksDAOProxy implements ITasksDAO {
    private final ITasksDAO inner; // The real DAO being wrapped
    private final ConcurrentHashMap<Integer, ITask> cache = new ConcurrentHashMap<>();

    /**
     * Creates a new proxy wrapping the given DAO.
     *
     * @param inner the real DAO implementation (Derby, File, etc.)
     */
    public TasksDAOProxy(ITasksDAO inner) {
        this.inner = inner;
    }

    /**
     * Returns all tasks.
     * If the cache is not empty, tasks are returned from the cache.
     * Otherwise, loads from the underlying DAO and fills the cache.
     *
     * @return array of all tasks
     * @throws TasksDAOException if the underlying DAO fails
     */
    public ITask[] getTasks() throws TasksDAOException {
        if (!cache.isEmpty()) return cache.values().toArray(new ITask[0]);
        ITask[] arr = inner.getTasks();
        for (ITask t : arr) cache.put(t.getId(), t);
        return arr;
    }

    /**
     * Returns a specific task by ID.
     * If found in cache, returns it directly.
     * Otherwise, loads it from the underlying DAO and updates the cache.
     *
     * @param id the ID of the task
     * @return the task, or {@code null} if not found
     * @throws TasksDAOException if the underlying DAO fails
     */
    public ITask getTask(int id) throws TasksDAOException {
        ITask c = cache.get(id);
        if (c != null) return c;
        ITask t = inner.getTask(id);
        if (t != null) cache.put(id, t);
        return t;
    }

    /**
     * Adds a new task.
     * Delegates to the underlying DAO and clears the cache.
     *
     * @param t the task to add
     * @throws TasksDAOException if the underlying DAO fails
     */
    public void addTask(ITask t) throws TasksDAOException {
        inner.addTask(t);
        cache.clear();
    }

    /**
     * Updates an existing task.
     * Delegates to the underlying DAO and clears the cache.
     *
     * @param t the task to update
     * @throws TasksDAOException if the underlying DAO fails
     */
    public void updateTask(ITask t) throws TasksDAOException {
        inner.updateTask(t);
        cache.clear();
    }

    /**
     * Deletes all tasks.
     * Delegates to the underlying DAO and clears the cache.
     *
     * @throws TasksDAOException if the underlying DAO fails
     */
    public void deleteTasks() throws TasksDAOException {
        inner.deleteTasks();
        cache.clear();
    }

    /**
     * Deletes a task by ID.
     * Delegates to the underlying DAO and clears the cache.
     *
     * @param id the ID of the task to delete
     * @throws TasksDAOException if the underlying DAO fails
     */
    public void deleteTask(int id) throws TasksDAOException {
        inner.deleteTask(id);
        cache.clear();
    }
}
