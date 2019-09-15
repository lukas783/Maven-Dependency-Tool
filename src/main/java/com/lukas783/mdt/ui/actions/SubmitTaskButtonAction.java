package com.lukas783.mdt.ui.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the 'Submit' action when creating a new task
 *
 * @author Lucas Carpenter
 */
public class SubmitTaskButtonAction implements ActionListener {
    /**
     * Performs the event for the action. Currently just closes the opened frame
     * until the service can handle submitting new tasks.
     * @param e The event given by the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // place holder till the process service can handle the submit task event
        ((JFrame) SwingUtilities.getRoot((Component)e.getSource())).dispose();
    }
}
