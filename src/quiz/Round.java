package quiz;

import org.apache.log4j.Logger;
import quiz.http.Client;
import quiz.http.Task;

import java.io.IOException;
import java.util.Scanner;

public class Round {
    private final Scanner scanner;
    private int correctAnswersCount;
    private int incorrectAnswersCount;
    private long score;
    private static final Logger logger = Logger.getLogger(Round.class);

    /**
     * Constructing the round and setting the scores to zero.
     */
    public Round() {
        logger.debug("The round was constructed");
        incorrectAnswersCount = 0;
        correctAnswersCount = 0;
        score = 0;
        scanner = new Scanner(System.in);
        logger.debug("System.in is the default output");
    }

    /**
     * Starting the round by printing instructions and setting the process of Q&A.
     */
    public void start() {
        logger.info("The round has started.");
        printInstructions();
        logger.info("The instructions were printed");
        logger.debug("The Q&A session has started");
        doTask();
    }

    public void printInstructions() {
        logger.debug("Printing the instructions");
        System.out.println("This is a quiz game.");
        System.out.println("Answer questions one by one.");
        System.out.println("For each correct question your score goes up.");
        System.out.println("If your answer is incorrect, you lose points.");
        System.out.println("When you want to quit, just type in /q");
        System.out.println("The game has started.");
        System.out.println();
        System.out.println();
    }

    /**
     * Finishing the round and printing the statistics.
     */
    public void finish() {
        logger.info("Finishing the round and printing the statistics");
        System.out.println("Game over");
        System.out.println("Correct answers: " + correctAnswersCount);
        System.out.println("Incorrect answers: " + incorrectAnswersCount);
        System.out.println("Score: " + score);
    }

    /**
     * One iteration of completing the task.
     * The task is parsed from the site and the user is asked to inut the correct answer.
     */
    public void doTask() {
        logger.debug("New question");
        try {
            Task task = Client.getTask();
            logger.debug("A random question was created");
            System.out.print("Question: " + task.question() + "\nYour answer: ");
            // The case is not important when answering.
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("/q")) {
                logger.debug("The user is quitting the session");
                finish();
            } else if (input.equals(task.answer().toLowerCase())) {
                logger.debug("The user has answered correctly");
                System.out.println("Correct!");
                System.out.println("\n");
                ++correctAnswersCount;
                score += task.value();
                doTask();
            } else {
                logger.debug("The user has answered incorrectly");
                System.out.println("Incorrect!\nThe correct answer is: " + task.answer());
                System.out.println("\n");
                ++incorrectAnswersCount;
                score -= task.value();
                doTask();
            }
        } catch (IOException e) {
            finish();
            logger.error("There is a problem with the server...", e);
            System.out.println("Try again next time");
        }
    }

}
