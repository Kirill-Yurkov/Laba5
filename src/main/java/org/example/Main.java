package org.example;

import server.Server;
import server.exceptions.CommandValueException;
import server.managers.CommandInvoker;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private final static Scanner src = new Scanner(System.in);

    public static void main(String[] args){

        CommandInvoker invoker = new CommandInvoker();

        String filePath = "src/main/resources/Collection.xml";
        Server.setFilePath(filePath);
        while (true) {
            System.out.println("Введите комманду (для справки используйте комманду help)");
            System.out.print("~ ");
            String commandFromConsole = src.nextLine();
            try {
                System.out.println(invoker.invoke(commandFromConsole) + "\n");
            } catch (NullPointerException ignored) {
                System.out.println("Incorrect command");
            } catch (CommandValueException ignored){
                System.out.println("Incorrect value of command");
            }
        }
    }
}
