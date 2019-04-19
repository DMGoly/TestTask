import java.util.*;

class Parking {
    private LinkedList<Ticket> tickets;
    private Vector<Car> carsAtTheParkingLot;

    Parking(int place) {
        this.tickets = new LinkedList<>();
        createTickets(place);
        this.carsAtTheParkingLot = new Vector<>();
    }

    private void createTickets(int amount) {
        int i = 0;
        while (i < amount) {
            this.tickets.add(new Ticket());
            i++;
        }
    }

    void park(int count) throws InterruptedException {
        if (tickets.isEmpty()) {
            System.out.println("Свободных мест нет");
            return;
        } else if (tickets.size() < count) {
            System.out.println("Не хватает свободных мест");
            return;
        }
        Car[] carsToPark = new Car[count];
        for (int i = 0; i < carsToPark.length; i++) {
            carsToPark[i] = new Car(tickets.remove());
        }

        Thread[] joinCars = new Thread[count];

        for (int i = 0; i < count; i++) {
            this.carsAtTheParkingLot.add(carsToPark[i]);
            joinCars[i] = new Thread(carsToPark[i]);
            joinCars[i].start();
        }

        for (Thread joinCar : joinCars) {
            joinCar.join();
        }

    }

    void unpark(int ticketId) {
        for (Car car : this.carsAtTheParkingLot) {
            if (car.getTicket().getTicketId() == ticketId) {
                car.unpark();
                this.tickets.add(car.getTicket());
                this.carsAtTheParkingLot.remove(car);
                return;
            }
        }
    }

    void unpark(Integer... tickets) throws InterruptedException {
        ArrayList<Integer> ticketsToUnpark = new ArrayList<Integer>(Arrays.asList(tickets));
        ArrayList<Car> carsToUnPark = new ArrayList<>();
        for (Car n : this.carsAtTheParkingLot) {
            if (ticketsToUnpark.contains(n.getTicket().getTicketId())) {
                carsToUnPark.add(n);
            }
        }
        Thread[] joinCars = new Thread[carsToUnPark.size()];
        for (int i = 0; i < carsToUnPark.size(); i++) {
            this.tickets.add(carsToUnPark.get(i).getTicket());
            this.carsAtTheParkingLot.remove(carsToUnPark.get(i));
            joinCars[i] = new Thread(carsToUnPark.get(i));
            joinCars[i].start();
        }

        for (Thread joinCar : joinCars) {
            joinCar.join();
        }
    }

    void list() {
        System.out.println("Количество автомобилей на стоянке");
        for (Car car : carsAtTheParkingLot) {
            car.info();
        }
    }

    void count() {
        System.out.println("Осталось свободных мест: " + this.tickets.size());
        this.tickets.size();
    }

    void exit() {
        System.out.println("Вы вышли из программы");
        System.exit(0);
    }

    static boolean isDigit(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println(String.valueOf(e) + " - Не является целым числом!");;
            System.out.println("Введите целое число");
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Ввведите корректное число");
            return false;
        }
        return true;
    }


}