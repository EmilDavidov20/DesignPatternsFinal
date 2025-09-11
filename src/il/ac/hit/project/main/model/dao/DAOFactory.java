package il.ac.hit.project.main.model.dao;

/**
 * DAOFactory is a singleton factory responsible for creating and providing
 * an {@link ITasksDAO} implementation.
 * <p>
 * It first attempts to initialize a Derby-based DAO ({@link TasksDAODerby}).
 * If that fails, it falls back to a file-based DAO ({@link FileTasksDAO}).
 * In both cases, the chosen DAO is wrapped in a {@link TasksDAOProxy} for
 * added flexibility (e.g., logging, transaction control, etc.).
 */
public final class DAOFactory {
    /** Eagerly initialized singleton instance of the factory. */
    private static final DAOFactory INSTANCE = new DAOFactory();

    /** The chosen ITasksDAO implementation wrapped with a proxy. */
    private final ITasksDAO dao;

    /**
     * Private constructor to enforce singleton pattern.
     * <p>
     * Attempts to create a Derby-backed DAO. If it fails (e.g., DB unavailable),
     * falls back to a file-based DAO. If both fail, a RuntimeException is thrown.
     */
    private DAOFactory() {
        ITasksDAO chosen;
        try {
            // Try Derby implementation first
            chosen = TasksDAODerby.getInstance();
        } catch (Exception e1) {
            try {
                // Fallback to file-based DAO if Derby fails
                chosen = new FileTasksDAO();
            } catch (Exception e2) {
                throw new RuntimeException("Failed to init DAO", e2);
            }
        }
        // Wrap the chosen DAO in a proxy (adds behavior like logging, caching, etc.)
        this.dao = new TasksDAOProxy(chosen);
    }

    /**
     * Returns the singleton instance of the DAOFactory.
     *
     * @return the singleton {@code DAOFactory} instance.
     */
    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the chosen {@link ITasksDAO} instance.
     *
     * @return the DAO wrapped in a {@link TasksDAOProxy}.
     */
    public ITasksDAO getDAO() {
        return dao;
    }
}
