package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.CommandValueException;
import server.exceptions.StopServerException;
import server.utilities.CommandValues;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScript implements Command {
    private Server server;
    private static Set<String> fileSet = new HashSet<>();

    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(String filePath) throws CommandValueException {

        if (checkFilePermission(filePath)) {
            if(!fileSet.contains(filePath)){
                fileSet.add(filePath);
                server.start(new File(filePath));
                server.getInputOutput().setReader(new BufferedReader(new InputStreamReader(System.in)));
                fileSet.remove(filePath);
                return null;
            } else {
                throw new CommandValueException("file has recursion");
            }

        }else {
            throw new CommandValueException("error");
        }

    }
    private boolean checkFilePermission(String filePath) throws  CommandValueException {
        try {
            File file = new File(filePath);
            FileReader f = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(f);

        } catch (FileNotFoundException e) {
            throw new CommandValueException("file not found");
        } catch (SecurityException e) {
            throw new CommandValueException("file permission denied");
        }
        return true;
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
