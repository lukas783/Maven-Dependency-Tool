package com.lukas783.mdt.ui.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles the 'Cancel' button action.
 *
 * @author Lucas Carpenter
 */
public class CancelButtonAction implements ActionListener {
    /**
     * Performs the event for the action. Closes the root frame without submitting
     * the task to the service.
     * @param e The event given by the action performed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ((JFrame)SwingUtilities.getRoot((Component)e.getSource())).dispose();
    }
}
