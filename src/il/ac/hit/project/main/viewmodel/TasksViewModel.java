package il.ac.hit.project.main.viewmodel;

import il.ac.hit.project.main.model.dao.*;
import il.ac.hit.project.main.model.task.*;
import il.ac.hit.project.main.model.report.*;
import il.ac.hit.project.main.view.observable.ObservableProperty;
import il.ac.hit.project.main.viewmodel.combinator.*;
import il.ac.hit.project.main.viewmodel.strategy.*;

import java.util.*;
import java.util.stream.Collectors;

public class TasksViewModel {
    private final ITasksDAO dao = DAOFactory.getInstance().getDAO();
    private final List<ITask> all = new ArrayList<>();
    private java.util.function.Predicate<ITask> filter = TaskFilters.any();
    private ISortingStrategy sort = new SortById();
    public final ObservableProperty<List<ITask>> tasks = new ObservableProperty<>(List.of());

    public void load() throws TasksDAOException {
        all.clear();
        all.addAll(Arrays.asList(dao.getTasks()));
        publish();
    }

    public void setFilter(java.util.function.Predicate<ITask> f) {
        this.filter = f == null ? TaskFilters.any() : f;
        publish();
    }

    public void setSort(ISortingStrategy s) {
        if (s != null) this.sort = s;
        publish();
    }

    private void publish() {
        tasks.setValue(all.stream().filter(filter).sorted(sort.getComparator()).collect(Collectors.toList()));
    }

    public void add(String title, String desc, TaskState state) throws TasksDAOException {
        dao.addTask(new Task(0, title, desc, state));
        load();
    }

    public void update(ITask t) throws TasksDAOException {
        dao.updateTask(t);
        load();
    }

    public void delete(int id) throws TasksDAOException {
        dao.deleteTask(id);
        load();
    }

    public void exportCsv(String path) {
        ReportVisitor v = new ReportVisitor();
        for (ITask t : tasks.getValue()) v.visit(t);
        new CsvReportAdapter().export(v.build(), path);
    }
}