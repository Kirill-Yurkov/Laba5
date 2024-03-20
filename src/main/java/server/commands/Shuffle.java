package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.utilities.CommandValues;

import java.util.Collections;

public class Shuffle implements Command {
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
    public String execute(String s) throws CommandCollectionZeroException {
        if(server.getListManager().getTicketList().isEmpty()){
            throw new CommandCollectionZeroException("collection is zero");
        }
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
