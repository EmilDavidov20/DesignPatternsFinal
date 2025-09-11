package il.ac.hit.project.main.model.task;

import java.util.Objects;

public class Task implements ITask {
    private int id;
    private String title;
    private String description;
    private TaskState state;

    public Task(int id, String title, String description, TaskState state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskState getState() {
        return state;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public void setState(TaskState s) {
        this.state = s;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Task t) && t.id == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + title + " [" + state.display() + "]";
    }
}