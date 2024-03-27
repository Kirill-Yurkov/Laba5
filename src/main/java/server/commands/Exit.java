package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.StopServerException;
import server.utilities.CommandValues;

public class Exit implements Command {
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
        server.stop();
        return "Successfully";
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String description() {
        return "завершить программу (без сохранения в файл)";
    }
}
