package il.ac.hit.project.main.model.report;

import il.ac.hit.project.main.model.task.ITask;

import java.io.FileWriter;

/**
 * Adapter that exports a {@link ReportData} object into a CSV file.
 * <p>
 * Implements the {@link IReportExporter} interface, converting report
 * information into a comma-separated values format that can be opened
 * with Excel or other spreadsheet tools.
 */
public class CsvReportAdapter implements IReportExporter {

    /**
     * Exports the given {@link ReportData} to a CSV file.
     *
     * @param data the report data containing all tasks and their statistics
     * @param path the file system path where the CSV should be written
     */
    @Override
    public void export(ReportData data, String path) {
        try (FileWriter fw = new FileWriter(path)) {
            // Write CSV header
            fw.write("Title,Description,State\n");

            // Write each task as a line in the CSV file
            for (ITask t : data.all()) {
                fw.write(
                        esc(t.getTitle()) + "," +
                                esc(t.getDescription()) + "," +
                                esc(t.getState().display()) + "\n"
                );
            }
        } catch (Exception e) {
            // Print error if writing fails
            e.printStackTrace();
        }
    }

    /**
     * Escapes text values so they are safe to write into a CSV file.
     * <ul>
     *     <li>Replaces line breaks with spaces.</li>
     *     <li>Replaces commas with semicolons to avoid breaking columns.</li>
     * </ul>
     *
     * @param s the input string
     * @return the escaped version, or empty string if {@code null}
     */
    private String esc(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace(",", ";");
    }
}
