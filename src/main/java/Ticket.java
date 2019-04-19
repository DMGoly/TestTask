public class Ticket {
        private static int id;
        private int ticketId;

        static {
            id = 0;
        }

        public Ticket() {
            id++;
            this.ticketId = id;
        }

        public int getTicketId() {
            return ticketId;
        }


    }

