package com.myredis.server.parser;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.parser.model.QueryCommand;
import com.myredis.server.exception.CommandParsingException;

public class CommandParser {
    public static CommandObject parseCommand(String query) throws CommandParsingException{
        CommandObject commandObject = new CommandObject();
        String[] parts = query.split("\\s+");
        if(parts.length < 1) throw new CommandParsingException();
        String part0 = parts[0];
        QueryCommand queryCommand;
        try {
            queryCommand = QueryCommand.valueOf(part0);
            commandObject.setQueryCommand(queryCommand);
        }catch (IllegalArgumentException a){
            throw new CommandParsingException();
        }
        if(QueryCommand.PRINT.equals(queryCommand)) return commandObject;
        if (parts.length < 2) throw new CommandParsingException("No key provided");
        String key = parts[1];
        commandObject.setKey(key);
        if (QueryCommand.SET.equals(queryCommand)) {
            if(parts.length < 3)
                throw new CommandParsingException("No value provided");
            String value = parts[2];
            commandObject.setValue(value);
        }
        return commandObject;
    }
}
