package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

public record CompletedTask(ITask t) implements ReportTask {
}
