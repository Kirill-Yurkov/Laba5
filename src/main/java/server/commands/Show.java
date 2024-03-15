package server.commands;

import server.Server;
import server.commands.interfaces.Command;

import java.util.ArrayList;

public class Show implements Command {
    @Override
    public String execute() {
        return Server.getTicketList().toString() + "\n successfully";
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
