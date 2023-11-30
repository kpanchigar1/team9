package trains.of.sheffield.views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * GenericSpinnerRenderer.java
 * Used to create a spinner renderer for the JTable
 * This is used for the stock page and the route page to allow the user to view the number of items in their order line
 */
public class GenericSpinnerRenderer<T> extends DefaultTableCellRenderer {

    /**
     * Creates a spinner renderer
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Comparable) {
            T originalValue = (T) value;
            JSpinner spinner = new JSpinner(new SpinnerNumberModel((Integer )originalValue + 0, 0, Integer.MAX_VALUE, 1));
            spinner.setBorder(null);
            return spinner;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}