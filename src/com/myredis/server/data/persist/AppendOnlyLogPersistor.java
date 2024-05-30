package com.myredis.server.data.persist;

import java.io.*;

public class AppendOnlyLogPersistor extends DataPersister {


    private void processCommand(String command){

    }

    @Override
    public void loadAndPersist() throws IOException {
        if(!getFile().exists())
            return;

        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            String[] parts = line.split("\\s++");
            processCommand(line);
        }


    }
}
