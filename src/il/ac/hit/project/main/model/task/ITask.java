package il.ac.hit.project.main.model.task;

/**
 * Core abstraction for a task entity in the system.
 * <p>
 * This interface defines the contract for any task-like object,
 * regardless of its underlying implementation (e.g., persisted in a DB,
 * loaded from a file, or created in-memory).
 * </p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Expose a unique identifier for the task.</li>
 *   <li>Provide access to the task's title and description.</li>
 *   <li>Expose the current {@link TaskState} (e.g., ToDo, InProgress, Completed).</li>
 * </ul>
 *
 * <p>Implementations of this interface are expected to be immutable or at least
 * consistent, so that consumers (like the ViewModel or ReportVisitor) can rely on them.</p>
 */
public interface ITask {

    /**
     * Returns the unique identifier of this task.
     *
     * @return the task ID (should be positive and unique within persistence layer)
     */
    int getId();

    /**
     * Returns the short title of this task.
     *
     * @return the title of the task (never {@code null})
     */
    String getTitle();

    /**
     * Returns the longer, human-readable description of this task.
     *
     * @return the description of the task (can be empty but not {@code null})
     */
    String getDescription();

    /**
     * Returns the current logical state of this task.
     *
     * @return the {@link TaskState} representing the task's state
     */
    TaskState getState();
}
