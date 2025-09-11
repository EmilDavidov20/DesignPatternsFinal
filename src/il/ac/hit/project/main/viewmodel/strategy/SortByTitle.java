package il.ac.hit.project.main.viewmodel.strategy;

import il.ac.hit.project.main.model.task.ITask;

import java.util.Comparator;

public class SortByTitle implements ISortingStrategy {
    public Comparator<ITask> getComparator() {
        return Comparator.comparing(ITask::getTitle, java.text.Collator.getInstance());
    }
}