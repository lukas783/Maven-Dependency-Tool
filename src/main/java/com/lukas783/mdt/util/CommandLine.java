package com.lukas783.mdt.util;

import com.lukas783.mdt.service.ProcessService;

import java.io.*;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandLine {

    private static final Logger logger = Logger.getLogger(CommandLine.class.getName());
    private static final boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    public static void ExecuteCommandLine(String cmd) {
        try {
            ProcessBuilder builder;
            if(isWindows)
                builder = new ProcessBuilder("cmd.exe", "/c", cmd);
            else
                builder = new ProcessBuilder("/bin/sh", "-c", cmd);

            Map<String, String> env = builder.environment();

            Process p = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
            while((line = reader.readLine()) != null) {
                ProcessService.getInstance().appendExecutionOutput(line);
                ProcessService.getInstance().appendExecutionOutput(System.getProperty("line.separator"));
            }
//            reader.lines().iterator().forEachRemaining(sj::add);
//            String result = sj.toString();
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
