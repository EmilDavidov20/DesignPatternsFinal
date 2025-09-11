package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

import java.util.List;

public class ReportData {
    private final List<ITask> all;
    private final long todo, inProgress, completed;

    public ReportData(List<ITask> all, long todo, long inProgress, long completed) {
        this.all = all;
        this.todo = todo;
        this.inProgress = inProgress;
        this.completed = completed;
    }

    public List<ITask> all() {
        return all;
    }

    public long todo() {
        return todo;
    }

    public long inProgress() {
        return inProgress;
    }

    public long completed() {
        return completed;
    }

    public long total() {
        return all == null ? 0 : all.size();
    }
}
