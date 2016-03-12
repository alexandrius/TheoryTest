package ge.turtlecat.theorytest.ui.tm;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ge.turtlecat.theorytest.bean.Ticket;
import ge.turtlecat.theorytest.ui.tools.Settings;

/**
 * Created by Alex on 3/12/2016.
 */
public class TicketManager {

    private static TicketManager instance;
    private List<Ticket> currentTickets;
    private static final int TEST_TICKET_AMOUNT = 30;
    private static String TICKET_QUERY = "select * from TICKET where";

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public List<Ticket> getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(List<Ticket> currentTickets) {
        this.currentTickets = currentTickets;
    }

    public void loadTestTickets(final TicketLoadListener ticketsLoaded) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Ticket> tmpTkts = Ticket.findWithQuery(Ticket.class, TICKET_QUERY + " answered = '-1'");
                List<Ticket> tickets = new ArrayList<>();
                tickets.addAll(tmpTkts);
                if (tmpTkts.size() > TEST_TICKET_AMOUNT) {
                    tickets.clear();
                    int index;
                    for (int i = 0; i < TEST_TICKET_AMOUNT; i++) {
                        index = i + 1;
                        if (index == TEST_TICKET_AMOUNT) {
                            if (new Random().nextInt() % 2 == 0) {
                                index++;
                            }
                        }
                        List<Ticket> tkts = Ticket.find(Ticket.class, "topic = ? and answered = ?", index + "", "-1");
                        int random = Math.abs(new Random(System.currentTimeMillis()).nextInt()) % tkts.size();
                        tickets.add(tkts.get(random));
                    }
                }
                setCurrentTickets(tickets);
                ticketsLoaded.onTicketsLoaded();
            }
        }).start();
    }


    public boolean loadWrongTickets() {
        List<Ticket> tkts = Ticket.findWithQuery(Ticket.class, TICKET_QUERY + " answered != corr_answer and answered != '-1'");
        if (tkts.size() > 0) {
            setCurrentTickets(tkts);
            return true;
        }
        return false;
    }

    public boolean loadLastTestTickets() {
        String[] ids = Settings.getLastTicketIds();
        ArrayList<Ticket> tickets = new ArrayList<>();

        for (String id : ids) {
            Ticket tkt = Ticket.findWithQuery(Ticket.class, TICKET_QUERY + " id = '" + id + "'").get(0);
            tickets.add(tkt);
        }

        if (tickets.size() > 0) {
            setCurrentTickets(tickets);
            return true;
        }
        return false;
    }
}