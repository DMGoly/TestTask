
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int id;
    private static Random random;
    private static Semaphore semaphore;
    private String carId;
    private Ticket ticket;
    private String isParked = "no";

    static {
        id = 10;
        semaphore = new Semaphore(2);
        random = new Random();

    }

    /**
     * @param ticket
     */
    Car(Ticket ticket) {
        id++;
        this.carId = "№ " + id;
        this.ticket = ticket;
    }

    /**
     * @return ticket
     */
    Ticket getTicket() {
        return ticket;
    }

    void info() {
        System.out.println("Автомобиль " + this.carId + ", Билет № " + this.ticket.getTicketId());
    }

    private void park() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Автомобиль " + this.carId + " паркуется...");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.isParked = "yes";
        System.out.println("Автомобиль " + this.carId + " припаркован");
        semaphore.release();
    }

    void unpark() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Автомобиль " + this.carId + " пытается покинуть парковочное место");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.isParked = "no";
        System.out.println("Автомобиль " + this.carId + " покинул парковку");
        semaphore.release();
    }


    @Override
    public void run() {
        switch (isParked) {
            case ("yes"):
                unpark();
                this.isParked = "no";
                break;
            default:
                park();
                this.isParked = "yes";
                break;
        }
    }


}