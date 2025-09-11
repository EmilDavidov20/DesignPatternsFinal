package il.ac.hit.project.main.viewmodel.combinator;

import il.ac.hit.project.main.model.task.*;

import java.util.function.Predicate;

public final class TaskFilters {
//    private TaskFilters() {
//    }

    public static Predicate<ITask> byTitleContains(String q) {
        String s = q == null ? "" : q.toLowerCase();
        return t -> t.getTitle() != null && t.getTitle().toLowerCase().contains(s);
    }

    public static Predicate<ITask> byState(TaskState state) {
        return t -> t.getState() == state;
    }

    public static Predicate<ITask> any() {
        return t -> true;
    }
}