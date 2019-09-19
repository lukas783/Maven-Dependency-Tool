package com.lukas783.mdt.ui;

import com.lukas783.mdt.api.IUiServiceListener;
import com.lukas783.mdt.service.UiService;
import com.lukas783.mdt.ui.actions.CancelButtonAction;
import com.lukas783.mdt.ui.actions.DirectorySelectionAction;
import com.lukas783.mdt.ui.actions.SubmitTaskButtonAction;

import javax.swing.*;
import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * A panel for creating a new task to process.
 *
 * @author Lucas Carpenter
 */
public class AddNewTaskPanel extends JPanel implements IUiServiceListener {

    // Declaration of logger for debug/error messages
    private static final Logger logger = Logger.getLogger(TaskPanel.class.getName());

    // Declaration of layout and layout constants
    private SpringLayout layout;
    private static final String NORTH = SpringLayout.NORTH;
    private static final String EAST = SpringLayout.EAST;
    private static final String WEST = SpringLayout.WEST;
    private static final String SOUTH = SpringLayout.SOUTH;

    // Declaration of components used by the panel
    private JTextField taskName;
    private JTextField workingDirectory;
    private JTextField renameText;
    private JTextField copyToDirectory;

    private JLabel taskNameLabel;
    private JLabel workingDirectoryLabel;
    private JLabel renameLabel;
    private JLabel copyToDirectoryLabel;

    private JCheckBox cleanCheckbox;
    private JCheckBox renameCheckbox;
    private JCheckBox copyToDirectoryCheckbox;
    private JCheckBox unpackageResult;
    private ButtonGroup goalGroup;
    private JRadioButton installRadioButton;
    private JRadioButton packageRadioButton;

    private JButton changeWorkingDirectoryButton;
    private JButton submitTaskButton;
    private JButton cancelTaskButton;
    private JButton copyToDirectoryButton;

    private UUID taskId;

    /**
     * Instantiate, build, display, and attach listeners to components of the panel.
     */
    public AddNewTaskPanel() {
        super();
        taskId = UUID.randomUUID();

        // Instantiate and set layout for the panel
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate labels for the panel
        taskNameLabel = new JLabel("Task Name");

        workingDirectoryLabel = new JLabel("Working Directory");

        renameLabel = new JLabel("Rename Result");

        copyToDirectoryLabel = new JLabel("Copy-To Directory");

        // Instantiate text input fields for panel
        taskName = new JTextField();

        workingDirectory = new JTextField();
        workingDirectory.setEditable(false);

        renameText = new JTextField();
        renameText.setEnabled(false);

        copyToDirectory = new JTextField();
        copyToDirectory.setEditable(false);

        // Instantiate checkboxes for panel
        cleanCheckbox = new JCheckBox("Clean Maven Target Directory", false);

        renameCheckbox = new JCheckBox("", false);
        renameCheckbox.addActionListener(e -> renameText.setEnabled(((JCheckBox) e.getSource()).isSelected()));

        copyToDirectoryCheckbox = new JCheckBox("", false);
        copyToDirectoryCheckbox.addActionListener(e -> copyToDirectoryButton.setEnabled(((JCheckBox) e.getSource()).isSelected()));

        unpackageResult = new JCheckBox("Untar/Unzip Result", false);

        // Instantiate radio button and button groups for panel
        installRadioButton = new JRadioButton("Install Artifact", false);
        packageRadioButton = new JRadioButton("Package Artifact", true);

        goalGroup = new ButtonGroup();
        goalGroup.add(installRadioButton);
        goalGroup.add(packageRadioButton);

        // Instantiate buttons for the panel
        submitTaskButton = new JButton("Submit");
        submitTaskButton.addActionListener(new SubmitTaskButtonAction(this));

        cancelTaskButton = new JButton("Cancel");
        cancelTaskButton.addActionListener(new CancelButtonAction());

        changeWorkingDirectoryButton = new JButton("...");
        changeWorkingDirectoryButton.addActionListener(
                new DirectorySelectionAction("working_directory", "Select Working Directory..."));

        copyToDirectoryButton = new JButton("...");
        copyToDirectoryButton.setEnabled(false);
        copyToDirectoryButton.addActionListener(
                new DirectorySelectionAction("copy_to_directory", "Select Copy-To Directory..."));

        // Add components to the panel
        add(taskNameLabel);
        add(taskName);
        add(workingDirectoryLabel);
        add(workingDirectory);
        add(changeWorkingDirectoryButton);
        add(renameCheckbox);
        add(renameLabel);
        add(renameText);
        add(copyToDirectoryLabel);
        add(copyToDirectory);
        add(copyToDirectoryButton);
        add(copyToDirectoryCheckbox);
        add(cleanCheckbox);
        add(unpackageResult);
        add(installRadioButton);
        add(packageRadioButton);
        add(submitTaskButton);
        add(cancelTaskButton);

        // Set constraints for taskNameLabel
        layout.putConstraint(NORTH, taskNameLabel, 15, NORTH, this);
        layout.putConstraint(WEST, taskNameLabel, 15, WEST, this);

        // Set constraints for taskName
        layout.putConstraint(NORTH, taskName, 5, SOUTH, taskNameLabel);
        layout.putConstraint(WEST, taskName, 0, WEST, taskNameLabel);
        layout.putConstraint(EAST, taskName, 400, WEST, taskName);

        // Set constraints for workingDirectoryLabel
        layout.putConstraint(NORTH, workingDirectoryLabel, 15, SOUTH, taskName);
        layout.putConstraint(WEST, workingDirectoryLabel, 0, WEST, taskName);

        // Set constraints for workingDirectory
        layout.putConstraint(NORTH, workingDirectory, 5, SOUTH, workingDirectoryLabel);
        layout.putConstraint(WEST, workingDirectory, 0, WEST, workingDirectoryLabel);
        layout.putConstraint(EAST, workingDirectory, 370, WEST, workingDirectory);

        // Set constraints for changeWorkingDirectoryButton
        layout.putConstraint(NORTH, changeWorkingDirectoryButton, 0, NORTH, workingDirectory);
        layout.putConstraint(WEST, changeWorkingDirectoryButton, 5, EAST, workingDirectory);
        layout.putConstraint(SOUTH, changeWorkingDirectoryButton, 0, SOUTH, workingDirectory);
        layout.putConstraint(EAST, changeWorkingDirectoryButton, 25, WEST, changeWorkingDirectoryButton);

        // Set constraints for renameLabel
        layout.putConstraint(NORTH, renameLabel, 15, SOUTH, workingDirectory);
        layout.putConstraint(WEST, renameLabel, 0, WEST, workingDirectory);

        // Set constraints for renameCheckbox
        layout.putConstraint(NORTH, renameCheckbox, 10, SOUTH, renameLabel);
        layout.putConstraint(WEST, renameCheckbox, 0, WEST, renameLabel);

        // Set constraints for renameText
        layout.putConstraint(NORTH, renameText, 0, NORTH, renameCheckbox);
        layout.putConstraint(WEST, renameText, 5, EAST, renameCheckbox);
        layout.putConstraint(SOUTH, renameText, 0, SOUTH, renameCheckbox);
        layout.putConstraint(EAST, renameText, 380, WEST, renameText);

        // Set constraints for copyToDirectoryLabbel
        layout.putConstraint(NORTH, copyToDirectoryLabel, 15, SOUTH, renameCheckbox);
        layout.putConstraint(WEST, copyToDirectoryLabel, 0, WEST, renameCheckbox);

        // Set cosntraints for copyToDirectoryCheckbox
        layout.putConstraint(NORTH, copyToDirectoryCheckbox, 10, SOUTH, copyToDirectoryLabel);
        layout.putConstraint(WEST, copyToDirectoryCheckbox, 0, WEST, copyToDirectoryLabel);

        // Set constraints for copyToDirectory
        layout.putConstraint(NORTH, copyToDirectory, 0, NORTH, copyToDirectoryCheckbox);
        layout.putConstraint(WEST, copyToDirectory, 5, EAST, copyToDirectoryCheckbox);
        layout.putConstraint(SOUTH, copyToDirectory, 0, SOUTH, copyToDirectoryCheckbox);
        layout.putConstraint(EAST, copyToDirectory, 350, WEST, copyToDirectory);

        // Set constraints for copyToDirectoryButton
        layout.putConstraint(NORTH, copyToDirectoryButton, 0, NORTH, copyToDirectory);
        layout.putConstraint(WEST, copyToDirectoryButton, 5, EAST, copyToDirectory);
        layout.putConstraint(SOUTH, copyToDirectoryButton, 0, SOUTH, copyToDirectory);
        layout.putConstraint(EAST, copyToDirectoryButton, 25, WEST, copyToDirectoryButton);

        // Set constraints for cleanCheckbox
        layout.putConstraint(NORTH, cleanCheckbox, 15, SOUTH, copyToDirectoryCheckbox);
        layout.putConstraint(WEST, cleanCheckbox, 0, WEST, copyToDirectoryCheckbox);

        // Set constraints for unpackageResult
        layout.putConstraint(NORTH, unpackageResult, 0, NORTH, cleanCheckbox);
        layout.putConstraint(WEST, unpackageResult, 30, EAST, cleanCheckbox);

        // Set constraints for packageRadioButton
        layout.putConstraint(NORTH, packageRadioButton, 10, SOUTH, cleanCheckbox);
        layout.putConstraint(WEST, packageRadioButton, 0, WEST, cleanCheckbox);

        // Set constraints for installRadioButon
        layout.putConstraint(NORTH, installRadioButton, 0, NORTH, packageRadioButton);
        layout.putConstraint(WEST, installRadioButton, 10, EAST, packageRadioButton);

        // Set constraints for cancelTaskButton
        layout.putConstraint(NORTH, cancelTaskButton, 30, SOUTH, packageRadioButton);
        layout.putConstraint(EAST, cancelTaskButton, 0, EAST, taskName);
        layout.putConstraint(WEST, cancelTaskButton, -80, EAST, cancelTaskButton);

        // Set constraints for submitTaskButton
        layout.putConstraint(NORTH, submitTaskButton, 0, NORTH, cancelTaskButton);
        layout.putConstraint(WEST, submitTaskButton, -80, WEST, cancelTaskButton);

        // Set constraints for panel size
        layout.putConstraint(EAST, this, 15, EAST, cancelTaskButton);
        layout.putConstraint(SOUTH, this, 15, SOUTH, cancelTaskButton);

        // Add the panel as a UiService listener
        UiService.getInstance().addListener(this);
    }

    /**
     * Method required by {@link IUiServiceListener}. Listens to the {@link UiService} for a file chooser to
     * send an updated directory for a UI specific componentTag.
     * @param componentTag The tag that an implementation of {@link IUiServiceListener} can use to update a component
     * @param directory The changed directory, as a {@link File}
     */
    @Override
    public void directoryUpdated(String componentTag, File directory) {
        switch(componentTag) {
            case "working_directory":
                workingDirectory.setText(directory.toString());
                break;

            case "copy_to_directory":
                copyToDirectory.setText(directory.toString());
                break;
        }
    }

    /**
     * Retrieves panel's field value for task name.
     * @return String with panel's field value for task name.
     */
    public String getTaskName() {
        return taskName.getText();
    }

    /**
     * Retrieves panel's field value for working directory.
     * @return String with panel's field value for working directory.
     */
    public String getWorkingDirectory() {
        return workingDirectory.getText();
    }

    /**
     * Retrieves panel's field value for renaming file.
     * @return String with panel's field value for renaming file.
     */
    public String getRenameText() {
        return renameText.getText();
    }

    /**
     * Retrieves panel's field value for copy-to directory.
     * @return String with panel's field value for copy-to directory.
     */
    public String getCopyToDirectory() {
        return copyToDirectory.getText();
    }

    /**
     * Retrieves panel's field value for whether to pre-pend 'clean' to task.
     * @return True if 'clean' should be pre-pended to task command, False otherwise.
     */
    public boolean getCleanCheckbox() {
        return cleanCheckbox.isSelected();
    }

    /**
     * Retrieves panel's field value for whether to rename file.
     * @return True if file should be renamed, False otherwise.
     */
    public boolean getRenameCheckbox() {
        return renameCheckbox.isSelected();
    }

    /**
     * Retrieves panel's field value for whether copy output to new directory.
     * @return True if result should be copied to a different directory, False otherwise.
     */
    public boolean getCopyToDirectoryCheckbox() {
        return copyToDirectoryCheckbox.isSelected();
    }

    /**
     * Retrieves panel's field value for whether to untar/unzip result.
     * @return True if result should be untarred/unzipped, False otherwise.
     */
    public boolean getUnpackageResult() {
        return unpackageResult.isSelected();
    }

    /**
     * Retrieves panel's field value for whether to run the install goal.
     * @return True if the maven goal should be to install, False otherwise.
     */
    public boolean getInstallRadioButton() {
        return installRadioButton.isSelected();
    }

    /**
     * Retrieves the panel's internal UUID, this becomes the task's internal UUID
     * @return The UUID of the panel.
     */
    public UUID getTaskId() {
        return taskId;
    }
}
