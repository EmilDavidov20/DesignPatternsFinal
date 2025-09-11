package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

public record InProgressTask(ITask t) implements ReportTask {
}