package il.ac.hit.project.main.model.dao;

import il.ac.hit.project.main.model.task.*;

import java.sql.*;
import java.util.*;


public class TasksDAODerby implements ITasksDAO {
    private static TasksDAODerby instance;

    //    private TasksDAODerby() throws TasksDAOException {
//        try {
//            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//            init();
//        } catch (ClassNotFoundException e) {
//            throw new TasksDAOException("Derby EmbeddedDriver not found", e);
//        } catch (SQLException e) {
//            throw new TasksDAOException("init", e);
//        }
//    }
    private TasksDAODerby() throws TasksDAOException {
        try {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException ignore) {
            }

            init();
        } catch (java.sql.SQLException e) {
            throw new TasksDAOException("init", e);
        }
    }


    //    public static synchronized TasksDAODerby getInstance() throws TasksDAOException {
//        if (instance == null) instance = new TasksDAODerby();
//        return instance;
//    }
    public static synchronized ITasksDAO getInstance() throws TasksDAOException {
        if (instance == null) instance = new TasksDAODerby();
        return instance;
    }

    private Connection getConnection() throws SQLException {
        String URL = "jdbc:derby:tasksDB;create=true"; //tasksdb//
        return DriverManager.getConnection(URL);
    }

    private void init() throws SQLException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("CREATE TABLE tasks (id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, title VARCHAR(255), description CLOB, state VARCHAR(32))");
        } catch (SQLException e) {
            if (!"X0Y32".equals(e.getSQLState())) throw e;
        }
    }

    private static TaskState toState(String s) {
//        return switch (s) {
//            case "In Progress" -> TaskState.InProgress;
//            case "Completed" -> TaskState.Completed;
//            default -> TaskState.ToDo;
//        };
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

    public ITask[] getTasks() throws TasksDAOException {
//        try (Connection c = getConnection(); Statement st = c.createStatement()) {
//            ResultSet rs = st.executeQuery("SELECT id,title,description,state FROM tasks ORDER BY id");
//            java.util.List<ITask> list = new ArrayList<>();
//            while (rs.next())
//                list.add(new Task(rs.getInt(1), rs.getString(2), rs.getString(3), toState(rs.getString(4))));
//            return list.toArray(new ITask[0]);
//        } catch (Exception e) {
//            throw new TasksDAOException("getTasks", e);
//        }
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT id,title,description,state FROM tasks ORDER BY id");
            java.util.List<ITask> list = new java.util.ArrayList<>();
            while (rs.next()) {
                list.add(new Task(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        toState(rs.getString("state"))  // ← המיפוי הסלחני
                ));
            }
            return list.toArray(new ITask[0]);
        } catch (Exception e) {
            throw new TasksDAOException("getTasks", e);
        }
    }

    public ITask getTask(int id) throws TasksDAOException {
//        try (Connection c = getConnection();
//             PreparedStatement ps = c.prepareStatement("SELECT id,title,description,state FROM tasks WHERE id=?")) {
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return new Task(rs.getInt(1), rs.getString(2), rs.getString(3), toState(rs.getString(4)));
//            return null;
//        } catch (Exception e) {
//            throw new TasksDAOException("getTask", e);
//        }
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

    //    public void addTask(ITask t) throws TasksDAOException {
//        try (Connection c = getConnection();
//             PreparedStatement ps = c.prepareStatement("INSERT INTO tasks(title,description,state) VALUES(?,?,?)")) {
//            ps.setString(1, t.getTitle());
//            ps.setString(2, t.getDescription());
//            ps.setString(3, t.getState().display());
//            ps.executeUpdate();
//        } catch (Exception e) {
//            throw new TasksDAOException("addTask", e);
//        }
//    }
    @Override
    public void addTask(ITask t) throws TasksDAOException {
        final String sql = "INSERT INTO tasks (title, description, state) VALUES (?,?,?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getState().name());
            ps.executeUpdate();


            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ((Task) t).setId(id);
                    return;
                }
            }


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


    public void updateTask(ITask t) throws TasksDAOException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE tasks SET title=?,description=?,state=? WHERE id=?")) {
            ps.setString(1, t.getTitle());
            ps.setString(2, t.getDescription());
            //ps.setString(3, t.getState().display());
            ps.setString(3, t.getState().name());

            ps.setInt(4, t.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new TasksDAOException("updateTask", e);
        }
    }

    public void deleteTasks() throws TasksDAOException {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM tasks");
        } catch (Exception e) {
            throw new TasksDAOException("deleteTasks", e);
        }
    }

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