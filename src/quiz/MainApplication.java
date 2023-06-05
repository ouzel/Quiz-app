package quiz;

import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class MainApplication {
    static Scanner scanner;

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        scanner = new Scanner(System.in);

        System.out.println("Print /start to start the round");
        String line = scanner.nextLine();
        while (!line.trim().equalsIgnoreCase("/start")) {
            System.out.println("Print /start to start the round");
            line = scanner.nextLine();
        }
        Round round = new Round();
        round.start();
    }
}