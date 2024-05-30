package com.myredis.server.commands;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandResult;

public class PrintCommand extends Command{

    public PrintCommand(CommandObject commandObject, Cache<String, Object> cache) {
        super(commandObject, cache);
    }

    @Override
    public CommandResult execute() {
        getCache().printMemory();
        return CommandResult.buildSuccess();
    }
}
