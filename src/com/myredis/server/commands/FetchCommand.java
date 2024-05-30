package com.myredis.server.commands;

import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.parser.model.CommandResult;

public class FetchCommand extends Command{

    public FetchCommand(CommandObject commandObject, Cache<String, Object> cache) {
        super(commandObject, cache);
    }

    @Override
    public CommandResult execute() {
        Object res = getCache().get(getCommandObject().getKey());
        return res == null ? CommandResult.buildFail() : CommandResult.buildSuccess(res);
    }
}
