package com.myredis.server.parser.model;

import java.io.Serializable;

public class CommandResult implements Serializable {

    private boolean success;
    private String message;
    private Object value;

    public CommandResult(boolean success) {
        this.success = success;
    }

    public CommandResult(boolean success, Object value) {
        this.success = success;
        this.value = value;
    }

    public CommandResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static CommandResult buildSuccess(){
        return new CommandResult(true);
    }

    public static CommandResult buildSuccess(Object data){
        return new CommandResult(true, data);
    }
    public static CommandResult buildFail(){
        return new CommandResult(false);
    }
    public static CommandResult buildFail(String message){
        return new CommandResult(false, message);
    }


    @Override
    public String toString() {
        return "CommandResult{" +
                "success=" + success +
                ((message == null) ? "" : (", message=" + message)) +
                ((value == null) ? "" : (", value=" + value)) +
                '}';
    }
}
