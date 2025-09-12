package il.ac.hit.project.main;

import il.ac.hit.project.main.view.MainFrame;

/**
 * Application entry point.
 * <p>
 * Launches the Swing-based Tasks Manager UI by creating and displaying
 * the {@link MainFrame}. The use of {@link javax.swing.SwingUtilities#invokeLater}
 * ensures that the UI is initialized on the Event Dispatch Thread (EDT),
 * which is the correct and thread-safe way to start Swing applications.
 */
public class Main {

    /**
     * The standard Java entry point.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Schedule creation of the main window on the Swing EDT
        javax.swing.SwingUtilities.invokeLater(() ->
                new MainFrame().setVisible(true)
        );
    }
}
