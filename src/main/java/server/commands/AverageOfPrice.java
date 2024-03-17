package server.commands;

import server.Server;
import server.commands.interfaces.Command;
import server.patternclass.Ticket;

public class AverageOfPrice implements Command {
    private Server server;
    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public String execute() {
        int price = 0;
        for(Ticket ticket: server.getListManager().getTicketList()){
            if(ticket.getPrice()!=null) {
                price += ticket.getPrice();
            }
        }
        if(server.getListManager().getTicketList().isEmpty()){
            return "List is empty";
        } else{
            return String.valueOf(price/server.getListManager().getTicketList().size());
        }
    }

    @Override
    public String getName() {
        return "average_of_price";
    }

    @Override
    public String description() {
        return "вывести среднее значение поля price для всех элементов коллекции";
    }
}
