package il.ac.hit.project.main.viewmodel.combinator;

import il.ac.hit.project.main.model.task.*;

import java.util.function.Predicate;

/**
 * Utility class providing reusable {@link Predicate} filters for {@link ITask} objects.
 * <p>
 * This class is used by the ViewModel layer to filter tasks based on title,
 * state, or to allow all tasks (no filtering).
 */
public final class TaskFilters {
    /**
     * Creates a filter that checks if a task's title contains the given query string.
     * <p>
     * The match is case-insensitive. If the query is {@code null}, it defaults to an empty string.
     *
     * @param q the query string to search for in the task title
     * @return a predicate that evaluates to {@code true} if the task's title contains the query
     */
    public static Predicate<ITask> byTitleContains(String q) {
        String s = q == null ? "" : q.toLowerCase(); // Normalize query
        return t -> t.getTitle() != null && t.getTitle().toLowerCase().contains(s);
    }

    /**
     * Creates a filter that checks if a task is in a specific {@link TaskState}.
     *
     * @param state the state to match (e.g., {@code TaskState.ToDo})
     * @return a predicate that evaluates to {@code true} if the task is in the given state
     */
    public static Predicate<ITask> byState(TaskState state) {
        return t -> t.getState() == state;
    }

    /**
     * A filter that accepts all tasks.
     * <p>
     * Useful as a "no filter" option.
     *
     * @return a predicate that always returns {@code true}
     */
    public static Predicate<ITask> any() {
        return t -> true;
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TaskFilters() {
    }
}
