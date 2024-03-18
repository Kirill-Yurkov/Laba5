package server;

import lombok.Getter;
import server.exceptions.CommandValueException;
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
    private boolean flag;

    public Server(BufferedReader reader, BufferedOutputStream writer) {
        inputOutput.setReader(reader);
        inputOutput.setWriter(writer);
    }

    public void setFilePath(String filePath) throws StopServerException {
        if (new File(filePath).canRead() && new File(filePath).canWrite()) {
            fileManager.setFilePath(filePath);
            readerWriter.readXML();
            listManager.readTicketList();
        } else {
            throw new StopServerException("Wrong file");
        }
    }

    public void stop() {
        flag = false;
    }

    public void start(boolean x) {
        if (x) {
            outPut("Enter the file path to collection file :\n~ ");
            try {
                setFilePath(inPut());//src/main/resources/Collection.xml
                outPut("Successfully was read!");
            } catch (StopServerException e) {
                outPut(e.getMessage() + "\n");
                x = false;
            }
        }
        flag = x;
        while (flag) {
            try {
                outPut("Введите комманду (для справки используйте комманду help) \n~ ");
                String commandFromConsole = inPut();
                outPut(invoke(commandFromConsole) + "\n");
            } catch (StopServerException e) {
                outPut(e.getMessage() + "\n");
            }
        }
    }

    public void start(File file) {
        try {
            FileReader f = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(f);
            String commandFromConsole;
            while ((commandFromConsole = br.readLine()) != null) {
                try {
                    outPut(invoke(commandFromConsole) + "\n");
                } catch (StopServerException e) {
                    outPut("Script isn't valid: " + e.getMessage() + "\n");
                    f.close();
                    break;
                }
            }
            f.close();
            start(true);
        } catch (Exception e) {
            outPut("Script isn't valid " + e.getMessage() + "\n");
        }
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
            throw new RuntimeException(e);
        }

    }

    public String invoke(String commandName) throws StopServerException {
        try {
            return commandInvoker.invoke(commandName);
        } catch (CommandValueException e) {
            throw new StopServerException("Incorrect value of command: " + e.getMessage() + "\n");
        } catch (NullPointerException ignored) {
            throw new StopServerException("Incorrect command \n");
        }
    }
}
