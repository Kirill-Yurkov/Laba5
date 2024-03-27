package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.utilities.CommandValues;

public class Help implements Command {
    private Server server;

    @Override
    public CommandValues getValue() {
        return CommandValues.NOTHING;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }
    @Override
    public String execute(String value){
        StringBuilder str = new StringBuilder();
        for(Command command: server.getCommandInvoker().getCommands()){
            str.append("\n").append(command.getName()).append(" : ").append(command.description());
        }
        return str.toString();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String description() {
        return "вывести справку по доступным командам";
    }
}
