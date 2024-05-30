package com.myredis.server;

import com.myredis.server.commands.Command;
import com.myredis.server.data.cache.Cache;
import com.myredis.server.data.cache.CacheEnum;
import com.myredis.server.data.cache.LRUCache;
import com.myredis.server.data.cache.SimpleCache;
import com.myredis.server.parser.CommandParser;
import com.myredis.server.parser.model.CommandObject;
import com.myredis.server.parser.model.CommandResult;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RedisServer {

    private final Cache<String, Object> cache;
    private int port;

    public static void main(String[] args) {
        // Options Definition
        Options options = new Options();
        Option portOption = new Option("p", "port", true, "port number");
        Option strategyOption = new Option("s", "strategy", true, "caching strategy");

        portOption.setRequired(true);
        options.addOption(portOption);
        options.addOption(strategyOption);


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        // parsing all the commands
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("my-redis-server", options);
            System.exit(1);
            return;
        }


        int port = 6379; // default port
        if (cmd.hasOption("port")) {
            try {
                port = Integer.parseInt(cmd.getOptionValue("port"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 6379.");
            }
        }
        CacheEnum strategy = CacheEnum.DEFAULT;
        if(cmd.hasOption("strategy")){
            try{
                 strategy = CacheEnum.valueOf(cmd.getOptionValue("strategy"));
            }catch (IllegalArgumentException e){
                System.err.println("Invalid Caching strategy");
            }
        }
        new RedisServer(port, strategy).startServer();
    }
    public RedisServer(){
        this.port = 8080;
        cache = new LRUCache<>();
    }
    public RedisServer(int port, CacheEnum cacheEnum){
        this.port = port;
        switch (cacheEnum){
            case LRU : {
                cache = new LRUCache<>(); break;
            }
            default:{
                cache = new SimpleCache<>();
            }
        }
        System.out.println("Using Cache: " + cacheEnum);
    }

    public CommandResult serve(CommandObject commandObject){
        System.out.println("Your Command is " + commandObject);
        Command command = StaticCommandFactory.getCommand(commandObject, this.cache);
        CommandResult commandResult = command.execute();
        System.out.println("commandResult " + commandResult);
        return commandResult;
    }

    public void startServer(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                if("".equals(inputLine)) continue;
                CommandResult commandResult = null;
                try{
                    CommandObject commandObject = CommandParser.parseCommand(inputLine);
                    commandResult = serve(commandObject);
                }catch (Exception e){
                    e.printStackTrace();
                    commandResult = CommandResult.buildFail(e.getMessage());
                }
                writer.println(commandResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
