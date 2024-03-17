package server.commands;

import lombok.Getter;
import lombok.Setter;
import server.Server;
import server.commands.interfaces.Command;
import server.managers.ListManager;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;
public class Clear implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
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
