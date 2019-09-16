package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.service.ProcessService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Handles the 'Submit' action when creating a new task
 *
 * @author Lucas Carpenter
 */
public class SubmitTaskButtonAction implements ActionListener {

    // Declaration of logger for use in debugging/error handling
    private static final Logger logger = Logger.getLogger(SubmitTaskButtonAction.class.getName());

    // Declaration of variables used by the action
    private UUID taskId;
    private JTextField taskName;
    private JTextField workingDirectory;
    private JTextField copyToDirectory;
    private JTextField renameResult;
    private JCheckBox rename;
    private JCheckBox copy;
    private JCheckBox clean;
    private JCheckBox unpackage;
    private JRadioButton doInstall;

    /**
     * Constructs a new action to execute for submitting a {@link MavenTask} object.
     * FIXME: This is probably not the best way to handle submission, but it works for now.
     *  Look into re-working this to have a more elegant solution in the future.
     * @param taskId The UUID of the task.
     * @param taskName The name of the task.
     * @param workingDirectory The working directory of the task.
     * @param copyToDirectory The directory to copy result to.
     * @param renameResult The name to rename the result to.
     * @param rename Whether to rename the result.
     * @param copy Whether to copy the result to a new location.
     * @param clean Whether to pre-pend the 'clean' argument to the maven task.
     * @param unpackage Whether to untar/unzip the result.
     * @param doInstall Whether to run the install or package goal.
     */
    public SubmitTaskButtonAction(UUID taskId, JTextField taskName, JTextField workingDirectory,
                                  JTextField copyToDirectory, JTextField renameResult, JCheckBox rename,
                                  JCheckBox copy, JCheckBox clean, JCheckBox unpackage, JRadioButton doInstall) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.workingDirectory = workingDirectory;
        this.copyToDirectory = copyToDirectory;
        this.renameResult = renameResult;
        this.rename = rename;
        this.copy = copy;
        this.clean = clean;
        this.unpackage = unpackage;
        this.doInstall = doInstall;
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
                .id(this.taskId)
                .taskName(this.taskName.getText())
                .workingDirectory(this.workingDirectory.getText())
                .copyToDirectory(this.copyToDirectory.getText())
                .renameString(this.renameResult.getText())
                .rename(this.rename.isSelected())
                .copy(this.copy.isSelected())
                .cleanTarget(this.clean.isSelected())
                .unpackage(this.unpackage.isSelected())
                .doInstall(this.doInstall.isSelected())
                .build();

        // If the task was able to be built successfully, alert the service, otherwise throw a logger warning.
        if(newMavenTask != null) {
            ProcessService.getInstance().addTask(newMavenTask);
            ((JFrame)SwingUtilities.getRoot((Component)e.getSource())).dispose();
        } else
            logger.warning("Could not create task from provided fields.");
    }
}
