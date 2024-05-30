package com.myredis.server.data.persist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class DataPersister {
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public abstract void loadAndPersist() throws IOException;
}
