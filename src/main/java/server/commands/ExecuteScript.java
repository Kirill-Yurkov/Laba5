package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.exceptions.ScriptException;
import server.exceptions.StopServerException;
import server.utilities.CommandValues;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ExecuteScript implements Command {
    public static boolean recursing = false;
    private boolean afterRecursive;
    private boolean hasRecursion;
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
    public String execute(String s) throws StopServerException {

        if (checkFilePermission(s)) {
            if(!fileSet.contains(s)){
                fileSet.add(s);
                server.start(new File(s));
                server.getInputOutput().setReader(new BufferedReader(new InputStreamReader(System.in)));
                fileSet.remove(s);
                return null;
            } else {
                throw new StopServerException("file has recursion");
            }

        }else {
            throw new StopServerException("error");
        }

    }
    private boolean checkFilePermission(String s) throws StopServerException {
        try {
            File file = new File(s);
            FileReader f = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(f);

        } catch (FileNotFoundException e) {
            throw new StopServerException("file not found");
        } catch (SecurityException e) {
            throw new StopServerException("file permission denied");
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
