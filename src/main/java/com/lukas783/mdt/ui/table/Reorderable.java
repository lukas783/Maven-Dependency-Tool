package com.lukas783.mdt.ui.table;

/**
 * An interface to be implemented by any {@link javax.swing.table.AbstractTableModel} that requires a
 * way of re-ordering elements.
 */
public interface Reorderable {
    /**
     * A required method that provides a way to re-order rows of a {@link javax.swing.JTable} by providing
     * an index to move, and an index to move to.
     * @param fromIndex The index to move.
     * @param toIndex The index to move to.
     */
    public void reorder(int fromIndex, int toIndex);
}
