package server;

import lombok.Getter;
import server.exceptions.CommandValueException;
import server.managers.CommandInvoker;
import server.managers.FileManager;
import server.managers.ListManager;
import server.utilities.IdCounter;
import server.utilities.TicketCreator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;

@Getter
public class Server {
    private final CommandInvoker commandInvoker = new CommandInvoker(this);
    private final FileManager fileManager = new FileManager(this);
    private final ListManager listManager = new ListManager(this);
    private final IdCounter idCounter = new IdCounter(this);
    private final TicketCreator ticketCreator = new TicketCreator(this);
    private final FileManager.ReaderWriter readerWriter = fileManager.new ReaderWriter();
    private final FileManager.InputOutput inputOutput = fileManager.new InputOutput();

    public void setFilePath(String filePath) {
        fileManager.setFilePath(filePath);
        readerWriter.readXML();
        listManager.readTicketList();
    }

    public void setReader(BufferedReader reader) {
        inputOutput.setReader(reader);
        ;
    }

    public void setWriter(BufferedOutputStream writer) {
        inputOutput.setWriter(writer);
    }

    public String inPut() {
        try {
            return inputOutput.getReader().readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void outPut(String text) {
        try {
            String s = text;
            inputOutput.getWriter().write(s.getBytes());
            inputOutput.getWriter().flush();
        } catch (IOException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }

    }

    public String invoke(String commandName) {
        try {
            return commandInvoker.invoke(commandName);
        } catch (CommandValueException ignored) {
            return "Incorrect value of command";
        } catch (NullPointerException ignored) {
            return "Incorrect command";
        }
    }
}
