package server;

import lombok.Getter;
import server.commands.ExecuteScript;
import server.exceptions.*;
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
    private boolean isFileInitialized = false;

    public Server(BufferedReader reader, BufferedOutputStream writer) {
        inputOutput.setReader(reader);
        inputOutput.setWriter(writer);
    }

    public void setFilePath(String filePath) throws StopServerException{
        if (new File(filePath).canRead() && new File(filePath).canWrite()) {
            try {
                fileManager.setFilePath(filePath);
                readerWriter.readXML();
                listManager.readTicketList();
            } catch (FileException e){
                throw new StopServerException("File fail: "+e.getMessage());
            }

        } else {
            throw new StopServerException("Wrong file");
        }
    }

    public void stop() {
        serverOn = false;
    }
    private boolean initializeFile(){
        if(isFileInitialized){
            return true;
        }else{
            outPut("Enter the file path to collection file :\n~ ");
            try {
                setFilePath("src/main/resources/Collection.xml");// src/main/resources/Collection.xml
                isFileInitialized = true;
                return true;
            } catch (StopServerException e) {
                outPut(e.getMessage() + "\n");
                isFileInitialized = false;
                return initializeFile();
            }
        }

    }
    public void start(boolean x) {
        if (initializeFile()){
            serverOn = x;
            while (serverOn) {
                try {
                    outPut("Введите комманду (для справки используйте комманду help) \n~ ");
                    String commandFromConsole = inPut();
                    String str = invoke(commandFromConsole);
                    if (str != null || str.isEmpty() || str.isBlank()){
                        outPut(str + "\n");
                    }
                } catch (StopServerException e){
                    outPut("Script isn't valid: " + e.getMessage() + "\n");
                    outPut("\n");
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
                        outPut(str + "\n");
                    }
                }catch (StopServerException e){
                    outPut("Script isn't valid: " + e.getMessage() + "\n");
                    outPut("\n");
                    f.close();
                    break;
                }
            }
            f.close();
        } catch (Exception e) {
            outPut("Script isn't valid: " + e.getMessage() + "\n");
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
            inputOutput.getWriter().write(text.getBytes());
            inputOutput.getWriter().flush();
        } catch (IOException ignored) {

        }
    }

    public String invoke(String commandName) throws StopServerException {
        try {
            return commandInvoker.invoke(commandName);
        } catch (CommandValueException e) {
            throw new StopServerException("Incorrect value of command: " + e.getMessage() + "\n");
        } catch (NullPointerException ignored) {
            throw new StopServerException("Incorrect command \n");
        } catch (CommandCollectionZeroException e) {
            throw new StopServerException("Command is useless: "+e.getMessage()+"\n");
        }
    }
}
