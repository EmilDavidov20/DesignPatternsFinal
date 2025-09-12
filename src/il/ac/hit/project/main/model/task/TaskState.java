package il.ac.hit.project.main.model.task;

/**
 * Enumeration representing the possible states of a task.
 * <p>
 * Each state has a human-readable display name and a unique symbol
 * used in reports or UI to visually represent the task status.
 * </p>
 *
 * <ul>
 *   <li>{@code ToDo} – task not started yet.</li>
 *   <li>{@code InProgress} – task currently in progress.</li>
 *   <li>{@code Completed} – task finished successfully.</li>
 * </ul>
 */
public enum TaskState {
    ToDo("To Do"),
    InProgress("In Progress"),
    Completed("Completed");

    /**
     * Human-readable label for display in UI or reports.
     */
    private final String display;

    /**
     * Constructs a new {@code TaskState} with the specified display label.
     *
     * @param d human-readable name of the state
     */
    TaskState(String d) {
        this.display = d;
    }

    /**
     * Returns the human-readable label for this state.
     *
     * @return display name (e.g., "To Do", "In Progress", "Completed")
     */
    public String display() {
        return display;
    }

    /**
     * Returns a symbolic representation of this state.
     * <p>
     * Useful for compact reporting:
     * <ul>
     *   <li>{@code [ ]} for {@code ToDo}</li>
     *   <li>{@code [~]} for {@code InProgress}</li>
     *   <li>{@code [x]} for {@code Completed}</li>
     * </ul>
     * </p>
     *
     * @return symbol string representing the state
     */
    public String symbol() {
        return switch (this) {
            case ToDo -> "[ ]";
            case InProgress -> "[~]";
            case Completed -> "[x]";
        };
    }
}
