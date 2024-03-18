package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;
import server.utilities.CommandValues;

public class Update implements Command {
    private Server server;
    @Override
    public CommandValues getValue() {
        return CommandValues.VALUE_ELEMENT;
    }

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute(String s) {
        long id;
        try {
            id = Long.parseLong(s);
        } catch (NumberFormatException ignored){
            return "Incorrect value of command";
        }
        for(Ticket ticket: server.getListManager().getTicketList()){
            if (ticket.getId() == id){
                server.getListManager().remove(ticket);
                Ticket newTicket = server.getTicketCreator().createTicketGroup();
                newTicket.setId(id);
                if(newTicket.getEvent()!=null){
                    newTicket.getEvent().setId(server.getIdCounter().getIdForEvent(newTicket.getEvent()));
                }
                server.getListManager().add(newTicket);
                return "successfully";
            }
        }
        return "Id not found";
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String description() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
