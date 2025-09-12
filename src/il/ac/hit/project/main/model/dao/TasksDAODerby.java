package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.*;

import java.sql.*;
import java.util.*;

/**
 * Concrete implementation of {@link ITasksDAO} that stores tasks
 * in an embedded Apache Derby database.
 * <p>
 * This class is a singleton (see {@link #getInstance()}) and provides
 * full CRUD operations on the tasks table. It also handles creating
 * the database schema if it does not exist.
 */
public class TasksDAODerby implements ITasksDAO {
    private static TasksDAODerby instance;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes Derby EmbeddedDriver and creates the tasks table if missing.
     *
     * @throws TasksDAOException if database initialization fails
     */
    private TasksDAODerby() throws TasksDAOException {
        try {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); // load Derby driver
            } catch (ClassNotFoundException ignore) {
            }
            init(); // ensure table exists
        } catch (java.sql.SQLException e) {
            throw new TasksDAOException("init", e);
        }
    }

    /**
     * Returns the single instance of this DAO (Singleton).
     */
    public static synchronized ITasksDAO getInstance() throws TasksDAOException {
        if (instance == null) instance = new TasksDAODerby();
        return instance;
    }

    /**
     * Opens a new connection to the embedded Derby database.
     */
    private Connection getConnection() throws SQLException {
        String URL = "jdbc:derby:tasksDB;create=true"; // auto-create DB if not exists
        return DriverManager.getConnection(URL);
    }

    /**
     * Creates the tasks table if it does not already exist.
     */
    private void init() throws SQLException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE tasks (" +
                            "id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                            "title VARCHAR(255), " +
                            "description CLOB, " +
                            "state VARCHAR(32))"
            );
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e; // X0Y32 = table already exists
        }
    }

    /**
     * Converts a string from DB into a {@link TaskState}.
     * Accepts multiple aliases (e.g., "DONE" → Completed).
     */
    private static TaskState toState(String s) {
        if (s == null) return TaskState.ToDo;
        String x = s.trim();

        for (TaskState ts : TaskState.values())
            if (ts.name().equals(x)) return ts;

        String u = x.toUpperCase();
        if (u.equals("TODO") || u.equals("TO DO")) return TaskState.ToDo;
        if (u.equals("IN_PROGRESS") || u.equals("IN PROGRESS") || u.equals("INPROGRESS")) return TaskState.InProgress;
        if (u.equals("COMPLETED") || u.equals("DONE")) return TaskState.Completed;

        return TaskState.ToDo;
    }

    /**
     * Retrieves all tasks from the database.
     *
     * @return array of tasks, never null
     * @throws TasksDAOException if SQL error occurs
     */
    public ITask[] getTasks() throws TasksDAOException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT id,title,description,state FROM tasks ORDER BY id");
            java.util.List<ITask> list = new java.util.ArrayList<>();
            while (rs.next()) {
                list.add(new Task(
                        rs.getInt(1),        // id
                        rs.getString(2),     // title
                        rs.getString(3),     // description
                        toState(rs.getString("state")) // safe mapping of state
                ));
            }
            return list.toArray(new ITask[0]);
        } catch (Exception e) {
            throw new TasksDAOException("getTasks", e);
        }
    }

    /**
     * Retrieves a single task by its ID.
     *
     * @param id task id
     * @return the task if found, else null
     * @throws TasksDAOException if SQL error occurs
     */
    public ITask getTask(int id) throws TasksDAOException {
        final String sql = "SELECT id,title,description,state FROM tasks WHERE id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            toState(rs.getString("state"))
                    );
                }
                return null;
            }
        } catch (Exception e) {
            throw new TasksDAOException("getTask", e);
        }
    }

    /**
     * Adds a new task to the database and sets its generated ID.
     *
     * @param t the task to add
     * @throws TasksDAOException if SQL error occurs
     */
    @Override
    public void addTask(ITask t) throws TasksDAOException {
        final String sql = "INSERT INTO tasks (title, description, state) VALUES (?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getState().name()); // save enum name
            ps.executeUpdate();

            // First try JDBC-generated keys
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ((Task) t).setId(id);
                    return;
                }
            }

            // Fallback for Derby
            try (Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery("VALUES IDENTITY_VAL_LOCAL()")) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ((Task) t).setId(id);
                    return;
                }
            }

            throw new TasksDAOException("Failed to obtain generated id");

        } catch (SQLException e) {
            throw new TasksDAOException("addTask", e);
        }
    }

    /**
     * Updates an existing task in the database.
     *
     * @param t the task with updated fields
     * @throws TasksDAOException if SQL error occurs
     */
    public void updateTask(ITask t) throws TasksDAOException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "UPDATE tasks SET title=?,description=?,state=? WHERE id=?")) {
            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getState().name()); // store enum name
            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new TasksDAOException("updateTask", e);
        }
    }

    /**
     * Deletes all tasks from the database.
     *
     * @throws TasksDAOException if SQL error occurs
     */
    public void deleteTasks() throws TasksDAOException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM tasks");
        } catch (Exception e) {
            throw new TasksDAOException("deleteTasks", e);
        }
    }

    /**
     * Deletes a specific task by its ID.
     *
     * @param id task id
     * @throws TasksDAOException if SQL error occurs
     */
    public void deleteTask(int id) throws TasksDAOException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM tasks WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new TasksDAOException("deleteTask", e);
        }
    }
}
