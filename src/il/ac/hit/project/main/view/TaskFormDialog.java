package il.ac.hit.project.main.view;

import il.ac.hit.project.main.model.task.*;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog window for creating or editing a Task.
 * <p>
 * Provides input fields for title, description, and state,
 * and returns values to the caller once the user confirms with "OK".
 * Used by {@link MainFrame} in Add and Edit operations.
 */
public class TaskFormDialog extends JDialog {

    /**
     * Text field for the task title.
     */
    private final JTextField titleField = new JTextField(20);

    /**
     * Text area for the task description.
     */
    private final JTextArea descArea = new JTextArea(2, 2);

    /**
     * Combo box to choose the task state (ToDo, InProgress, Completed).
     */
    private final JComboBox<TaskState> stateBox = new JComboBox<>(TaskState.values());

    /**
     * Flag indicating whether the dialog was confirmed (OK) or cancelled.
     */
    private boolean ok = false;

    /**
     * Constructs a modal dialog for task creation/editing.
     *
     * @param owner parent frame that owns this dialog
     * @param title title text for the dialog window
     */
    public TaskFormDialog(JFrame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout(20, 20));

        // Build the form with labels and fields
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descArea));
        form.add(new JLabel("State:"));
        form.add(stateBox);
        add(form, BorderLayout.CENTER);

        // Buttons panel with OK and Cancel
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("Cancel");
        btns.add(okBtn);
        btns.add(cancelBtn);
        add(btns, BorderLayout.SOUTH);

        // OK button → mark dialog as confirmed and close
        okBtn.addActionListener(e -> {
            ok = true;
            setVisible(false);
        });

        // Cancel button → mark dialog as cancelled and close
        cancelBtn.addActionListener(e -> {
            ok = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Pre-fills the dialog with initial values for title, description, and state.
     *
     * @param t title text
     * @param d description text
     * @param s task state
     */
    public void setInitial(String t, String d, TaskState s) {
        titleField.setText(t);
        descArea.setText(d);
        stateBox.setSelectedItem(s);
    }

    /**
     * Returns whether the dialog was confirmed with "OK".
     *
     * @return true if OK was clicked, false otherwise
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Gets the current text from the title field.
     *
     * @return entered task title
     */
    public String getTitleText() {
        return titleField.getText();
    }

    /**
     * Gets the current text from the description area.
     *
     * @return entered task description
     */
    public String getDescText() {
        return descArea.getText();
    }

    /**
     * Gets the currently selected {@link TaskState}.
     *
     * @return selected task state
     */
    public TaskState getState() {
        return (TaskState) stateBox.getSelectedItem();
    }
}
