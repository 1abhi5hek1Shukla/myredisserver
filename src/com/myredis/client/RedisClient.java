package com.myredis.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RedisClient {
    public static void main(String[] args) {


        try(Socket socket = new Socket("localhost", 8080);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)){

            Scanner scanner = new Scanner(System.in);

            while(!socket.isClosed()){

                System.out.print("redis> ");
                String command = scanner.nextLine().trim();

                printWriter.println(command);

                String response = bufferedReader.readLine();

                System.out.println("Server response: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
