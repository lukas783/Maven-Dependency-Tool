package com.lukas783.mdt;

import com.lukas783.mdt.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * The main entry-point of the application. Creates and sets-up the main frame of the application and passes
 * control of the rest of the application to the main panel for set-up and design.
 *
 * @author Lucas Carpenter
 */
public class MavenDevelopmentTool {

    // Declare a logger for debug/error logging.
    private static final Logger logger = Logger.getLogger(MavenDevelopmentTool.class.getName());

    /**
     * The entry-point of the application. Creates a JFrame and sets configuration for the frame, then
     * adds the main panel to the center of the frame and lets the panel handle application display and
     * control from there.
     * @param args arguments passed to the application through command-line.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maven Development Tool");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new MainPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * A helper function to load any resources from the resources directory, given the right path.
     * @param path The path to the resource file.
     * @return A URL to the resource file.
     * @throws FileNotFoundException If file could not be found, throws a FileNotFoundException.
     */
    public static URL LoadResource(String path) throws FileNotFoundException {
        URL resource = MavenDevelopmentTool.class.getClassLoader().getResource(path);

        if (resource == null)
            throw new FileNotFoundException("Resource file does not exist or is mispelled.");
        else
            return resource;
    }
}
