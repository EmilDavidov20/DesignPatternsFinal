package view;

import il.ac.hit.project.main.view.MainFrame;
import il.ac.hit.project.main.model.task.TaskState;
import org.junit.jupiter.api.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MainFrame}.
 * <p>
 * These tests validate that the Swing UI is correctly built,
 * focusing on the Sort and Filter combo boxes at the top of the frame.
 */
public class MainFrameTest {

    /**
     * Runs a task on the Swing Event Dispatch Thread (EDT).
     * Ensures that Swing components are created and tested safely.
     */
    private static void runOnEdt(Runnable r) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                SwingUtilities.invokeAndWait(r);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recursively collects all components inside a given Swing container.
     *
     * @param root root container to search
     * @return flat list of all child components
     */
    private static List<Component> allComponents(Container root) {
        List<Component> res = new ArrayList<>();
        Deque<Container> st = new ArrayDeque<>();
        st.push(root);
        while (!st.isEmpty()) {
            Container c = st.pop();
            for (Component child : c.getComponents()) {
                res.add(child);
                if (child instanceof Container cc) {
                    st.push(cc);
                }
            }
        }
        return res;
    }

    /**
     * Ensures that {@link MainFrame} initializes with:
     * <ul>
     *   <li>A "Sort" combo box containing "By ID", "By Title", "By State".</li>
     *   <li>A "Filter" combo box containing all {@link TaskState} values plus an "All" (null) option.</li>
     * </ul>
     * Validates both structure and default selection.
     */
    @Test
    void sortAndFilterComboBox() {
        MainFrame[] holder = new MainFrame[1];

        // Create MainFrame safely on EDT
        runOnEdt(() -> {
            MainFrame f = new MainFrame();
            f.pack();
            holder[0] = f;
        });

        MainFrame frame = holder[0];
        assertNotNull(frame);

        // Collect all combo boxes in the frame
        List<JComboBox<?>> combos = allComponents(frame).stream()
                .filter(c -> c instanceof JComboBox<?>)
                .map(c -> (JComboBox<?>) c)
                .collect(Collectors.toList());

        assertTrue(combos.size() >= 2, "Expected at least 2 combo boxes (sort + filter)");

        // Validate Sort combo box
        JComboBox<?> sortCombo = combos.stream()
                .filter(cb -> {
                    ComboBoxModel<?> m = cb.getModel();
                    return m.getSize() == 3
                            && Objects.equals(m.getElementAt(0), "By ID")
                            && Objects.equals(m.getElementAt(1), "By Title")
                            && Objects.equals(m.getElementAt(2), "By State");
                })
                .findFirst()
                .orElseThrow(() -> new AssertionError("Sort combo (By ID/Title/State) not found"));

        assertEquals("By ID", sortCombo.getItemAt(0));
        assertEquals("By Title", sortCombo.getItemAt(1));
        assertEquals("By State", sortCombo.getItemAt(2));

        // Validate Filter combo box (TaskState values + All)
        int expectedFilterSize = TaskState.values().length + 1;
        JComboBox<?> filterCombo = combos.stream()
                .filter(cb -> cb.getModel().getSize() == expectedFilterSize)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Filter combo with 'All' (null) not found"));

        assertNull(filterCombo.getItemAt(0), "First item in filter combo should be null (All)");
        assertNull(filterCombo.getSelectedItem(), "Selected item should default to null (All)");
    }
}
