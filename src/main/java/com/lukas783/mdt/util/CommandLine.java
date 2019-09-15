package com.lukas783.mdt.util;

import com.lukas783.mdt.service.ProcessService;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility file filled with helper functions to utilize the system's native terminal.
 *
 * @author Lucas Carpenter
 */
public class CommandLine {

    // Declare a logger for stdout logging
    private static final Logger logger = Logger.getLogger(CommandLine.class.getName());

    // Declaration of constants used by the utility
    private static final boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    /**
     * Executes a command line statement. Builds a process and executes the process, redirecting
     * output of the process to a BufferedReader and serving the {@link ProcessService} with the
     * output of the process.
     * @param cmd The command to execute
     */
    public static void ExecuteCommandLine(String cmd) {
        try {
            // Build the process as either a windows or linux process
            ProcessBuilder builder;
            if(isWindows)
                builder = new ProcessBuilder("cmd.exe", "/c", cmd);
            else
                builder = new ProcessBuilder("/bin/sh", "-c", cmd);

            // Gather environment variables (Not used atm, may need to use it later, who knows)
            Map<String, String> env = builder.environment();

            // Start the process
            Process p = builder.start();

            // Redirect output of the process to a buffered reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Read process output line-by-line, adding a system line separator between each line.
            String line;
            while((line = reader.readLine()) != null) {
                ProcessService.getInstance().appendExecutionOutput(line);
                ProcessService.getInstance().appendExecutionOutput(System.getProperty("line.separator"));
            }

            // Wait for the process to finish, then destroy the process and leave the function
            p.waitFor();
            p.destroy();
        } catch(IOException ioe) {
            logger.log(Level.SEVERE, "Unable to redirect i/o for command: " + cmd, ioe);
        } catch(InterruptedException ie) {
            logger.log(Level.SEVERE, "Child process was interrupted when running command: " + cmd, ie);
        } catch(Exception e) {
            logger.log(Level.SEVERE, "Unhandled exception...", e);
        }
    }
}
