package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

import java.io.FileWriter;

public class CsvReportAdapter implements IReportExporter {
    public void export(ReportData data, String path) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write("Title,Description,State\n");
            for (ITask t : data.all()) //tasks in reportdata - change
                fw.write(esc(t.getTitle()) + "," + esc(t.getDescription()) + "," + esc(t.getState().display()) + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace(",", ";");
    }
}