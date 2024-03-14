package server;

import server.commands.Add;
import server.commands.Command;
import server.patternclass.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    private HashMap<String, Command> commandHashMap = new HashMap<>();
    private List<Ticket> ticketList = new ArrayList<>();

    {
     commandHashMap.put("add",new Add());
    }
    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }
    public void perform(String cmd){
        if(commandHashMap.containsKey(cmd)){
            Command command = commandHashMap.get(cmd);

        }
    }

}
