package report;

import il.ac.hit.project.main.model.report.ReportVisitor;
import il.ac.hit.project.main.model.report.ReportData;
import il.ac.hit.project.main.model.task.ITask;
import il.ac.hit.project.main.model.task.Task;
import il.ac.hit.project.main.model.task.TaskState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ReportVisitor}.
 * <p>
 * These tests validate that the visitor correctly aggregates tasks
 * into a {@link ReportData} summary with accurate counts per {@link TaskState}.
 */
public class ReportVisitorTest {

    /**
     * Ensures that when tasks of different {@link TaskState}s are visited,
     * the resulting {@link ReportData} correctly counts each state.
     * <p>
     * Also validates that tasks are preserved in insertion order.
     */
    @Test
    void countsByState_fromVisitedTasks() {
        ReportVisitor rv = new ReportVisitor();

        ITask t1 = new Task(1, "Task A", "desc", TaskState.ToDo);
        ITask t2 = new Task(2, "Task B", "desc", TaskState.InProgress);
        ITask t3 = new Task(3, "Task C", "desc", TaskState.Completed);
        ITask t4 = new Task(4, "Task D", "desc", TaskState.ToDo);

        // Visit tasks
        rv.visit(t1);
        rv.visit(t2);
        rv.visit(t3);
        rv.visit(t4);

        // Build aggregated report
        ReportData rd = rv.build();

        // Validate totals
        assertEquals(4, rd.total());
        assertEquals(2, rd.todo());
        assertEquals(1, rd.inProgress());
        assertEquals(1, rd.completed());

        // Validate task list integrity
        assertEquals(4, rd.all().size());
        assertEquals(1, rd.all().get(0).getId());
        assertEquals(TaskState.Completed, rd.all().get(2).getState());
    }

    /**
     * Ensures that when no tasks are visited,
     * the {@link ReportData} returned contains all zero counts
     * and an empty task list.
     */
    @Test
    void emptyVisitor_returnsZeros() {
        ReportVisitor rv = new ReportVisitor();
        ReportData rd = rv.build();

        assertEquals(0, rd.total());
        assertEquals(0, rd.todo());
        assertEquals(0, rd.inProgress());
        assertEquals(0, rd.completed());
        assertTrue(rd.all().isEmpty());
    }
}
