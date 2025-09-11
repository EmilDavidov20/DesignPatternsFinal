package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.*;

import java.util.*;

public class ReportVisitor {
    private final List<ITask> bucket = new ArrayList<>();

    public void visit(ITask task) {
        bucket.add(task);
    }

    public ReportData build() {
        long todo = 0, inprog = 0, completed = 0;
        for (ITask t : bucket) {
            TaskState s = t.getState();
            switch (s) {
                case ToDo -> todo++;
                case InProgress -> inprog++;
                case Completed -> completed++;
            }
        }
        return new ReportData(bucket, todo, inprog, completed);
    }
}