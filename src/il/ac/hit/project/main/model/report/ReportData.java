package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

import java.util.List;

public record ReportData(List<ITask> tasks, long todo, long inprog, long completed) {
}