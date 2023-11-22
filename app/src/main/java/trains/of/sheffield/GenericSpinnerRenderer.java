package trains.of.sheffield;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class GenericSpinnerRenderer<T> extends DefaultTableCellRenderer {
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