package il.ac.hit.project.main.model.task;

public interface ITask {
    int getId();

    String getTitle();

    String getDescription();

    TaskState getState();
}