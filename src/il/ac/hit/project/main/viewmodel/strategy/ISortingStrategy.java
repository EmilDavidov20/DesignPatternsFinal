package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

/**
 * Strategy interface for defining custom sorting logic for {@link ITask} objects.
 * <p>
 * This interface is part of the Strategy design pattern used in the project.
 * Implementations of this interface provide different ways to compare tasks
 * (e.g., by ID, by title, by state).
 */
public interface ISortingStrategy {

    /**
     * Returns a {@link Comparator} that defines how {@link ITask} objects
     * should be compared for sorting purposes.
     *
     * @return a comparator for sorting tasks according to the chosen strategy
     */
    Comparator<ITask> getComparator();
}
