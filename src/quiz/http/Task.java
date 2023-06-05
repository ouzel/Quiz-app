package quiz.http;

/**
 * The information about each task.
 *
 * @param question The question from the website.
 * @param answer   The correct answer (the case is the same as on the website).
 * @param value    The value, assigned by the creator.
 */
public record Task(String question, String answer, int value) {
}
