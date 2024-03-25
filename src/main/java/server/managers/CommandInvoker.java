package server.managers;


import lombok.Getter;
import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.exceptions.ScriptException;
import server.exceptions.StopServerException;
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

    public String invoke(String commandName) throws CommandValueException, NullPointerException, StopServerException, CommandCollectionZeroException{
        String[] s = commandName.strip().split(" ");
        switch (commands.get(s[0]).getValue()) {
            case NOTHING, ELEMENT -> {
                if (s.length == 1) {
                    return commands.get(s[0]).execute("");
                }
                throw new CommandValueException("unexpected values");
            }
            case VALUE, VALUE_ELEMENT -> {
                if (s.length == 2) {
                    return commands.get(s[0]).execute(s[1]);
                }
                throw new CommandValueException("wrong valuse");
            }
            default -> throw new NullPointerException("");
        }
    }

    private void registerCommand(Command... commandsToRegister) {
        for (Command command : commandsToRegister) {
            command.setServer(server);
            commands.put(command.getName(), command);
        }
    }

    public List<Command> getCommands() {
        return new ArrayList<>(this.commands.values());
    }
}
