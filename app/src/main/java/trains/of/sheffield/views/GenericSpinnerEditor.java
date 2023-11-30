package trains.of.sheffield.views;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * GenericSpinnerEditor.java
 * Used to create a spinner editor for the JTable
 * This is used for the stock page and the route page to allow the user to edit the number of items in their order line
 */
public class GenericSpinnerEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;

    public GenericSpinnerEditor(int originalValue, boolean isStockPage) {
        SpinnerNumberModel model = new SpinnerNumberModel(originalValue, 0, Integer.MAX_VALUE, 1);
        this.spinner = new JSpinner(model);
        configureSpinner(isStockPage);
    }

    /**
     * Configures the spinner to be right aligned and to not allow invalid values
     * @param isStockPage - whether the spinner is on the stock page or not
     */
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

    /**
     * Sets the value of the spinner to the current value in the table
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        int currentValue = (int) value;
        spinner.setValue(currentValue);
        return spinner;
    }

    /**
     * Gets the value of the spinner
     */
    @Override
    public Object getCellEditorValue() {
        return spinner.getValue();
    }

    /**
     * Gets the spinner
     * @return the spinner
     */
    public JSpinner getSpinner() {
        return spinner;
    }
}
