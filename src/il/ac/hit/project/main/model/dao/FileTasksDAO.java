package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileTasksDAO implements ITasksDAO {
    private final File file;
    private final Map<Integer, ITask> store = new LinkedHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(0);

    public FileTasksDAO() throws TasksDAOException {
        String home = System.getProperty("user.home", ".");
        this.file = new File(home, "tasks_data.csv");
        load();
    }

    private static TaskState toState(String s) {
        if ("In Progress".equals(s)) return TaskState.InProgress;
        if ("Completed".equals(s)) return TaskState.Completed;
        return TaskState.ToDo;
    }

    private void load() throws TasksDAOException {
        store.clear();
        seq.set(0);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
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

    private void save() throws TasksDAOException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            for (ITask t : store.values()) {
                String desc = t.getDescription() == null ? "" : t.getDescription().replace("\n", "\\n").replace(",", ";");
                String title = t.getTitle() == null ? "" : t.getTitle().replace(",", ";");
                pw.println(t.getId() + "," + title + "," + desc + "," + t.getState().display());
            }
        } catch (Exception e) {
            throw new TasksDAOException("save file", e);
        }
    }

    public ITask[] getTasks() {
        return store.values().toArray(new ITask[0]);
    }

    public ITask getTask(int id) {
        return store.get(id);
    }

    public void addTask(ITask task) throws TasksDAOException {
        int id = seq.incrementAndGet();
        store.put(id, new Task(id, task.getTitle(), task.getDescription(), task.getState()));
        save();
    }

    public void updateTask(ITask task) throws TasksDAOException {
        store.put(task.getId(), task);
        save();
    }

    public void deleteTasks() throws TasksDAOException {
        store.clear();
        save();
    }

    public void deleteTask(int id) throws TasksDAOException {
        store.remove(id);
        save();
    }
}