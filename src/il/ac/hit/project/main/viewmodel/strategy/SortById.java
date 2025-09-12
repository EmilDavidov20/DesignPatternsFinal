package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

/**
 * Sorting strategy that orders {@link ITask} objects by their unique ID.
 * <p>
 * This class is part of the Strategy design pattern. It implements
 * {@link ISortingStrategy} to provide sorting based on the {@code id} field
 * of tasks.
 */
public class SortById implements ISortingStrategy {

    /**
     * Returns a comparator that sorts tasks by their {@code id} in ascending order.
     *
     * @return a comparator comparing {@link ITask} objects by {@link ITask#getId()}
     */
    @Override
    public Comparator<ITask> getComparator() {
        // Uses Comparator.comparingInt for efficient integer comparison
        return Comparator.comparingInt(ITask::getId);
    }
}
