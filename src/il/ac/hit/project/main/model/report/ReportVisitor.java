package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.*;

import java.util.ArrayList;
import java.util.List;

public class ReportVisitor {
    private final List<ReportTask> bucket = new ArrayList<>();

    public void visit(ITask task) {
        // ממפה ITask ל-record לפי ה־state
        bucket.add(switch (task.getState()) {
            case ToDo -> new ToDoTask(task);
            case InProgress -> new InProgressTask(task);
            case Completed -> new CompletedTask(task);
        });
    }

    public ReportData build() {
        long todo = 0, inprog = 0, completed = 0;

        // pattern matching על טיפוסי ה-record
        for (ReportTask rt : bucket) {
            switch (rt) {
                case ToDoTask __ -> todo++;
                case InProgressTask __ -> inprog++;
                case CompletedTask __ -> completed++;
            }
        }

        java.util.List<ITask> all = new java.util.ArrayList<>();
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
