package com.lukas783.mdt.ui.actions;

import com.lukas783.mdt.service.UiService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Handles creating a directory selection component and fires an event to the {@link UiService} to
 * let any class implementing {@link com.lukas783.mdt.api.IUiServiceListener} to update any components
 * that utilize the file selection action. A component tag and title are provided on instnatiation to
 * differentiate to the listeners which component they should update from the results of the directory
 * selector.
 *
 * @author Lucas Carpenter
 */
public class DirectorySelectionAction implements ActionListener {

    // Declaration of components and variables used by the directory chooser
    private String componentTag;
    private String title;
    private JFileChooser chooserWindow;

    /**
     * Instantiates the directory chooser with a component tag for any {@link com.lukas783.mdt.api.IUiServiceListener}
     * objects to differentiate between which file choosers to listen to, and a title to display on the file chooser.
     *
     * @param componentTag A string to differentiate between multiple directory choosers.
     * @param title The title to display on the directory selector.
     */
    public DirectorySelectionAction(String componentTag, String title) {
        super();
        this.componentTag = componentTag;
        this.title = title;
    }

    /**
     * Handles performing the event for the action provided. Spawns a new thread that displays a
     * file selection window restricted to only select directories. On submission, the event
     * tells an instance of {@link UiService} to notify listeners of the submission using the
     * provided component tag.
     * @param e The event derived from the action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            // Instantiate file chooser for the panel
            chooserWindow = new JFileChooser(new File("."));
            chooserWindow.setDialogTitle(title);
            chooserWindow.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooserWindow.setAcceptAllFileFilterUsed(false);
            if(chooserWindow.showOpenDialog((Component)e.getSource()) == JFileChooser.APPROVE_OPTION)
                UiService.getInstance().UpdateDirectory(componentTag, chooserWindow.getSelectedFile());
        }).start();
    }
}
