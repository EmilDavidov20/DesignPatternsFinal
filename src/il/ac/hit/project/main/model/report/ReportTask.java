package il.ac.hit.project.main.model.report;

/**
 * A sealed interface that represents a "report task wrapper".
 * <p>
 * It is part of the Visitor/Report pattern:
 * <ul>
 *   <li>{@link ToDoTask} — wraps a task that is in the {@code ToDo} state</li>
 *   <li>{@link InProgressTask} — wraps a task that is in the {@code InProgress} state</li>
 *   <li>{@link CompletedTask} — wraps a task that is in the {@code Completed} state</li>
 * </ul>
 *
 * <p>This interface is declared {@code sealed}, meaning only the permitted
 * classes ({@link ToDoTask}, {@link InProgressTask}, {@link CompletedTask})
 * are allowed to implement it. This ensures compile-time safety: no other
 * types outside of these can represent a report task.</p>
 *
 * <p>Usage: {@code ReportVisitor} creates instances of these wrappers when
 * traversing tasks, and then pattern matching can be applied on them to
 * generate statistics or output.</p>
 */
public sealed interface ReportTask permits ToDoTask, InProgressTask, CompletedTask {
}
