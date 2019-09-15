package com.lukas783.mdt.ui;

import com.lukas783.mdt.api.IUiServiceListener;
import com.lukas783.mdt.service.UiService;
import com.lukas783.mdt.ui.actions.CancelButtonAction;
import com.lukas783.mdt.ui.actions.DirectorySelectionAction;
import com.lukas783.mdt.ui.actions.SubmitTaskButtonAction;

import javax.swing.*;
import java.io.File;
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
    private JTextArea taskName;
    private JLabel taskNameLabel;
    private JCheckBox cleanCheckbox;
    private ButtonGroup goalGroup;
    private JRadioButton installRadioButton;
    private JRadioButton packageRadioButton;
    private JTextArea workingDirectory;
    private JLabel workingDirectoryLabel;
    private JButton changeWorkingDirectoryButton;
//    private JFileChooser workingDirectory;

    private JButton submitTaskButton;
    private JButton cancelTaskButton;

    /**
     * Instantiate, build, display, and attach listeners to components of the panel.
     */
    public AddNewTaskPanel() {
        super();
        // Instantiate and set layout for the panel
        layout = new SpringLayout();
        setLayout(layout);

        // Instantiate labels for the panel
        taskNameLabel = new JLabel("Task Name");
        workingDirectoryLabel = new JLabel("Working Directory");

        // Instantiate text input fields for panel
        taskName = new JTextArea();
        workingDirectory = new JTextArea();
        workingDirectory.setEditable(false);

        // Instantiate checkboxes for panel
        cleanCheckbox = new JCheckBox("Clean Maven Target Directory", false);

        // Instantiate radio button and button groups for panel
        goalGroup = new ButtonGroup();
        installRadioButton = new JRadioButton("Install Artifact", false);
        packageRadioButton = new JRadioButton("Package Artifact", true);
        goalGroup.add(installRadioButton);
        goalGroup.add(packageRadioButton);

        // Instantiate buttons for the panel
        submitTaskButton = new JButton("Submit");
        submitTaskButton.addActionListener(new SubmitTaskButtonAction());

        cancelTaskButton = new JButton("Cancel");
        cancelTaskButton.addActionListener(new CancelButtonAction());

        changeWorkingDirectoryButton = new JButton("...");
        changeWorkingDirectoryButton.addActionListener(
                new DirectorySelectionAction("working_directory", "Select Working Directory..."));

        // Add components to the panel
        add(taskNameLabel);
        add(taskName);
        add(workingDirectoryLabel);
        add(workingDirectory);
        add(changeWorkingDirectoryButton);
        add(cleanCheckbox);
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
        layout.putConstraint(SOUTH, taskName, 15, NORTH, taskName);
        layout.putConstraint(EAST, taskName, 300, WEST, taskName);

        // Set constraints for workingDirectoryLabel
        layout.putConstraint(NORTH, workingDirectoryLabel, 10, SOUTH, taskName);
        layout.putConstraint(WEST, workingDirectoryLabel, 0, WEST, taskName);

        // Set constraints for workingDirectory
        layout.putConstraint(NORTH, workingDirectory, 5, SOUTH, workingDirectoryLabel);
        layout.putConstraint(WEST, workingDirectory, 0, WEST, workingDirectoryLabel);
        layout.putConstraint(SOUTH, workingDirectory, 15, NORTH, workingDirectory);
        layout.putConstraint(EAST, workingDirectory, 270, WEST, workingDirectory);

        // Set constraints for changeWorkingDirectoryButton
        layout.putConstraint(NORTH, changeWorkingDirectoryButton, 0, NORTH, workingDirectory);
        layout.putConstraint(WEST, changeWorkingDirectoryButton, 5, EAST, workingDirectory);
        layout.putConstraint(SOUTH, changeWorkingDirectoryButton, 0, SOUTH, workingDirectory);
        layout.putConstraint(EAST, changeWorkingDirectoryButton, 25, WEST, changeWorkingDirectoryButton);

        // Set constraints for cleanCheckbox
        layout.putConstraint(NORTH, cleanCheckbox, 10, SOUTH, workingDirectory);
        layout.putConstraint(WEST, cleanCheckbox, 0, WEST, workingDirectory);

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

    @Override
    public void directoryUpdated(String componentTag, File directory) {
        if(componentTag.equalsIgnoreCase("working_directory")) {
            workingDirectory.setText(directory.toString());
        }
    }
}
