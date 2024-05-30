package com.myredis.server.parser.model;


public class CommandObject {
    private QueryCommand queryCommand;
    private String key;
    private Object value;

    public QueryCommand getQueryCommand() {
        return queryCommand;
    }

    public void setQueryCommand(QueryCommand queryCommand) {
        this.queryCommand = queryCommand;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CommandObject{" +
                "queryCommand=" + queryCommand +
                ", key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
