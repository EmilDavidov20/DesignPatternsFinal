package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

/**
 * Sorting strategy that orders {@link ITask} objects by their state.
 * <p>
 * This class is part of the Strategy design pattern. It implements
 * {@link ISortingStrategy} to provide sorting based on the human-readable
 * {@code display()} value of the {@link il.ac.hit.project.main.model.task.TaskState}.
 */
public class SortByState implements ISortingStrategy {

    /**
     * Returns a comparator that sorts tasks by their state, using the display string
     * of the {@link il.ac.hit.project.main.model.task.TaskState}.
     * <p>
     * For example: "Completed", "In Progress", "To Do".
     *
     * @return a comparator comparing {@link ITask} objects by their state display string
     */
    @Override
    public Comparator<ITask> getComparator() {
        // Compares tasks by the display value of their TaskState
        return Comparator.comparing(t -> t.getState().display());
    }
}
