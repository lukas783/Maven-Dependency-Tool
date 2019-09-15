package com.lukas783.mdt;

import com.lukas783.mdt.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

public class MavenDevelopmentTool {

    private static final Logger logger = Logger.getLogger(MavenDevelopmentTool.class.getName());

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maven Development Tool");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new MainPanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static URL LoadResource(String path) throws FileNotFoundException {
        URL resource = MavenDevelopmentTool.class.getClassLoader().getResource(path);

        if (resource == null)
            throw new FileNotFoundException("Resource file does not exist or is mispelled.");
        else
            return resource;
    }

    public static String ExecuteCommandLine(String command) {
        try {
            Process childProcess = Runtime.getRuntime().exec(command);

            InputStream in = childProcess.getInputStream();

            int c;
            StringBuilder sb = new StringBuilder();

            while((c = in.read()) != -1)
                sb.append((char)c);

            in.close();
            return sb.toString();

        } catch(IOException ioe) {
            logger.severe("IO Exception when running command: "+command);
        }
        return "Error processing command.";
    }
}
