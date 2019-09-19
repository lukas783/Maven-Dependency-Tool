package com.lukas783.mdt.ui.table;

import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.service.ProcessService;

import javax.swing.table.AbstractTableModel;
import java.util.logging.Logger;

/**
 * The table model used to display and modify a {@link MavenTask} object.
 *
 * @author Lucas Carpenter
 */
public class TaskTableModel extends AbstractTableModel implements Reorderable {

    // Declaration of logger for debug/error handling messages.
    private static final Logger logger = Logger.getLogger(TaskTableModel.class.getName());

    /**
     * Constructs a new table model. Builds the internal list of values in the table.
     */
    public TaskTableModel() { }

    /**
     * Retrieves the number of rows in the table.
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return ProcessService.getInstance().getTasks().size();
    }

    /**
     * Retrieves the number of columns in the table.
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return 11;
    }

    /**
     * Retrieves the value of the {@link MavenTask} at a particular row and column in the table.
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MavenTask task = ProcessService.getInstance().getTasks().get(rowIndex);
        switch(columnIndex) {
            case 1: return task.getEnabled();
            case 2: return task.getTaskName();
            case 3: return task.getWorkingDirectory();
            case 4: return task.getCopyToDirectory();
            case 5: return task.getRenameString();
            case 6: return task.doCopy();
            case 7: return task.doRename();
            case 8: return task.cleanTarget();
            case 9: return task.doUnpackage();
            case 10: return task.doInstall();
            default: return "";
        }
    }

    /**
     * Retrieves the column header for a particular column in the table.
     * {@inheritDoc}
     */
    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 1: return "Enabled?";
            case 2: return "Task Name";
            case 3: return "Working Directory";
            case 4: return "Copy-To Directory";
            case 5: return "Rename String";
            case 6: return "Copy Result?";
            case 7: return "Rename Result?";
            case 8: return "Clean Target Directory?";
            case 9: return "Untar/Unzip Result?";
            case 10: return "Install/Package";
            default: return "";
        }
    }

    /**
     * Retrieves the type of object being used in the table.
     * {@inheritDoc}
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 1:
            case 6:
            case 7:
            case 8:
            case 9: return Boolean.class;

            case 2:
            case 3:
            case 4:
            case 5: return String.class;

            case 10: return InstallPackageClass.class;
            default: return Object.class;
        }
    }

    /**
     * Retrieves whether a particular cell in a given row or column is edittable.
     * {@inheritDoc}
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    /**
     * Sets values in the table to their changed values when a cell is done being edited.
     * {@inheritDoc}
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        MavenTask.Builder builder  = new MavenTask.Builder(
                ProcessService.getInstance().getTasks().get(row));
        switch(col) {
            case 1: builder.enabled((Boolean)value); break;
            case 2: builder.taskName((String)value); break;
            case 3: builder.workingDirectory((String)value); break;
            case 4: builder.copyToDirectory(((String)value)); break;
            case 5: builder.renameString((String)value); break;
            case 6: builder.copy((Boolean)value); break;
            case 7: builder.rename((Boolean)value); break;
            case 8: builder.cleanTarget((Boolean)value); break;
            case 9: builder.unpackage((Boolean) value); break;
            case 10: builder.doInstall((Boolean)value); break;

            default:// ignore
                return;
        }
        MavenTask updatedTask = builder.build();
        if(updatedTask != null) {
            ProcessService.getInstance().updateTask(builder.build());
            updateTasks();
        }
    }

    /**
     * Adds a new {@link MavenTask} object to the table model.
     * @param task The {@link MavenTask} to insert into the table.
     * @deprecated Due to the list being derived from the {@link ProcessService} instead of held locally.
     */
    public void addTask(MavenTask task) {
    }

    /**
     * Updates the tasks in the table to reflect the list in {@link ProcessService#getTasks()}.
     */
    public void updateTasks() {
        fireTableDataChanged();
    }

    /**
     * Removes an existing {@link MavenTask} object from the table.
     * @param task The {@link MavenTask} to remove from the table.
     * @deprecated Due to the list being derived from the {@link ProcessService} instead of held locally.
     */
    public void removeTask(MavenTask task) {
    }

    /**
     * Retrieves a particular {@link MavenTask} object at a certain index in the table.
     * @param index The index of the table model
     * @return A {@link MavenTask} object.
     */
    public MavenTask getTaskAt(int index) {
        return ProcessService.getInstance().getTasks().get(index);
    }

    /**
     * Implementation of {@link Reorderable#reorder(int, int)} to supply a method of alerting the model
     * of a request to re-order table elements provided by {@link TaskTableTransferHandler}
     * @param fromIndex The index of the initial drag event
     * @param toIndex The index of the release event
     */
    @Override
    public void reorder(int fromIndex, int toIndex) {
        if(toIndex - 1 == fromIndex)
            return;

        ProcessService.getInstance().reorderTaskPosition(fromIndex, toIndex);
        updateTasks();
        fireTableDataChanged();
    }
}
