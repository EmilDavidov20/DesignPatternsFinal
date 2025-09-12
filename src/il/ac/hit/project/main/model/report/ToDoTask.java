package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

/**
 * A record that represents a task in the {@code ToDo} state.
 * <p>
 * This record is part of the sealed hierarchy {@link ReportTask}, which classifies tasks
 * according to their {@link il.ac.hit.project.main.model.task.TaskState}.
 * It is used by the {@link ReportVisitor} to group and analyze tasks by state
 * when building a {@link ReportData}.
 *
 * <p>Each instance wraps a single {@link ITask}.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * ITask task = new Task(1, "Example", "desc", TaskState.ToDo);
 * ReportTask rt = new ToDoTask(task);
 * }</pre>
 *
 * @param t the original {@link ITask} object in the {@code ToDo} state
 */
public record ToDoTask(ITask t) implements ReportTask {
}
