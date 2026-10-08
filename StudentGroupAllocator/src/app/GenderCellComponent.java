package app;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.FlowLayout;

public class GenderCellComponent extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private final JPanel panel;
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;

    public GenderCellComponent() {
        maleButton = new JRadioButton("M");
        femaleButton = new JRadioButton("F");
        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);

        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        panel.add(maleButton);
        panel.add(femaleButton);

        maleButton.addActionListener(e -> stopCellEditing());
        femaleButton.addActionListener(e -> stopCellEditing());
    }

    private void applyValue(Object value) {
        Gender gender = (value instanceof Gender) ? (Gender) value : Gender.MALE;
        if (gender == Gender.MALE) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }
    }

    @Override
    public Object getCellEditorValue() {
        return maleButton.isSelected() ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        applyValue(value);
        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        applyValue(value);
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }
}
