package com.myredis.server.parser.model;


public enum QueryCommand {
    SET,
    GET,
    EVICT,
    PRINT,
    CSET;

    QueryCommand() {

    }
}
