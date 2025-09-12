package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

/**
 * Sorting strategy that orders {@link ITask} objects by their title.
 * <p>
 * This class is part of the Strategy design pattern. It implements
 * {@link ISortingStrategy} to provide sorting based on the task title
 * using a locale-sensitive {@link java.text.Collator}.
 */
public class SortByTitle implements ISortingStrategy {

    /**
     * Returns a comparator that sorts tasks by their title.
     * <p>
     * The comparison is done using a {@link java.text.Collator}, which ensures
     * locale-aware ordering (e.g., handles case and accents properly).
     *
     * @return a comparator comparing {@link ITask} objects by their title
     */
    @Override
    public Comparator<ITask> getComparator() {
        // Compare tasks by title with Collator for natural alphabetical order
        return Comparator.comparing(ITask::getTitle, java.text.Collator.getInstance());
    }
}
