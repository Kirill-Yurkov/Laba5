package server;

import lombok.Getter;
import server.exceptions.CommandCollectionZeroException;
import server.exceptions.CommandValueException;
import server.exceptions.FileException;
import server.exceptions.StopServerException;
import server.managers.CommandInvoker;
import server.managers.FileManager;
import server.managers.ListManager;
import server.utilities.IdCounter;
import server.utilities.TicketCreator;

import java.io.*;

@Getter
public class Server {
    private final CommandInvoker commandInvoker = new CommandInvoker(this);
    private final FileManager fileManager = new FileManager(this);
    private final ListManager listManager = new ListManager(this);
    private final IdCounter idCounter = new IdCounter(this);
    private final TicketCreator ticketCreator = new TicketCreator(this);
    private final FileManager.ReaderWriter readerWriter = fileManager.new ReaderWriter();
    private final FileManager.InputOutput inputOutput = fileManager.new InputOutput();
    private boolean serverOn;

    public Server(BufferedReader reader, BufferedOutputStream writer) {
        inputOutput.setReader(reader);
        inputOutput.setWriter(writer);
    }

    public void stop() {
        serverOn = false;
    }


    public void start() {
        if (fileManager.initializeFile()) {
            serverOn = true;
            while (serverOn) {
                try {
                    inputOutput.outPut("Введите комманду (для справки используйте комманду help) \n~ ");
                    String commandFromConsole = inputOutput.inPut();
                    if (commandFromConsole == null) {
                        inputOutput.outPut("\nПолучен сигнал завершения работы.");
                        serverOn = false;
                        return;
                    }
                    String str = invoke(commandFromConsole);
                    if (str != null) {
                        inputOutput.outPut(str + "\n");
                        inputOutput.outPut("\n");
                    } else {
                        inputOutput.outPut("\n");
                    }
                } catch (StopServerException e) {
                    inputOutput.outPut("Command isn't valid: " + e.getMessage() + "\n");
                    inputOutput.outPut("\n");
                }
            }
        }
    }

    public void start(File file) {
        try {
            FileReader f = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(f);
            inputOutput.setReader(br);
            String commandFromConsole;
            while ((commandFromConsole = br.readLine()) != null) {
                try {
                    String str = invoke(commandFromConsole);
                    if (str != null) {
                        inputOutput.outPut(str + "\n");
                    } else {
                        inputOutput.outPut("\n");
                    }
                } catch (StopServerException e) {
                    inputOutput.outPut("Script isn't valid: " + e.getMessage() + "\n");
                    f.close();
                    break;
                }
            }
            f.close();
        } catch (Exception e) {
            inputOutput.outPut("Script isn't valid: " + e.getMessage() + "\n");
        }
    }



    public String invoke(String commandName) throws StopServerException {
        try {
            return commandInvoker.invoke(commandName);
        } catch (CommandValueException e) {
            throw new StopServerException("incorrect value of command: " + e.getMessage());
        } catch (NullPointerException ignored) {
            throw new StopServerException("incorrect command");
        } catch (CommandCollectionZeroException e) {
            throw new StopServerException("command is useless: " + e.getMessage());
        }
    }
}
