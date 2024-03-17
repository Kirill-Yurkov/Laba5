package server.commands;

import lombok.Getter;
import lombok.Setter;
import server.Server;
import server.commands.interfaces.Command;

import java.io.Console;

public class Help implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }
    @Override
    public String execute(){
        StringBuilder s= new StringBuilder();
        for(Command command: server.getCommandInvoker().getCommands()){
            s.append("\n").append(command.getName()).append(" : ").append(command.description());
        }
        return s.toString();
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
