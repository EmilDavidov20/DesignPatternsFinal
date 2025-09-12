package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

/**
 * A record that represents a task currently marked as "In Progress"
 * in the reporting subsystem.
 * <p>
 * Implements the {@link ReportTask} marker interface so it can be
 * processed by the {@link ReportVisitor}.
 *
 * <p>Each instance wraps a single {@link ITask}.</p>
 *
 * @param t the underlying task that is "In Progress"
 */
public record InProgressTask(ITask t) implements ReportTask {
    // Being a record, this automatically provides:
    // - A constructor
    // - Accessor method t()
    // - equals(), hashCode(), toString()
}
