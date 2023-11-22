package trains.of.sheffield;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;
import java.awt.*;

public class GenericSpinnerEditor<T> extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;
    private final int originalValue;

    public GenericSpinnerEditor(int originalValue, SpinnerNumberModel model) {
        this.originalValue = originalValue;

        spinner = new JSpinner(model);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setHorizontalAlignment(JTextField.RIGHT);
        ((DefaultFormatter) editor.getTextField().getFormatter()).setAllowsInvalid(false);

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = (int) spinner.getValue();
                if (currentValue < originalValue) {
                    spinner.setValue(originalValue);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        spinner.setValue(value);
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
