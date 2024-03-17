package server.commands;

import server.Server;
import server.commands.interfaces.Command;

public class Exit implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
        System.exit(-1);
        return null;
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
