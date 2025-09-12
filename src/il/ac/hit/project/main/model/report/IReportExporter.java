package il.ac.hit.project.main.model.report;

/**
 * Defines a contract for exporting reports generated from tasks.
 * <p>
 * Implementations of this interface decide how the {@link ReportData}
 * is written to an external format (e.g., CSV, PDF, etc.).
 * <p>
 * This abstraction allows the system to support multiple export formats
 * without changing the core reporting logic.
 */
public interface IReportExporter {

    /**
     * Exports the given {@link ReportData} to the target file path.
     *
     * @param data the report data to export (contains tasks and statistics)
     * @param path the file system path where the exported file will be written
     */
    void export(ReportData data, String path);
}
