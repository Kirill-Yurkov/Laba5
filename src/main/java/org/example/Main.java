package org.example;

import server.Server;
import server.commands.interfaces.Command;
import server.commons.ReflectionImplements;
import server.managers.CommandInvoker;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Scanner src = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

        CommandInvoker invoker = new CommandInvoker();
        List<Class<?>> implementations = ReflectionImplements.getImplementations(Command.class);


        for (Class<?> implementation : implementations) {
            invoker.registerCommand((Command) implementation.newInstance());
        }


        String filePath = "src/main/resources/Collection.xml";
        Server.setFilePath(filePath);
        while (true) {
            System.out.println("Введите комманду (для справки используйте комманду help)");
            System.out.print("~ ");
            String commandFromConsole = src.nextLine();
            System.out.println(invoker.invoke(commandFromConsole));
        }
    }
}
