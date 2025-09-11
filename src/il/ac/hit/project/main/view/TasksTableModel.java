package il.ac.hit.project.main.view;

import il.ac.hit.project.main.model.task.ITask;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TasksTableModel extends AbstractTableModel {
    private List<ITask> data;
    private final String[] cols = {"ID", "Title", "Description", "State"};

    public TasksTableModel(List<ITask> data) {
        this.data = data;
    }

    public void setData(List<ITask> d) {
        this.data = d;
        fireTableDataChanged();
    }

    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    public int getColumnCount() {
        return cols.length;
    }

    public String getColumnName(int c) {
        return cols[c];
    }

    public Object getValueAt(int r, int c) {
        ITask t = data.get(r);
        return switch (c) {
            case 0 -> t.getId();
            case 1 -> t.getTitle();
            case 2 -> t.getDescription();
            case 3 -> t.getState().display();
            default -> "";
        };
    }

    public ITask getAt(int row) {
        return data.get(row);
    }
}