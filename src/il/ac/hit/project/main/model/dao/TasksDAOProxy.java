package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.ITask;

import java.util.concurrent.ConcurrentHashMap;

public class TasksDAOProxy implements ITasksDAO {
    private final ITasksDAO inner;
    private final ConcurrentHashMap<Integer, ITask> cache = new ConcurrentHashMap<>();

    public TasksDAOProxy(ITasksDAO inner) {
        this.inner = inner;
    }

    public ITask[] getTasks() throws TasksDAOException {
        if (!cache.isEmpty()) return cache.values().toArray(new ITask[0]);
        ITask[] arr = inner.getTasks();
        for (ITask t : arr) cache.put(t.getId(), t);
        return arr;
    }

    public ITask getTask(int id) throws TasksDAOException {
        ITask c = cache.get(id);
        if (c != null) return c;
        ITask t = inner.getTask(id);
        if (t != null) cache.put(id, t);
        return t;
    }

    public void addTask(ITask t) throws TasksDAOException {
        inner.addTask(t);
        cache.clear();
    }

    public void updateTask(ITask t) throws TasksDAOException {
        inner.updateTask(t);
        cache.clear();
    }

    public void deleteTasks() throws TasksDAOException {
        inner.deleteTasks();
        cache.clear();
    }

    public void deleteTask(int id) throws TasksDAOException {
        inner.deleteTask(id);
        cache.clear();
    }
}