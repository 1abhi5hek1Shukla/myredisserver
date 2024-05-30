package com.myredis.server.exception;

public class CommandParsingException extends RuntimeException {
    public CommandParsingException (){
        super("Invalid Command");
    }
    public CommandParsingException (String errorMessage){
        super(errorMessage);
    }
}
