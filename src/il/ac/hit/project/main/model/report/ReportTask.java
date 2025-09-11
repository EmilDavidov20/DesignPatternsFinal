package il.ac.hit.project.main.model.report;

public sealed interface ReportTask permits ToDoTask, InProgressTask, CompletedTask {
}
