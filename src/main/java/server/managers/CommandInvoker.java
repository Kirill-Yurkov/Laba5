package server.managers;


import server.commands.interfaces.Command;

import java.util.HashMap;

public class CommandInvoker {
    private final HashMap<String, Command> commands = new HashMap<>();
    public String invoke(String commandName){
        return commands.get(commandName).execute();
    }
    public void registerCommand(Command ... commandsToRegister){
        for (Command command: commandsToRegister){
            commands.put(command.getName(), command);
        }
    }
}
