package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

import java.util.List;

/**
 * A data holder (DTO) class that represents the result of generating a report
 * over a collection of tasks.
 * <p>
 * It stores:
 * <ul>
 *   <li>A list of all tasks included in the report</li>
 *   <li>Statistics: how many tasks are in each state (ToDo, InProgress, Completed)</li>
 *   <li>The total number of tasks</li>
 * </ul>
 * This class is immutable — all fields are {@code final}.
 */
public class ReportData {
    private final List<ITask> all;       // All tasks included in the report
    private final long todo;             // Number of tasks in "ToDo" state
    private final long inProgress;       // Number of tasks in "InProgress" state
    private final long completed;        // Number of tasks in "Completed" state

    /**
     * Constructs a new {@code ReportData} instance with the given statistics.
     *
     * @param all        list of all tasks included in the report
     * @param todo       number of tasks in "ToDo" state
     * @param inProgress number of tasks in "InProgress" state
     * @param completed  number of tasks in "Completed" state
     */
    public ReportData(List<ITask> all, long todo, long inProgress, long completed) {
        this.all = all;
        this.todo = todo;
        this.inProgress = inProgress;
        this.completed = completed;
    }

    /**
     * @return all tasks included in the report
     */
    public List<ITask> all() {
        return all;
    }

    /**
     * @return the number of tasks in "ToDo" state
     */
    public long todo() {
        return todo;
    }

    /**
     * @return the number of tasks in "InProgress" state
     */
    public long inProgress() {
        return inProgress;
    }

    /**
     * @return the number of tasks in "Completed" state
     */
    public long completed() {
        return completed;
    }

    /**
     * @return the total number of tasks included in the report
     */
    public long total() {
        return all == null ? 0 : all.size();
    }
}
