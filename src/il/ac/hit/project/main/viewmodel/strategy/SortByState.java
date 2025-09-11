package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

public class SortByState implements ISortingStrategy {
    public Comparator<ITask> getComparator() {
        return Comparator.comparing(t -> t.getState().display());
    }
}