package il.ac.hit.project.main.model.report;

public interface IReportExporter {
    void export(ReportData data, String path);
}