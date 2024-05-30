package com.myredis.server.commands;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandResult;

public class EvictCommand extends Command{
    public EvictCommand(CommandObject commandObject, Cache<String, Object> cache) {
        super(commandObject, cache);
    }

    @Override
    public CommandResult execute() {
        getCache().evict(getCommandObject().getKey());
        return getCache().get(getCommandObject().getKey()) == null ? CommandResult.buildSuccess() : CommandResult.buildFail();
    }
}
