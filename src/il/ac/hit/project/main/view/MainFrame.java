package il.ac.hit.project.main.view;

import il.ac.hit.project.main.model.dao.TasksDAOException;
import il.ac.hit.project.main.model.task.*;
import il.ac.hit.project.main.viewmodel.TasksViewModel;
import il.ac.hit.project.main.viewmodel.combinator.TaskFilters;
import il.ac.hit.project.main.viewmodel.strategy.*;
import il.ac.hit.project.main.model.report.ReportVisitor;
import il.ac.hit.project.main.model.report.ReportData;


import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private final TasksViewModel vm = new TasksViewModel();
    private final TasksTableModel model = new TasksTableModel(java.util.List.of());
    private final JTable table = new JTable(model);

    public MainFrame() {
        super("Tasks Manager");
        setMinimumSize(new Dimension(800, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(40, 40));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }
        java.awt.Font ui = new java.awt.Font("Arial", java.awt.Font.PLAIN, 14);
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object k = keys.nextElement();
            Object v = UIManager.get(k);
            if (v instanceof java.awt.Font) {
                UIManager.put(k, ui);
            }
        }

        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);
        JButton add = new JButton("Add"), edit = new JButton("Edit"), del = new JButton("Delete"), btnReport = new JButton("Generate Report"), export = new JButton("Export CSV");
        gc.gridy = 0;
        gc.gridx = 0;
        top.add(new JLabel("Tasks:"), gc);
        gc.gridx++;
        top.add(add, gc);
        gc.gridx++;
        top.add(edit, gc);
        gc.gridx++;
        top.add(del, gc);
        gc.gridx++;
        top.add(export, gc);
        gc.gridx++;
        top.add(btnReport, gc);
        gc.gridy = 1;
        gc.gridx = 0;
        top.add(new JLabel("Sort:"), gc);
        JComboBox<String> sortCombo = new JComboBox<>(new String[]{"By ID", "By Title", "By State"});
        gc.gridx = 1;
        top.add(sortCombo, gc);
        gc.gridx = 2;
        top.add(new JLabel("Filter:"), gc);

        JComboBox<TaskState> filterCombo = new JComboBox<>(TaskState.values());
        filterCombo.insertItemAt(null, 0);
        filterCombo.setSelectedIndex(0);

        filterCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "All" : ((TaskState) value).toString());
                return c;
            }
        });
        gc.gridx = 3;
        top.add(filterCombo, gc);
        gc.gridx = 4;
        top.add(new JLabel("Search Title:"), gc);
        JTextField search = new JTextField(16);
        gc.gridx = 5;
        top.add(search, gc);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        vm.tasks.addObserver((o, n) -> model.setData(n));
        add.addActionListener(e -> onAdd());
        edit.addActionListener(e -> onEdit());
        del.addActionListener(e -> onDelete());

//        btnReport.addActionListener(e -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append("[ ] =  To Do\n").append("[~] =  In Progress\n").append("[x] =  Completed\n\n");
//            for (int i = 0; i < model.getRowCount(); i++) {
//                ITask t = model.getAt(i);
//                String sym = t.getState().symbol();
//                sb.append(sym).append(" #").append(t.getId())
//                        .append(" ").append(t.getTitle() == null ? "" : t.getTitle())
//                        .append("\n");
//
//            }
//            JTextArea area = new JTextArea(sb.toString(), 15, 40);
//            area.setEditable(false);
//            area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 16));
//            JScrollPane sp = new JScrollPane(area);
//            sp.setPreferredSize(new Dimension(500, 400));
//            JOptionPane.showMessageDialog(this, sp, "Report (Preview)", JOptionPane.INFORMATION_MESSAGE);
//        });
        btnReport.addActionListener(e -> {
            ReportVisitor rv = new ReportVisitor();
            for (int i = 0; i < model.getRowCount(); i++) {
                rv.visit(model.getAt(i));
            }
            ReportData rd = rv.build();
            StringBuilder sb = new StringBuilder();
            sb.append("Total: ").append(rd.total()).append("\n")
                    .append("[ ] = To Do: ").append(rd.todo()).append("\n")
                    .append("[~] = In Progress: ").append(rd.inProgress()).append("\n")
                    .append("[x] = Completed: ").append(rd.completed()).append("\n\n")
                    .append("-------------------------------------------\n\n");

            for (ITask t : rd.all()) {
                String sym = t.getState().symbol(); // יש לך כבר symbol() ב-TaskState
                sb.append(sym).append(" #").append(t.getId())
                        .append(" ").append(t.getTitle() == null ? "" : t.getTitle())
                        .append("\n");
            }

            JTextArea area = new JTextArea(sb.toString(), 20, 60);
            area.setEditable(false);
            area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 16));
            JScrollPane sp = new JScrollPane(area);
            sp.setPreferredSize(new Dimension(500, 400));
            JOptionPane.showMessageDialog(this, sp, "Report (Preview)", JOptionPane.INFORMATION_MESSAGE);
        });

        setLocationRelativeTo(null);
        export.addActionListener(e -> onExport());
        sortCombo.addActionListener(e -> {
            String s = (String) sortCombo.getSelectedItem();
            if ("By Title".equals(s)) vm.setSort(new SortByTitle());
            else if ("By State".equals(s)) vm.setSort(new SortByState());
            else vm.setSort(new SortById());
        });
        filterCombo.addActionListener(e -> applyFilter((TaskState) filterCombo.getSelectedItem(), search.getText()));
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            void upd() {
                applyFilter((TaskState) filterCombo.getSelectedItem(), search.getText());
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                upd();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                upd();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                upd();
            }
        });
        try {
            vm.load();
        } catch (TasksDAOException ex) {
            err(ex);
        }
    }

    private void applyFilter(TaskState st, String q) {
        java.util.function.Predicate<ITask> f = (st == null) ? TaskFilters.any() : TaskFilters.byState(st);
        if (q != null && !q.isBlank())
            f = il.ac.hit.project.main.viewmodel.combinator.Combinator.and(f, TaskFilters.byTitleContains(q));
        vm.setFilter(f);
    }

    private void onAdd() {
        TaskFormDialog d = new TaskFormDialog(this, "Add Task");
        while (true) {
            d.setVisible(true);
            if (!d.isOk()) return;

            String title = d.getTitleText() == null ? "" : d.getTitleText().trim();
            String desc = d.getDescText() == null ? "" : d.getDescText().trim();

            if (title.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Title and Description are required.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );
                // נשאר בלולאה ופותח את אותו דיאלוג שוב לתיקון
                d.toFront();
                continue;
            }

            try {
                vm.add(title, desc, d.getState());
            } catch (TasksDAOException ex) {
                err(ex);
            }
            return;
        }
    }

    private void onEdit() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ITask t = model.getAt(r);
        TaskFormDialog d = new TaskFormDialog(this, "Edit Task");
        d.setInitial(t.getTitle(), t.getDescription(), t.getState());

        while (true) {
            d.setVisible(true);
            if (!d.isOk()) return;

            String title = d.getTitleText() == null ? "" : d.getTitleText().trim();
            String desc = d.getDescText() == null ? "" : d.getDescText().trim();

            if (title.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Title and Description are required.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );
                d.toFront();
                continue;
            }

            ((Task) t).setTitle(title);
            ((Task) t).setDescription(desc);
            ((Task) t).setState(d.getState());
            try {
                vm.update(t);
            } catch (TasksDAOException ex) {
                err(ex);
            }
            return;
        }
    }


    private void onDelete() {
        int r = table.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(this, "Select a row first", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ITask t = model.getAt(r);
        if (JOptionPane.showConfirmDialog(this, "Delete task #" + t.getId() + "?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION) {
            try {
                vm.delete(t.getId());
            } catch (TasksDAOException ex) {
                err(ex);
            }
        }
    }

    private void onExport() {
        JFileChooser ch = new JFileChooser(new File("."));
        ch.setSelectedFile(new File("tasks_report.csv"));
        if (ch.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            vm.exportCsv(ch.getSelectedFile().getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Exported to " + ch.getSelectedFile().getAbsolutePath());
        }
    }

    private void err(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}