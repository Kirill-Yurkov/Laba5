package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.commons.IdCounter;
import server.managers.FileManager;
import server.managers.ListManager;
import server.patternclass.Ticket;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clear implements Command {
    @Override
    public String execute() {
        List<Ticket> tickets = new ArrayList<>();
        IdCounter.setIdTickets(new HashMap<>());
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
