package server.commands;

import server.Server;
import server.commands.interfaces.Command;

public class Info implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
        return server.getReaderWriter().getCollectionInfo().toString();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String description() {
        return "вывести в стандартный поток вывода информацию о коллекции";
    }
}
