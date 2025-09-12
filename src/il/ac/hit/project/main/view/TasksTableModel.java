package il.ac.hit.project.main.view;

import il.ac.hit.project.main.model.task.ITask;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Table model for displaying a list of tasks in a Swing JTable.
 * <p>
 * Provides column names and values mapped from {@link ITask} objects,
 * supporting the columns: ID, Title, Description, and State.
 * This class is used by {@link MainFrame} to bind the data to the task table.
 */
public class TasksTableModel extends AbstractTableModel {

    /**
     * The underlying list of tasks currently displayed in the table.
     */
    private List<ITask> data;

    /**
     * Column names shown in the table header.
     */
    private final String[] cols = {"ID", "Title", "Description", "State"};

    /**
     * Constructs a new table model with the given list of tasks.
     *
     * @param data the list of tasks to display
     */
    public TasksTableModel(List<ITask> data) {
        this.data = data;
    }

    /**
     * Updates the data in the model and refreshes the table.
     *
     * @param d new list of tasks
     */
    public void setData(List<ITask> d) {
        this.data = d;
        fireTableDataChanged(); // notifies JTable to repaint with new data
    }

    /**
     * Returns the number of rows (tasks) in the table.
     *
     * @return row count
     */
    @Override
    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * Returns the number of columns in the table (always 4).
     *
     * @return column count
     */
    @Override
    public int getColumnCount() {
        return cols.length;
    }

    /**
     * Returns the name of the column at the given index.
     *
     * @param c column index
     * @return column name
     */
    @Override
    public String getColumnName(int c) {
        return cols[c];
    }

    /**
     * Returns the value to display at a given row and column.
     *
     * @param r row index
     * @param c column index
     * @return cell value (ID, Title, Description, or State)
     */
    @Override
    public Object getValueAt(int r, int c) {
        ITask t = data.get(r);
        return switch (c) {
            case 0 -> t.getId();               // Task ID
            case 1 -> t.getTitle();            // Task title
            case 2 -> t.getDescription();      // Task description
            case 3 -> t.getState().display();  // Human-readable state
            default -> "";
        };
    }

    /**
     * Returns the task at a specific row.
     *
     * @param row row index
     * @return the {@link ITask} at that row
     */
    public ITask getAt(int row) {
        return data.get(row);
    }
}
