package il.ac.hit.project.main.view;

import il.ac.hit.project.main.model.task.*;

import javax.swing.*;
import java.awt.*;

public class TaskFormDialog extends JDialog {
    private final JTextField titleField = new JTextField(20);
    private final JTextArea descArea = new JTextArea(0, 0);
    private final JComboBox<TaskState> stateBox = new JComboBox<>(TaskState.values());
    private boolean ok = false;

    public TaskFormDialog(JFrame owner, String title) {
        super(owner, title, true);
        setLayout(new BorderLayout(8, 8));
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Title:"));
        form.add(titleField);
        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descArea));
        form.add(new JLabel("State:"));
        form.add(stateBox);
        add(form, BorderLayout.CENTER);
        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okBtn = new JButton("OK"), cancelBtn = new JButton("Cancel");
        btns.add(okBtn);
        btns.add(cancelBtn);
        add(btns, BorderLayout.SOUTH);
        okBtn.addActionListener(e -> {
            ok = true;
            setVisible(false);
        });
        cancelBtn.addActionListener(e -> {
            ok = false;
            setVisible(false);
        });
        pack();
        setLocationRelativeTo(owner);
    }

    public void setInitial(String t, String d, TaskState s) {
        titleField.setText(t);
        descArea.setText(d);
        stateBox.setSelectedItem(s);
    }

    public boolean isOk() {
        return ok;
    }

    public String getTitleText() {
        return titleField.getText();
    }

    public String getDescText() {
        return descArea.getText();
    }

    public TaskState getState() {
        return (TaskState) stateBox.getSelectedItem();
    }
}