package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the Visitor pattern that collects tasks
 * and produces aggregated report statistics.
 * <p>
 * The {@code ReportVisitor} traverses over {@link ITask} objects, wraps each
 * one in a {@link ReportTask} (according to its {@link TaskState}), and then
 * builds a {@link ReportData} object containing:
 * <ul>
 *   <li>The full list of tasks.</li>
 *   <li>The number of tasks in {@code ToDo}, {@code InProgress}, and {@code Completed} states.</li>
 *   <li>The total number of tasks.</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * ReportVisitor rv = new ReportVisitor();
 * rv.visit(task1);
 * rv.visit(task2);
 * ReportData data = rv.build();
 * System.out.println("Completed count: " + data.completed());
 * }</pre>
 */
public class ReportVisitor {

    /**
     * A bucket of wrapped tasks, each represented as a {@link ReportTask}.
     */
    private final List<ReportTask> bucket = new ArrayList<>();

    /**
     * Visits a single {@link ITask}, wrapping it in a {@link ReportTask}
     * based on its {@link TaskState}, and storing it in the bucket.
     *
     * @param task the task to visit and classify
     */
    public void visit(ITask task) {
        bucket.add(switch (task.getState()) {
            case ToDo -> new ToDoTask(task);
            case InProgress -> new InProgressTask(task);
            case Completed -> new CompletedTask(task);
        });
    }

    /**
     * Builds a {@link ReportData} instance by:
     * <ol>
     *   <li>Counting tasks by their state (ToDo, InProgress, Completed).</li>
     *   <li>Reconstructing the original {@link ITask} list.</li>
     *   <li>Packaging the counts and task list into a {@code ReportData} object.</li>
     * </ol>
     *
     * @return a {@link ReportData} object containing all tasks and statistics
     */
    public ReportData build() {
        long todo = 0, inprog = 0, completed = 0;

        // Count tasks by type using pattern matching on ReportTask records
        for (ReportTask rt : bucket) {
            switch (rt) {
                case ToDoTask __ -> todo++;
                case InProgressTask __ -> inprog++;
                case CompletedTask __ -> completed++;
            }
        }

        // Reconstruct original ITask objects
        List<ITask> all = new ArrayList<>();
        for (ReportTask rt : bucket) {
            switch (rt) {
                case ToDoTask(var t) -> all.add(t);
                case InProgressTask(var t) -> all.add(t);
                case CompletedTask(var t) -> all.add(t);
            }
        }

        return new ReportData(all, todo, inprog, completed);
    }
}
