package org.example;

import server.Server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private final static Scanner src = new Scanner(System.in);

    public static void main(String[] args) {

        String filePath = "src/main/resources/Collection.xml";
        Server server = new Server();
        server.setFilePath(filePath);
        server.setReader(new BufferedReader(new InputStreamReader(System.in)));
        server.setWriter(new BufferedOutputStream(System.out));
        while (true) {
            server.outPut("Введите комманду (для справки используйте комманду help) \n~ ");;
            String commandFromConsole = server.inPut();
            server.outPut(server.invoke(commandFromConsole) + "\n");
        }
    }
}
