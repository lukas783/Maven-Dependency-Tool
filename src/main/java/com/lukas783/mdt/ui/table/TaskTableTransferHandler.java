package com.lukas783.mdt.ui.table;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles transferring items to/from the {@link TaskTableModel}.
 *
 * @author Lucas Carpenter
 */
public class TaskTableTransferHandler extends TransferHandler {

    // Declaration of logger for debug/error handling messages.
    private static final Logger logger = Logger.getLogger(TaskTableTransferHandler.class.getName());

    // Declaration of variables used by the class.
    private final DataFlavor localObjectFlavor = new ActivationDataFlavor(
            Integer.class,
            "application/x-java-Integer;class=java.lang.Integer",
            "Integer Row Index");
    private JTable table;

    /**
     * Constructs a new handler object and assigns the table the handler will be attached to.
     * @param table A {@link JTable} object to use for manipulation.
     */
    public TaskTableTransferHandler(JTable table) {
        this.table = table;
    }

    /**
     * Interprets the data into a transferrable object that a handling class can use to import/export data
     * {@inheritDoc}
     */
    @Override
    protected Transferable createTransferable(JComponent c) {
        assert (c == table);
        return new DataHandler(table.getSelectedRow(), localObjectFlavor.getMimeType());
    }

    /**
     * Determines whether this handler class supports importing a type of data to the table.
     * {@inheritDoc}
     */
    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        boolean b = info.getComponent() == table && info.isDrop() && info.isDataFlavorSupported(localObjectFlavor);
        table.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        return b;
    }

    /**
     * Gets the flags for actions available to the table in terms of handling data transfer.
     * {@inheritDoc}
     */
    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    /**
     * Attempts to import data from an object to the table by deriving the selected row as well
     * as the dropped location for the object.
     * {@inheritDoc}
     */
    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        // Retrieve target we are dropping on, we are assuming it's a JTable in this instandce.
        JTable target = (JTable) info.getComponent();

        // Retrieve where we are dropping the data into the table at
        JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();

        // Retrieve the row and the maximum number of rows in the table
        int index = dl.getRow();
        int max = table.getModel().getRowCount();

        // If we fell outside of the bounds of the table, put ourselves back into the bounds
        if(index < 0 || index > max)
            index = max;

        // Re-set the cursor back to its default state.
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        try {
            // Retrieve the row we are transferring data from (retrieved as a type of imported DataFlavor)
            Integer rowFrom = (Integer) info.getTransferable().getTransferData(localObjectFlavor);

            // If the row being dragged to or from isn't an illegal row value, attempt to fire the reorder event
            // after finishing, clear the selections of the table
            if (rowFrom != -1 && rowFrom != index) {
                ((Reorderable)table.getModel()).reorder(rowFrom, index);
                target.getSelectionModel().clearSelection();
                return true;
            }
        } catch(UnsupportedFlavorException ufe) {
            logger.log(Level.SEVERE, "TaskTableTransferHandler local object flavor not supported.", ufe);
        } catch(IOException ioe) {
           logger.log(Level.SEVERE, "TaskTableTransferHandler, unable to read or write to table.", ioe);
        }
        return false;
    }

    /**
     * Handles finishing exporting a data flavor. The purpose is primarily just to reset the cursor back to its
     * default state.
     * {@inheritDoc}
     */
    @Override
    protected void exportDone(JComponent c, Transferable t, int act) {
        if ((act == TransferHandler.MOVE) || (act == TransferHandler.NONE))
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
