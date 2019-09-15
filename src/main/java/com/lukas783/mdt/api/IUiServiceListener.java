package com.lukas783.mdt.api;

import java.io.File;

/**
 * A listener interface that can be implemented by any class that needs to listen to
 * the {@link com.lukas783.mdt.service.UiService} singleton-service.
 */
public interface IUiServiceListener {
    /**
     * A required method that is meant to be called when
     * {@link com.lukas783.mdt.service.UiService#UpdateDirectory(String, File)} finishes processing.,
     * @param componentTag The tag that an implementation of {@link IUiServiceListener} can use to update a component
     * @param directory The changed directory, as a {@link File}
     */
    public void directoryUpdated(String componentTag, File directory);
}
