package server.commands.interfaces;

import server.Server;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.exceptions.StopServerException;
import server.utilities.CommandValues;

public interface Command {
    CommandValues getValue();
    void setServer(Server server);
    String execute(String s) throws StopServerException, CommandValueException, CommandCollectionZeroException;
    String getName();
    String description();
}
