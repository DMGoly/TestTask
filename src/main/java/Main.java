import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Main class
 *
 * @author Dmitry Golyshkin (dmgoly@gmail.com)
 * @version $0.1$
 * @since 18.04.2019
 */



public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        int places;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите количество мест на стоянке");

        String numberOfPlaces;

        do {
            numberOfPlaces = reader.readLine();
        } while (!Parking.isDigit(numberOfPlaces));

        places = Integer.parseInt(numberOfPlaces);
        Parking parking = new Parking(places);

        do {
            System.out.println("Введите команду");
            System.out.println();
            System.out.println("p:N - припарковать машину, где N - количество машин на въезд");
            System.out.println("u:N - выехать с парковки, где N - номер парковочного билета");
            System.out.println("u:[1..n] - чтобы выехать с парковки нескольким машинам, где в квадратных скобках, через запятую передаются номера парковочных билетов");
            System.out.println("l - список машин, находящихся на парковке. Для каждой машины выводится ее порядковый номер и номер билета");
            System.out.println("c - количество оставшихся мест на парковке");
            System.out.println("e - выход из приложения");

            String answer = reader.readLine();


            if (answer.startsWith("p:")) {
                answer = answer.replaceAll(" ", "");
                String[] token = answer.split(":");
                try {
                    parking.park(Integer.parseInt(token[1]));
                }catch (NumberFormatException e){
                    System.out.println("### Неверный формат ввода ###");
                }


            } else if (answer.startsWith("u")) {
                if (answer.contains("[")) {
                    String rep0 = answer.replaceAll(" ", "");
                    String rep1 = rep0.replaceAll("u:\\[", "");
                    String rep2 = rep1.replaceAll("\\]", "");
                    String[] tokens = rep2.split(",");
                    Integer[] ticketsToUnpark = new Integer[tokens.length];
                    for (int i = 0; i < tokens.length; i++) {
                        if (!Parking.isDigit(tokens[i])) {
                            break;
                        }
                        ticketsToUnpark[i] = Integer.parseInt(tokens[i]);
                    }
                    parking.unpark(ticketsToUnpark);
                } else {
                    String rep0 = answer.replaceAll(" ", "");
                    String[] tokens = rep0.split(":");
                    if (Parking.isDigit(tokens[1])) {
                        parking.unpark(Integer.parseInt(tokens[1]));
                    } else {
                        System.out.println("Введите действительный номер билета");
                    }
                }

            } else if (answer.startsWith("l")) {
                parking.list();

            } else if (answer.startsWith("c")) {
                parking.count();

            } else if (answer.startsWith("e")) {
                parking.exit();
            }

            System.out.println();
        } while (true);
    }
}
