package com.lukas783.mdt.api;

import java.util.UUID;

/**
 * A POJO that defines a task to complete. Contains all the useful information to build a set of processes for
 * building a maven repository
 *
 * @author Lucas Carpenter
 */
public class MavenTask {

    // Declarations of variables held by the objects
    private UUID id;
    private String taskName;
    private String workingDirectory;
    private String copyToDirectory;
    private String renameString;
    private boolean copy;
    private boolean rename;
    private boolean cleanTarget;
    private boolean unpackage;
    private boolean doInstall;

    /**
     * Constructs a MavenTask object to be used elsewhere.
     * @param id A UUID to match the object to components of the application.
     * @param taskName The readable name to identify the task.
     * @param workingDirectory The directory to begin the task inside.
     * @param copyToDirectory The directory to copy the resulting task to.
     * @param renameString The string to rename the resulting file/directory to
     * @param copy Whether to copy the result to another location.
     * @param rename Whether to rename the result to another name.
     * @param cleanTarget Whether to run the clean argument when building the process.
     * @param unpackage Whether to unpackage the resulting package.
     * @param doInstall Whether to run an install goal or a package goal.
     */
    private MavenTask(UUID id,
                      String taskName,
                      String workingDirectory,
                      String copyToDirectory,
                      String renameString,
                      boolean copy,
                      boolean rename,
                      boolean cleanTarget,
                      boolean unpackage,
                      boolean doInstall) {
        this.id = id;
        this.taskName = taskName;
        this.workingDirectory = workingDirectory;
        this.copyToDirectory = copyToDirectory;
        this.renameString = renameString;
        this.copy = copy;
        this.rename = rename;
        this.cleanTarget = cleanTarget;
        this.unpackage = unpackage;
        this.doInstall = doInstall;
    }

    /**
     * Retrieves the UUID of the object.
     * @return The UUID of the task object.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retrieves the task name.
     * @return The string for the object.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Retrieves the working directory of the object.
     * @return The working directory of the object.
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Retrieves the copy-to directory of the object.
     * @return The copy-to directory of the object.
     */
    public String getCopyToDirectory() {
        return copyToDirectory;
    }

    /**
     * Retrieves the string to rename the resulting file to for the object.
     * @return The string to rename the resulting file to for the object.
     */
    public String getRenameString() {
        return renameString;
    }

    /**
     * Retrieves whether to copy the result to another location upon completion.
     * @return True if the result should be copied to another location, False otherwise.
     */
    public boolean doCopy() {
        return copy;
    }

    /**
     * Retrieves whether to rename the result to another name upon completion.
     * @return True if the result should be renamed to another file name, False otherwise.
     */
    public boolean doRename() {
        return rename;
    }

    /**
     * Retrieves whether to pre-pend the 'clean' command to the maven goal.
     * @return True if the 'clean' command should be pre-pended to the goal, False otherwise.
     */
    public boolean cleanTarget() {
        return cleanTarget;
    }

    /**
     * Retrieves whether to attempt to untar/unzip the result of the maven goal.
     * @return True if the result of the maven goal should be untarred/unzipped, False otherwise.
     */
    public boolean doUnpackage() {
        return unpackage;
    }

    /**
     * Retrieves whether to run the maven task as a package goal or an install goal.
     * @return True if the task should run an install goal, False otherwise.
     */
    public boolean doInstall() {
        return doInstall;
    }

    /**
     * A Builder to force proper creation of the {@link MavenTask} object.
     */
    public static class Builder {

        // Declaration of private builder variables
        private UUID id;
        private String taskName;
        private String workingDirectory;
        private String copyToDirectory;
        private String renameString;
        private boolean copy;
        private boolean rename;
        private boolean cleanTarget;
        private boolean unpackage;
        private boolean doInstall;

        /**
         * Required constructor to instantiate a new builder.
         */
        public Builder() { }

        /**
         * Sets the UUID for the builder.
         * @param id A valid UUID to identify the {@link MavenTask} object.
         * @return A modified builder
         */
        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the task name for the builder.
         * @param taskName A String to identify the {@link MavenTask} object.
         * @return A modified builder
         */
        public Builder taskName(String taskName) {
            this.taskName = taskName;
            return this;
        }

        /**
         * Sets the working directory for the builder.
         * @param workingDirectory The working directory of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder workingDirectory(String workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        /**
         * Sets the copy-to directory for the builder.
         * @param copyToDirectory The copy-to directory of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder copyToDirectory(String copyToDirectory) {
            this.copyToDirectory = copyToDirectory;
            return this;
        }

        /**
         * Sets the rename string for the builder.
         * @param renameString The rename string of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder renameString(String renameString) {
            this.renameString = renameString;
            return this;
        }

        /**
         * Sets the copy flag for the builder.
         * @param copy The copy flag of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder copy(boolean copy) {
            this.copy = copy;
            return this;
        }

        /**
         * Sets the rename flag for the builder.
         * @param rename The rename flag of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder rename(boolean rename) {
            this.rename = rename;
            return this;
        }

        /**
         * Sets the cleanTarget flag for the builder.
         * @param cleanTarget The cleanTarget flag of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder cleanTarget(boolean cleanTarget) {
            this.cleanTarget = cleanTarget;
            return this;
        }

        /**
         * Sets the unpackage flag for the builder.
         * @param unpackage The unpackage flag of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder unpackage(boolean unpackage) {
            this.unpackage = unpackage;
            return this;
        }

        /**
         * Sets the doInstall flag for the builder.
         * @param doInstall The doInstall flag of the {@link MavenTask} object.
         * @return A modified builder.
         */
        public Builder doInstall(boolean doInstall) {
            this.doInstall = doInstall;
            return this;
        }

        /**
         * A helper function of the builder to validate the builder has enough information
         * to create a fully functioning {@link MavenTask} object.
         * @return True if the builder contains enough valid information, False otherwise.
         */
        private boolean isValid() {
            return id != null && taskName.length() > 0 && workingDirectory.length() > 0 &&
                    (!rename || renameString.length() > 0) && (!copy || copyToDirectory.length() > 0);
        }

        /**
         * Builds a new {@link MavenTask} object with the currently set variables of the builder.
         * @return A new {@link MavenTask} object if the builder contains valid data, null otherwise.
         */
        public MavenTask build() {
            if(isValid())
                return new MavenTask(id, taskName, workingDirectory, copyToDirectory, renameString,
                        copy, rename, cleanTarget, unpackage, doInstall);
            return null;
        }
    }
}
