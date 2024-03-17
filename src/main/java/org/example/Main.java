package org.example;

import server.Server;

import java.io.*;
import java.util.Scanner;

public class Main {
    private final static Scanner src = new Scanner(System.in);

    public static void main(String[] args) {

        String filePath = "src/main/resources/Collection.xml";
        Server server = new Server();
        server.setFilePath(filePath);
        server.setReader(new BufferedReader(new InputStreamReader(System.in)));
        server.setWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        while (true) {
            server.outPut("Введите комманду (для справки используйте комманду help)");
            server.outPut("~ ");

            String commandFromConsole = src.nextLine();
            System.out.println(server.invoke(commandFromConsole) + "\n");
        }
    }
}
