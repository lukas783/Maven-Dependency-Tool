package com.lukas783.mdt.ui.table;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Renders the JTable cell for the install/package option. Shows a center-aligned label stating whether
 * the current value of the {@link com.lukas783.mdt.api.MavenTask} is to install or package the artifact.
 *
 * @author Lucas Carpenter
 */
public class InstallPackageRenderer extends JLabel implements TableCellRenderer {

    /**
     * Retrieves the component to render when the cell is not selected.
     * {@inheritDoc}
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText(((Boolean) value) ? "Install" : "Package");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        return this;
    }
}
