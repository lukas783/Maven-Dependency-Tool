package com.lukas783.mdt.ui;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.service.ProcessService;
import com.lukas783.mdt.ui.actions.AddNewTaskButtonAction;
import com.lukas783.mdt.ui.actions.RemoveTaskButtonAction;
import com.lukas783.mdt.ui.table.*;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * A panel to display the list of tasks as well as components to modify the tasks
 *
 * @author Lucas Carpenter
 */
public class TaskPanel extends JPanel implements IProcessServiceListener {

    // Declaration of logger for debug/error messages
    private static final Logger logger = Logger.getLogger(TaskPanel.class.getName());

    // Declaration of layout and layout constants
    private SpringLayout layout;
    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    // Declaration of components used by the panel
    private JButton addNewTaskButton;
    private JButton removeTaskButton;
    private JScrollPane taskTableScrollPane;
    private JTable taskTable;
    private TaskTableModel taskTableModel;

    /**
     * Instantiate, build, display, and attach listeners to components of the panel.
     */
    public TaskPanel() {
        super();
        // Instantiate and set-up layout
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate table components for the panel
        taskTableModel = new TaskTableModel();
        taskTable = new JTable(taskTableModel);
        taskTable.setDragEnabled(true);
        taskTable.setDropMode(DropMode.INSERT_ROWS);
        taskTable.setTransferHandler(new TaskTableTransferHandler(taskTable));
        taskTable.setDefaultRenderer(InstallPackageClass.class, new InstallPackageRenderer());
        taskTable.setDefaultEditor(InstallPackageClass.class, new InstallPackageEditor());
        taskTableScrollPane = new JScrollPane(taskTable);

        // Instantiate buttons for the panel
        addNewTaskButton = new JButton("Add New Task");
        addNewTaskButton.addActionListener(new AddNewTaskButtonAction());

        removeTaskButton = new JButton("Remove Selected Task");
        removeTaskButton.addActionListener(new RemoveTaskButtonAction(taskTable));

        // Add components to the panel
        add(addNewTaskButton);
        add(removeTaskButton);
        add(taskTableScrollPane);

        // Declare layout constraints for addNewTaskButton.
        layout.putConstraint(NORTH, addNewTaskButton, 5, NORTH, this);
        layout.putConstraint(WEST, addNewTaskButton, 5, WEST, this);

        // Declare layout constraints for removeTaskButton.
        layout.putConstraint(NORTH, removeTaskButton, 0, NORTH, addNewTaskButton);
        layout.putConstraint(WEST, removeTaskButton, 15, EAST, addNewTaskButton);

        // Declare layout constraints for taskTableScrollPane
        layout.putConstraint(NORTH, taskTableScrollPane, 30, SOUTH, addNewTaskButton);
        layout.putConstraint(WEST, taskTableScrollPane, 0, WEST, addNewTaskButton);
        layout.putConstraint(EAST, this, 5, EAST, taskTableScrollPane);
        layout.putConstraint(SOUTH, this, 5, SOUTH, taskTableScrollPane);

        // Attach any listeners needed by the panel.
        ProcessService.getInstance().addListener(this);
    }

    /**
     * Required function from {@link IProcessServiceListener}
     * @param processOutput The most recent output string from a command line execution.
     */
    @Override
    public void processOutputAppend(String processOutput) { }

    /**
     * Required function from {@link IProcessServiceListener}. Fires when a new task is added
     * to {@link ProcessService}.
     * @param task The {@link MavenTask} object to add to the service.
     */
    @Override
    public void taskAdded(MavenTask task) {
        taskTableModel.updateTasks();
    }

    /**
     * Required function from {@link IProcessServiceListener}. Fires when a task is updated
     * in {@link ProcessService}.
     * @param task The {@link MavenTask} object to update in the service.
     */
    @Override
    public void taskUpdated(MavenTask task) {
        taskTableModel.updateTasks();
    }

    /**
     * Required function from {@link IProcessServiceListener}. Fires when a task is removed
     * from {@link ProcessService}.
     * @param task The {@link MavenTask} object to remove from the service.
     */
    @Override
    public void taskRemoved(MavenTask task) {
        taskTableModel.updateTasks();
    }
}
