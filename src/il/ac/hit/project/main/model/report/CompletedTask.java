package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

/**
 * A {@link ReportTask} record that represents a completed task.
 * <p>
 * This wrapper is used by the {@link ReportVisitor} to distinguish between task states
 * (ToDo, InProgress, Completed) when building reports and statistics.
 *
 * @param t the underlying {@link ITask} instance that is marked as completed
 */
public record CompletedTask(ITask t) implements ReportTask {
}
