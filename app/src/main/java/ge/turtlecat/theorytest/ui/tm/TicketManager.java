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
    private static String TICKET_QUERY = "select * from TICKET";
    private static String WHERE = "where";

    private static String TICKET_QUERY_WHERE = "select * from TICKET " + WHERE;

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public int getDoneAmount() {
        List<Ticket> tkts = Ticket.findWithQuery(Ticket.class, TICKET_QUERY_WHERE + " answered != '-1'");
        return tkts.size();
    }

    public List<Ticket> getCurrentTickets() {
        return currentTickets;
    }

    public void setCurrentTickets(List<Ticket> currentTickets) {
        this.currentTickets = currentTickets;
    }

    public void loadTestTickets(final TicketLoadListener ticketsLoaded, final boolean full) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String q = full ? "" : " " + WHERE + " answered = '-1'";

                List<Ticket> tmpTkts = Ticket.findWithQuery(Ticket.class, TICKET_QUERY + q);

                List<Ticket> tickets = new ArrayList<>();
                tickets.addAll(tmpTkts);
                //if left tickets amount more than TEST_TICKET_AMOUNT
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

                        List<Ticket> tkts;
                        while (true) {
                            tkts = getUnansweredTicketsForTopic(index, tickets);
                            if (index + 1 <= TEST_TICKET_AMOUNT) {
                                index++;
                            } else {
                                index = 1;
                            }
                            if (tkts.size() > 0) break;
                        }
                        int random = Math.abs(new Random().nextInt()) % tkts.size();
                        tickets.add(tkts.get(random));
                    }
                }
                setCurrentTickets(tickets);
                ticketsLoaded.onTicketsLoaded();
            }
        }).start();
    }


    private List<Ticket> getUnansweredTicketsForTopic(int topic, List<Ticket> tkts) {
        List<Ticket> unAnsweredTickets = Ticket.find(Ticket.class, "topic = ? and answered = ?", topic + "", "-1");
        for (Ticket tkt : tkts) {
            Ticket remove = null;
            for (Ticket unAnsweredTicket : unAnsweredTickets) {
                if (tkt.getId().longValue() == unAnsweredTicket.getId().longValue()) {
                    remove = unAnsweredTicket;
                    break;
                }
            }
            if (remove != null)
                unAnsweredTickets.remove(remove);
        }
        return unAnsweredTickets;
    }

    public boolean loadWrongTickets() {
        List<Ticket> tkts = Ticket.findWithQuery(Ticket.class, TICKET_QUERY_WHERE + " answered != corr_answer and answered != '-1'");
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
            Ticket tkt = Ticket.findWithQuery(Ticket.class, TICKET_QUERY_WHERE + " id = '" + id + "'").get(0);
            tickets.add(tkt);
        }

        if (tickets.size() > 0) {
            setCurrentTickets(tickets);
            return true;
        }
        return false;
    }
}
