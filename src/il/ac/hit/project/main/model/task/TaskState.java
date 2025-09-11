package il.ac.hit.project.main.model.task;

public enum TaskState {
    ToDo("To Do"),
    InProgress("In Progress"),
    Completed("Completed");

    private final String display;

    TaskState(String d) {
        this.display = d;
    }

    public String display() {
        return display;
    }

    public String symbol() {
        return switch (this) {
            case ToDo -> "[ ]";
            case InProgress -> "[~]";
            case Completed -> "[x]";
        };
    }
}
