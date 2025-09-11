package report;

import il.ac.hit.project.main.model.report.ReportVisitor;
import il.ac.hit.project.main.model.report.ReportData;
import il.ac.hit.project.main.model.task.ITask;
import il.ac.hit.project.main.model.task.Task;
import il.ac.hit.project.main.model.task.TaskState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReportVisitorTest {

    @Test
    void countsByState_fromVisitedTasks() {
        // Arrange
        ReportVisitor rv = new ReportVisitor();

        ITask t1 = new Task(1, "Task A", "desc", TaskState.ToDo);
        ITask t2 = new Task(2, "Task B", "desc", TaskState.InProgress);
        ITask t3 = new Task(3, "Task C", "desc", TaskState.Completed);
        ITask t4 = new Task(4, "Task D", "desc", TaskState.ToDo);

        // Act
        rv.visit(t1);
        rv.visit(t2);
        rv.visit(t3);
        rv.visit(t4);
        ReportData rd = rv.build();

        // Assert – ספירות
        assertEquals(4, rd.total());
        assertEquals(2, rd.todo());
        assertEquals(1, rd.inProgress());
        assertEquals(1, rd.completed());

        // Assert – תוכן הרשימה שהוחזרה
        assertEquals(4, rd.all().size());
        assertEquals(1, rd.all().get(0).getId());
        assertEquals(TaskState.Completed, rd.all().get(2).getState());
    }

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
