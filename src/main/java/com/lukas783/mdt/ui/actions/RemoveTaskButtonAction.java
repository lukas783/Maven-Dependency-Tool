package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.service.ProcessService;
import com.lukas783.mdt.ui.table.TaskTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the 'Remove Selected Task' button action.
 *
 * @author Lucas Carpenter
 */
public class RemoveTaskButtonAction implements ActionListener {

    // Declaration of variables used by the class.
    private JTable table;

    /**
     * Constructs a new action. Sets the table to get the selected table row from.
     * @param tableToRemoveFrom The table to retrieve the selected row from.
     */
    public RemoveTaskButtonAction(JTable tableToRemoveFrom) {
        this.table = tableToRemoveFrom;
    }

    /**
     * Performs the action for a given event. Removes a {@link com.lukas783.mdt.api.MavenTask}
     * object from {@link ProcessService} if, and only if, the {@link JTable} provided to the
     * action handler contains 1 selected row.
     * @param e The event fired for a given action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(table.getSelectedRowCount() == 1) {
            TableModel model = table.getModel();
            if (model instanceof TaskTableModel) {
                TaskTableModel taskTableModel = (TaskTableModel) model;
                ProcessService.getInstance().removeTask(taskTableModel.getTaskAt(table.getSelectedRow()));
            }
        }
    }
}
