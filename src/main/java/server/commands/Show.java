package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.managers.ListManager;
import server.patternclass.Ticket;

import java.util.ArrayList;

public class Show implements Command {
    @Override
    public String execute() {
        StringBuilder s = new StringBuilder();
        for(Ticket ticket: ListManager.getTicketList()){
            s.append(ticket.toString()).append("\n");
        }
        return s + "successfully";
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String description() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
