package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.service.ProcessService;
import com.lukas783.mdt.ui.AddNewTaskPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Handles the 'Submit' action when creating a new task
 *
 * @author Lucas Carpenter
 */
public class SubmitTaskButtonAction implements ActionListener {

    // Declaration of logger for use in debugging/error handling
    private static final Logger logger = Logger.getLogger(SubmitTaskButtonAction.class.getName());

    private final AddNewTaskPanel addNewTaskPanel;

    /**
     * Constructs a new action to execute for submitting a {@link MavenTask} object.
     * @param addNewTaskPanel An instance of {@link AddNewTaskPanel} to retrieve panel fields from.
     */
    public SubmitTaskButtonAction(AddNewTaskPanel addNewTaskPanel) {
        this.addNewTaskPanel = addNewTaskPanel;
    }

    /**
     * Performs the event for the action. Builds a new {@link MavenTask} object from the previously
     * provided components passed to the constructor. If the resulting object isn't valid, a warning
     * is thrown through the logger.
     * @param e The event given by the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Build the MavenTask object
        MavenTask newMavenTask = new MavenTask.Builder()
                .id(addNewTaskPanel.getTaskId())
                .taskName(addNewTaskPanel.getTaskName())
                .workingDirectory(addNewTaskPanel.getWorkingDirectory())
                .copyToDirectory(addNewTaskPanel.getCopyToDirectory())
                .renameString(addNewTaskPanel.getRenameText())
                .rename(addNewTaskPanel.getRenameCheckbox())
                .copy(addNewTaskPanel.getCopyToDirectoryCheckbox())
                .cleanTarget(addNewTaskPanel.getCleanCheckbox())
                .unpackage(addNewTaskPanel.getUnpackageResult())
                .doInstall(addNewTaskPanel.getInstallRadioButton())
                .build();

        // If the task was able to be built successfully, alert the service, otherwise throw a logger warning.
        if(newMavenTask != null) {
            ProcessService.getInstance().addTask(newMavenTask);
            ((JFrame)SwingUtilities.getRoot(addNewTaskPanel)).dispose();
        } else
            logger.warning("Could not create task from provided fields.");
    }
}
