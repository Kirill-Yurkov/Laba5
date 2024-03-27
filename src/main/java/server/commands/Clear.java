package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

import java.util.ArrayList;
import java.util.List;
public class Clear implements Command {
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
    public String execute(String value) {
        List<Ticket> tickets = new ArrayList<>();
        server.getListManager().setTicketList(tickets);
        return "successfully";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String description() {
        return "очистить коллекцию";
    }
}
