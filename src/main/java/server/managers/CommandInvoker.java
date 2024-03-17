package server.managers;


import lombok.Getter;
import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandValueException;
import server.utilities.ReflectionImplements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandInvoker {
    private final HashMap<String, Command> commands = new HashMap<>();
    @Getter
    private Server server;

    public CommandInvoker(Server server) {
        this.server = server;
        List<Class<?>> implementations = ReflectionImplements.getImplementations(Command.class);
        for (Class<?> implementation : implementations) {
            try {
                registerCommand((Command) implementation.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String invoke(String commandName) throws CommandValueException {
        String[] s = commandName.split(" ");
        return commands.get(commandName).execute();
    }

    public void registerCommand(Command... commandsToRegister) {
        for (Command command : commandsToRegister) {
            command.setServer(server);
            commands.put(command.getName(), command);
        }
    }
    public List<Command> getCommands(){
        return new ArrayList<>(this.commands.values());
    }
}
