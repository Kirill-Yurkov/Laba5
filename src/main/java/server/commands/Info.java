package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandCollectionZeroException;
import server.utilities.CommandValues;

public class Info implements Command {
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
    public String execute(String value) throws CommandCollectionZeroException {
        if (server.getReaderWriter().getCollectionInfo().isEmpty()) {
            throw new CommandCollectionZeroException("Collection information will be updating in next save");
        }
        StringBuilder str = new StringBuilder();
        for (String i : server.getReaderWriter().getCollectionInfo()) {
            str.append("\n").append(i);
        }
        return str.toString();
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
