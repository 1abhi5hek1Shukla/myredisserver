package com.myredis.server.commands;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandResult;

public abstract class Command {
    private CommandObject commandObject;
    private Cache<String, Object> cache;

    public Command(CommandObject commandObject, Cache<String, Object> cache) {
        this.commandObject = commandObject;
        this.cache = cache;
    }

    public CommandObject getCommandObject() {
        return commandObject;
    }

    public void setCommandObject(CommandObject commandObject) {
        this.commandObject = commandObject;
    }

    public Cache<String, Object> getCache() {
        return cache;
    }

    public void setCache(Cache<String, Object> cache) {
        this.cache = cache;
    }

    public abstract CommandResult execute();
}
