package com.lukas783.mdt.ui.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles edit events for the Install/Package column on the {@link TaskTableModel}
 *
 * @author Lucas Carpenter
 */
public class InstallPackageEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    // Declaration of variables used by the cell editor
    private boolean value;
    private JButton button;

    /**
     * Class constructor. Sets up the internal button that the user is clicking and styles it like a {@link JLabel}.
     */
    public InstallPackageEditor() {
        button = new JButton();
        button.setBorderPainted(false);
        button.addActionListener(this);
    }

    /**
     * Performs the button pressed event. Toggles the internal boolean value, {@link #value}.
     * @param e The event fired when the button is pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.value = !this.value;
        fireEditingStopped();
    }

    /**
     * Retrieves the component for editing a table cell.
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value = (Boolean)value;
        this.button.setText(this.value ? "Install" : "Package");
        return button;
    }

    /**
     * Returns the value to send to the {@link javax.swing.table.AbstractTableModel#setValueAt(Object, int, int)} event.
     * {@inheritDoc}
     */
    @Override
    public Object getCellEditorValue() {
        return this.value;
    }
}
