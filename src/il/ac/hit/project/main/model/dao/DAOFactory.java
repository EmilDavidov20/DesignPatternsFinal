package il.ac.hit.project.main.model.dao;


public final class DAOFactory {
    private static final DAOFactory INSTANCE = new DAOFactory();
    private final ITasksDAO dao;

    private DAOFactory() {
        ITasksDAO chosen;
        try {
            chosen = TasksDAODerby.getInstance();
        } catch (Exception e1) {
            try {
                chosen = new FileTasksDAO();
            } catch (Exception e2) {
                throw new RuntimeException("Failed to init DAO", e2);
            }
        }
        this.dao = new TasksDAOProxy(chosen);
    }

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    public ITasksDAO getDAO() {
        return dao;
    }
}