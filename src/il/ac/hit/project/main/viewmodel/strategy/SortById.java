package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

public class SortById implements ISortingStrategy {
    public Comparator<ITask> getComparator() {
        return Comparator.comparingInt(ITask::getId);
    }
}