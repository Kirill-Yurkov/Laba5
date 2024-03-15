package server.commands.interfaces;

public interface Command {
    String execute();
    String getName();
    String description();

}
