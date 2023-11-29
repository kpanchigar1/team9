package trains.of.sheffield.views;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class GenericSpinnerEditor<T> extends AbstractCellEditor implements TableCellEditor {
    private final int originalValue;
    private JSpinner spinner;

    public GenericSpinnerEditor(int originalValue, SpinnerNumberModel model, boolean isStockPage) {
        this.originalValue = originalValue;
        this.spinner = new JSpinner(model);
        configureSpinner(isStockPage);
    }

    private void configureSpinner(boolean isStockPage) {
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);

        spinner.addChangeListener(e -> {
            int currentValue = (int) spinner.getValue();
            if (currentValue < originalValue && isStockPage) {
                spinner.setValue(originalValue);
            }
        });

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
        spinner = new JSpinner(new SpinnerNumberModel(currentValue, 0, Integer.MAX_VALUE, 1));
        configureSpinner(true); // Pass the correct flag based on your use case
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
