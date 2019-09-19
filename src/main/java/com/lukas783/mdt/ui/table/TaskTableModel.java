package com.lukas783.mdt.ui.table;

import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.service.ProcessService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * The table model used to display and modify a {@link MavenTask} object.
 *
 * @author Lucas Carpenter
 */
public class TaskTableModel extends AbstractTableModel {

    // Declaration of internal variables used by the table model.
    private ArrayList<MavenTask> taskList;

    /**
     * Constructs a new table model. Builds the internal list of values in the table.
     */
    public TaskTableModel() {
        taskList = new ArrayList<>(ProcessService.getInstance().getTasks().values());
    }

    /**
     * Retrieves the number of rows in the table.
     * {@inheritDoc}
     */
    @Override
    public int getRowCount() {
        return taskList.size();
    }

    /**
     * Retrieves the number of columns in the table.
     * {@inheritDoc}
     */
    @Override
    public int getColumnCount() {
        return 10;
    }

    /**
     * Retrieves the value of the {@link MavenTask} at a particular row and column in the table.
     * {@inheritDoc}
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MavenTask task = taskList.get(rowIndex);
        switch(columnIndex) {
            case 1: return task.getEnabled();
            case 2: return task.getTaskName();
            case 3: return task.getWorkingDirectory();
            case 4: return task.getCopyToDirectory();
            case 5: return task.getRenameString();
            case 6: return task.doCopy();
            case 7: return task.doRename();
            case 8: return task.doUnpackage();
            case 9: return task.doInstall();
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
            case 8: return "Untar/Unzip Result?";
            case 9: return "Install/Package";
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
            case 8: return Boolean.class;

            case 2:
            case 3:
            case 4:
            case 5: return String.class;

            case 9: return InstallPackageClass.class;
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
        MavenTask.Builder builder  = new MavenTask.Builder(taskList.get(row));
        switch(col) {
            case 1: builder.enabled((Boolean)value); break;
            case 2: builder.taskName((String)value); break;
            case 3: builder.workingDirectory((String)value); break;
            case 4: builder.copyToDirectory(((String)value)); break;
            case 5: builder.renameString((String)value); break;
            case 6: builder.copy((Boolean)value); break;
            case 7: builder.rename((Boolean)value); break;
            case 8: builder.unpackage((Boolean) value); break;
            case 9: builder.doInstall((Boolean)value); break;

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
     */
    public void addTask(MavenTask task) {
        taskList.add(task);
        int row = taskList.indexOf(task);
        fireTableRowsInserted(row, row);
    }

    /**
     * Updates the tasks in the table to reflect the list in {@link ProcessService#getTasks()}.
     */
    public void updateTasks() {
        taskList = new ArrayList<>(ProcessService.getInstance().getTasks().values());
        fireTableDataChanged();
    }

    /**
     * Removes an existing {@link MavenTask} object from the table.
     * @param task The {@link MavenTask} to remove from the table.
     */
    public void removeTask(MavenTask task) {
        if (taskList.contains(task)) {
            int row = taskList.indexOf(task);
            taskList.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

    /**
     * Retrieves a particular {@link MavenTask} object at a certain index in the table.
     * @param index The index of the table model
     * @return A {@link MavenTask} object.
     */
    public MavenTask getTaskAt(int index) {
        return taskList.get(index);
    }

}
