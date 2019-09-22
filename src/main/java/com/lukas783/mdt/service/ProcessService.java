package com.lukas783.mdt.service;

import com.lukas783.mdt.api.IProcessServiceListener;
import com.lukas783.mdt.api.MavenTask;
import com.lukas783.mdt.api.PomInfo;
import com.lukas783.mdt.util.CommandLine;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.logging.Logger;

import org.apache.commons.io.filefilter.RegexFileFilter;

/**
 * A singleton-service class that can be called statically by any class that needs to use it.
 * Keeps a list of listeners that implement {@link IProcessServiceListener} to fire events
 * as the service sees fit. The service is responsible for handling processing of tasks to-do.
 */
public class ProcessService {

    // Declaration of logger for debug/error handling messages.
    private static final Logger logger = Logger.getLogger(ProcessService.class.getName());

    // Declaration of the internal instance to always use
    private static ProcessService INSTANCE;

    // Declaration of listeners the service will need to fire events to
    private ArrayList<IProcessServiceListener> listeners;

    private ArrayList<MavenTask> taskList;

    /**
     * The public facing method to get an instance of the class.
     * @return An instance of {@link ProcessService}
     */
    public static ProcessService getInstance() {
        if(INSTANCE == null)
            INSTANCE = new ProcessService();
        return INSTANCE;
    }

    /**
     * The private facing constructor used by the public-facing {@link #getInstance()} method
     * to create a new service instance reference if one does not already exist.
     */
    private ProcessService() {
        listeners = new ArrayList<>();
        taskList = new ArrayList<>();
    }

    /**
     * Executes the current tasks by using the {@link CommandLine#ExecuteCommandLine(File, String)} method
     */
    public void executeProcessTasks() {
        for(MavenTask task : taskList) {
            if (task.getEnabled()) {
                appendExecutionOutput(
                        "Processing Task: " +
                                task.getTaskName() +
                                System.getProperty("line.separator"));

                StringBuilder commandString = new StringBuilder();

                // Build the command to traverse to the proper working directory.
                File workingDirectory = new File(task.getWorkingDirectory());

                // Check that the directory to do work in is a real directory.
                if (!workingDirectory.isDirectory()) {
                    logger.warning("Provided MavenTask: " + task.getTaskName() + " has bad working directory.");
                    continue;
                }

                // Validate that the working directory contains a POM file to execute a maven task
                File pomFile = new File(task.getWorkingDirectory() + "/pom.xml");
                if(!pomFile.exists()) {
                    appendExecutionOutput(
                            "POM file for task: " +
                                    task.getTaskName() +
                                    " does not exist, skipping task." +
                                    System.getProperty("line.separator"));
                    continue;
                }

                // Build the XML POM file into an object to make identifying POM file attributes easier
                PomInfo pomInfo = new PomInfo(pomFile);

                // Use the PomInfo object to retrieve what the built artifact name will be.
                String artifactId = pomInfo.getNodeValue("project.artifactId");
                String version = pomInfo.getNodeValue("project.version");

                // Validate the artifactId exists
                if(artifactId == null || artifactId.length() == 0) {
                    appendExecutionOutput(
                            "POM file found no 'project.artifactId' tag, skipping task: " +
                                    task.getTaskName() +
                                    "." +
                                    System.getProperty("line.separator"));
                    continue;
                }

                // Validate the version exists
                if(version == null || version.length() == 0) {
                    version = pomInfo.getNodeValue("project.parent.version");
                    if(version == null || version.length() == 0) {
                        appendExecutionOutput(
                                "POM file found no 'project.version' or 'project.parent.version' tag, skipping task: " +
                                        task.getTaskName() +
                                        "." +
                                        System.getProperty("line.separator"));
                        continue;
                    }
                }

                // Build and execute the maven command
                commandString.append("mvn ");

                if (task.cleanTarget())
                    commandString.append("clean ");

                if (task.doInstall())
                    commandString.append("install ");
                else
                    commandString.append("package");

                // Execute the maven build command and clear the current command string
                CommandLine.ExecuteCommandLine(workingDirectory, commandString.toString());
                commandString = new StringBuilder();

                if(task.doRename() || task.doCopy() || task.doUnpackage()) {
                    // Build a filter to find the built artifact
                    String pattern = artifactId + "-" + version + "\\..*";
                    FileFilter filter = new RegexFileFilter(pattern);
                    File[] files = new File(workingDirectory + "/target/").listFiles(filter);
                    String builtTargetPath;

                    // If the filter can find a file, note down its absolute path, otherwise error out
                    if (files != null && files.length > 0) {
                        builtTargetPath = files[0].getAbsolutePath();
                    } else {
                        appendExecutionOutput(
                                "Could not find a built target directory given the POM configuration for task: " +
                                        task.getTaskName() +
                                        ". Stopping task execution." +
                                        System.getProperty("line.separator"));
                        continue;
                    }

                    // Build the rename command if the task needs to be renamed.
                    if (task.doRename()) {
                        // Build the new command string.
                        commandString.append("ren ");
                        commandString.append(builtTargetPath);
                        commandString.append(" ");
                        commandString.append(task.getRenameString());

                        // Execute the command and reset the command string
                        CommandLine.ExecuteCommandLine(workingDirectory, commandString.toString());
                        commandString = new StringBuilder();

                        // Find the new file, if it exists, and update the field values for use elsewhere
                        pattern = task.getRenameString();
                        filter = new RegexFileFilter(pattern);
                        files = new File(workingDirectory + "/target/").listFiles(filter);

                        if (files != null && files.length > 0) {
                            builtTargetPath = files[0].getAbsolutePath();
                        } else {
                            appendExecutionOutput(
                                    "Unable to find renamed file after renaming for task: " +
                                            task.getTaskName() +
                                            ". Stopping task execution." +
                                            System.getProperty("line.separator"));
                            continue;
                        }
                    }

                    // Build the copy command if the task needs to do a copy
                    if (task.doCopy()) {
                        // Build the new command string
                        commandString.append("copy /Y ");
                        commandString.append(builtTargetPath);
                        commandString.append(" ");
                        commandString.append(task.getCopyToDirectory());

                        // Execute the command and reset the command string
                        CommandLine.ExecuteCommandLine(workingDirectory, commandString.toString());
                        commandString = new StringBuilder();
                    }
                }
                appendExecutionOutput(
                        "Task with name: " +
                                task.getTaskName() +
                                " has completed execution." +
                                System.getProperty("line.separator"));

            } else {
                appendExecutionOutput(
                        "Skipping Task: " +
                                task.getTaskName() +
                                " due to not being enabled." +
                                System.getProperty("line.separator"));
            }
        }
    }

    /**
     * Returns a new {@link ArrayList} of tasks to be used by other parts of the application.
     * @return A new {@link ArrayList} of {@link MavenTask} objects held by the service.
     */
    public ArrayList<MavenTask> getTasks() {
        return new ArrayList<>(taskList);
    }

    /**
     * Returns a specific {@link MavenTask} from the list of all maven tasks held by the service.
     * @param id The UUID to retrieve from the list of tasks.
     * @return A {@link MavenTask} object, or null if the task doesn't exist.
     */
    public MavenTask getTask(UUID id) {
        for(MavenTask task : taskList) {
            if(task.getId().equals(id))
                return task;
        }
        return null;
    }

    /**
     * Returns the index in the processes internal task list of a specific {@link MavenTask}.
     * @param id The {@link UUID} of the task to retrieve the index of.
     * @return The index of the {@link MavenTask} in the processes internal task list, or -1 if it doesn't exist.
     */
    public int getTaskIndex(UUID id) {
        for(MavenTask task: taskList) {
            if(task.getId().equals(id))
                return taskList.indexOf(task);
        }
        return -1;
    }

    /**
     * Adds a new {@link MavenTask} to the service's list of tasks.
     * @param task The {@link MavenTask} to add to the list.
     * @return True if object was added successfully, False otherwise.
     */
    public boolean addTask(MavenTask task) {
        if(getTask(task.getId()) != null)
            return updateTask(task);

        taskList.add(task);

        if(getTask(task.getId()) != null) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskAdded(task);
            }
            appendExecutionOutput(
                    "New maven task: " +
                            task.getId() +
                            " with name: " +
                            task.getTaskName() +
                            " has been added." +
                            System.getProperty("line.separator"));
            return true;
        }
        return false;
    }

    /**
     * Updates an existing {@link MavenTask} from the service's list of tasks.
     * @param task The {@link MavenTask} to update from the list of tasks.
     * @return True if the task updated successfully, False otherwise.
     */
    public boolean updateTask(MavenTask task) {
        int indexToUpdate = getTaskIndex(task.getId());

        if(indexToUpdate == -1)
            return addTask(task);


        taskList.set(indexToUpdate, task);

        if(getTask(task.getId()) != null) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskUpdated(task);
            }
            appendExecutionOutput(
                    "Maven task: " +
                            task.getId() +
                            " with name: " +
                            task.getTaskName() +
                            " has been updated." +
                            System.getProperty("line.separator"));
            return true;
        }
        return false;
    }

    /**
     * Removes a {@link MavenTask} from the service's list of tasks.
     * @param task The {@link MavenTask} to remove from the list of tasks.
     * @return True if the object was successfully removed, False otherwise.
     */
    public boolean removeTask(MavenTask task) {
        boolean removed = taskList.remove(task);

        if(removed) {
            for (IProcessServiceListener listener : listeners) {
                listener.taskRemoved(task);
            }
            appendExecutionOutput(
                    "Maven task: " +
                            task.getId() +
                            " with name: " +
                            task.getTaskName() +
                            " has been removed." +
                            System.getProperty("line.separator"));
            return true;
        }
        return false;
    }

    /**
     * Reorders elements in {@link #taskList} by swapping positions. This is a better method
     * than just removing the element and inserting at the proper position as the list doesn't
     * require resizing to a smaller, then larger size. There may even be a better way to implement
     * this kind of re-ordering of the list, but for now, the list is small, and a bubble-sort-like
     * implementation for re-ordering will suffice.
     * @param from The index the {@link MavenTask} to be re-ordered resides at.
     * @param to The index to move the {@link MavenTask} to.
     */
    public void reorderTaskPosition(int from, int to) {
        // Handle moving the item towards the beginning of the table
        if (from > to) {
            while (from != to) {
                Collections.swap(taskList, from, from - 1);
                from--;
            }
        // Handle moving the item towards the end of the table
        } else {
            // subtracting 1 from the 'to' side of things is required when moving up, as off-by-one error otherwise.
            to--;
            while(from != to) {
                Collections.swap(taskList, from, from + 1);
                from++;
            }
        }
    }

    /**
     * A public facing method to let the service know to tell listeners of the service that there is
     * new execution output from a child process.
     * @param toAppend The string to tell other listeners about.
     */
    public void appendExecutionOutput(String toAppend) {
        for(IProcessServiceListener listener : listeners)
            listener.processOutputAppend(toAppend);
    }

    /**
     * Adds an implementation of {@link IProcessServiceListener} to the service's internal list of listeners
     * @param listener A class that implements {@link IProcessServiceListener}
     * @return True if the listener was added successfully, False otherwise.
     */
    public boolean addListener(IProcessServiceListener listener) {
        return listeners.add(listener);
    }

    /**
     * Removes an existing implementation of {@link IProcessServiceListener} from the service's internal list
     * of listeners.
     * @param listener A class that implements {@link IProcessServiceListener}
     * @return True if the listener was removed successfully, False otherwise.
     */
    public boolean removeListener(IProcessServiceListener listener) {
        return listeners.remove(listener);
    }
}
