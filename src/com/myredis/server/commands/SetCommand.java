package com.myredis.server.commands;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandResult;

public class SetCommand extends Command{
    public SetCommand(CommandObject commandObject, Cache<String, Object> cache) {
        super(commandObject, cache);
    }

    @Override
    public CommandResult execute(){

        getCache().put(getCommandObject().getKey(), getCommandObject().getValue());
        return getCommandObject()
                .getValue()
                .equals(
                        getCache().get(getCommandObject().getKey())
                ) ? CommandResult.buildSuccess() : CommandResult.buildFail();
    }
}
