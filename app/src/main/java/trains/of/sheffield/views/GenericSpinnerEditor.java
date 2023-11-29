package trains.of.sheffield.views;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GenericSpinnerEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;

    public GenericSpinnerEditor(int originalValue, boolean isStockPage) {
        SpinnerNumberModel model = new SpinnerNumberModel(originalValue, 0, Integer.MAX_VALUE, 1);
        this.spinner = new JSpinner(model);
        configureSpinner(isStockPage);
    }

    private void configureSpinner(boolean isStockPage) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);

        spinner.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                stopCellEditing();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        int currentValue = (int) value;
        spinner.setValue(currentValue);
        return spinner;
    }

    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    public JSpinner getSpinner() {
        return spinner;
    }
}
