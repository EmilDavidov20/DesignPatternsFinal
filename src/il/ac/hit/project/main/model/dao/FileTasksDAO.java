package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A file–based implementation of {@link ITasksDAO}.
 * <p>
 * Instead of persisting tasks to a relational database (like Derby),
 * this DAO stores tasks in a CSV file located under the user's home directory.
 * It maintains a simple in-memory map of tasks and synchronizes with the file on every change.
 * This ensures tasks are preserved across program restarts even without a database.
 */
public class FileTasksDAO implements ITasksDAO {
    private final File file;
    private final Map<Integer, ITask> store = new LinkedHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(0);

    /**
     * Constructs a new {@code FileTasksDAO}, initializing the storage file
     * under the user's home directory and loading existing tasks from it.
     *
     * @throws TasksDAOException if the file cannot be loaded.
     */
    public FileTasksDAO() throws TasksDAOException {
        String home = System.getProperty("user.home", ".");
        this.file = new File(home, "tasks_data.csv");
        load();
    }

    /**
     * Maps a raw string from the CSV file into a {@link TaskState}.
     *
     * @param s raw string (e.g., "In Progress", "Completed", or fallback).
     * @return matching {@link TaskState}.
     */
    private static TaskState toState(String s) {
        if ("In Progress".equals(s)) return TaskState.InProgress;
        if ("Completed".equals(s)) return TaskState.Completed;
        return TaskState.ToDo;
    }

    /**
     * Loads all tasks from the CSV file into memory.
     * <p>
     * The file is parsed line by line; each row corresponds to a task.
     * Titles and descriptions are unescaped, and IDs are tracked for sequencing.
     *
     * @throws TasksDAOException if the file cannot be read or parsed.
     */
    private void load() throws TasksDAOException {
        store.clear();
        seq.set(0);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length < 4) continue;
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String desc = parts[2].replace("\\n", "\n");
                TaskState st = toState(parts[3]);
                store.put(id, new Task(id, title, desc, st));
                if (id > seq.get()) seq.set(id);
            }
        } catch (Exception e) {
            throw new TasksDAOException("load file", e);
        }
    }

    /**
     * Saves all in-memory tasks back into the CSV file.
     * <p>
     * Titles and descriptions are escaped to avoid breaking CSV format.
     *
     * @throws TasksDAOException if the file cannot be written.
     */
    private void save() throws TasksDAOException {
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (ITask t : store.values()) {
                String desc = t.getDescription() == null ? "" : t.getDescription()
                        .replace("\n", "\\n").replace(",", ";");
                String title = t.getTitle() == null ? "" : t.getTitle().replace(",", ";");
                pw.println(t.getId() + "," + title + "," + desc + "," + t.getState().display());
            }
        } catch (Exception e) {
            throw new TasksDAOException("save file", e);
        }
    }

    /**
     * Returns all tasks currently stored in memory.
     *
     * @return array of tasks (never {@code null}).
     */
    public ITask[] getTasks() {
        return store.values().toArray(new ITask[0]);
    }

    /**
     * Returns a single task by its ID.
     *
     * @param id task identifier.
     * @return the matching task, or {@code null} if not found.
     */
    public ITask getTask(int id) {
        return store.get(id);
    }

    /**
     * Adds a new task to the store and persists it to file.
     * <p>
     * The task receives a unique auto-incremented ID.
     *
     * @param task task to add.
     * @throws TasksDAOException if saving fails.
     */
    public void addTask(ITask task) throws TasksDAOException {
        int id = seq.incrementAndGet();
        store.put(id, new Task(id, task.getTitle(), task.getDescription(), task.getState()));
        save();
    }

    /**
     * Updates an existing task in the store and persists the change.
     *
     * @param task task with updated values.
     * @throws TasksDAOException if saving fails.
     */
    public void updateTask(ITask task) throws TasksDAOException {
        store.put(task.getId(), task);
        save();
    }

    /**
     * Deletes all tasks from the store and persists the empty state.
     *
     * @throws TasksDAOException if saving fails.
     */
    public void deleteTasks() throws TasksDAOException {
        store.clear();
        save();
    }

    /**
     * Deletes a single task by its ID and persists the change.
     *
     * @param id task identifier.
     * @throws TasksDAOException if saving fails.
     */
    public void deleteTask(int id) throws TasksDAOException {
        store.remove(id);
        save();
    }
}
