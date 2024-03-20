package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.StopServerException;
import server.utilities.CommandValues;

import java.io.*;

public class ExecuteScript implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE;
    }

    @Override
    public void setServer(Server server) {
        this.server=server;
    }

    @Override
    public String execute(String s) throws StopServerException {
        server.stop();
        server.start(new File(s));
        throw new StopServerException("");
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String description() {
        return "cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
