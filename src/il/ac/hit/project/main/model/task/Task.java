package il.ac.hit.project.main.model.task;

import java.util.Objects;

/**
 * Concrete implementation of the {@link ITask} interface.
 * <p>
 * Represents a task in the system with an ID, title, description, and {@link TaskState}.
 * Tasks are mutable: their title, description, and state can be updated after creation.
 * </p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Encapsulate task data (ID, title, description, state).</li>
 *   <li>Provide getters/setters for task manipulation.</li>
 *   <li>Define equality based on the unique ID only.</li>
 *   <li>Override {@code toString()} for human-readable representation.</li>
 * </ul>
 */
public class Task implements ITask {
    private int id;
    private String title;
    private String description;
    private TaskState state;

    /**
     * Constructs a new task instance.
     *
     * @param id          the unique identifier of the task
     * @param title       short title of the task
     * @param description detailed description of the task
     * @param state       current {@link TaskState} of the task
     */
    public Task(int id, String title, String description, TaskState state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public String getTitle() {
        return title;
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    public TaskState getState() {
        return state;
    }

    /**
     * Updates the ID of this task.
     *
     * @param id the new ID to assign
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Updates the title of this task.
     *
     * @param t new title
     */
    public void setTitle(String t) {
        this.title = t;
    }

    /**
     * Updates the description of this task.
     *
     * @param d new description
     */
    public void setDescription(String d) {
        this.description = d;
    }

    /**
     * Updates the state of this task.
     *
     * @param s new {@link TaskState}
     */
    public void setState(TaskState s) {
        this.state = s;
    }

    /**
     * Equality check is based on the task ID.
     *
     * @param o other object
     * @return true if the other object is a Task with the same ID
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof Task t) && t.id == id;
    }

    /**
     * Hash code is derived from the task ID.
     *
     * @return hash based on ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return formatted string: "{id}: {title} [{state}]"
     */
    @Override
    public String toString() {
        return id + ": " + title + " [" + state.display() + "]";
    }
}
