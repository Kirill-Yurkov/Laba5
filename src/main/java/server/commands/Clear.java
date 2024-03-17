package server.commands;

import server.commands.interfaces.Command;
import server.managers.ListManager;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.List;

public class Clear implements Command {
    @Override
    public String execute() {
        List<Ticket> tickets = new ArrayList<>();
        ListManager.setTicketList(tickets);

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
