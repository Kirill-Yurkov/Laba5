package server.commands;

import server.Server;
import server.commands.interfaces.Command;

import java.util.Collections;

public class Shuffle implements Command {
    private Server server;

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
        Collections.shuffle(server.getListManager().getTicketList());
        return "successfully shuffled";
    }

    @Override
    public String getName() {
        return "shuffle";
    }

    @Override
    public String description() {
        return "перемешать элементы коллекции в случайном порядке";
    }
}
