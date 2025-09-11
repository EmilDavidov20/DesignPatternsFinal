package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

public interface ISortingStrategy {
    Comparator<ITask> getComparator();
}