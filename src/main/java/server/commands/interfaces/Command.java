package server.commands.interfaces;

import server.Server;

public interface Command {
    void setServer(Server server);
    String execute();
    String getName();
    String description();

}
