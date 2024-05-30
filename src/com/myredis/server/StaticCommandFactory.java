package com.myredis.server;

import com.myredis.server.commands.*;
import com.myredis.server.exception.CommandParsingException;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandObject;

public class StaticCommandFactory <K, V>{
    public static Command getCommand(CommandObject commandObject, Cache<String, Object> cache){
        switch (commandObject.getQueryCommand()){
            case GET -> {
                return new FetchCommand(commandObject, cache);
            }
            case SET -> {
                return new SetCommand(commandObject, cache);
            }
            case EVICT -> {
                return new EvictCommand(commandObject, cache);
            }
            case PRINT -> {
                return new PrintCommand(commandObject, cache);
            }
        }
        throw new CommandParsingException("Invalid Command");
    }
}
