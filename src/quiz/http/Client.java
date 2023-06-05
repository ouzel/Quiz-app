package quiz.http;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class);

    /**
     * Connecting to the server and trying to parse a new task for the player.
     *
     * @return The Task object with the important information about each task.
     * @throws IOException When having troubles connecting to the server.
     */
    public static Task getTask() throws IOException {
        logger.info("Trying to get a task from the website");

        try {
            StringBuilder result = new StringBuilder();
            logger.debug("Trying to open URL");
            URL url = new URL("http://jservice.io/api/random?count=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            logger.debug("Client connected to the website");
            conn.setRequestMethod("GET");

            logger.debug("Reading info from the page");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            String jsonString = result.substring(1, result.length() - 1);

            return jsonStringToTask(jsonString);
        } catch (JSONException e) {
            logger.error("JSONException when parsing the page");
            logger.debug("Trying to get another task");
            return getTask();
        }
    }

    /**
     * Changing the format of the json string to the record Task.
     *
     * @param jsonString The string with extended information about the task.
     * @return Task object with only the important information about the task.
     */
    public static Task jsonStringToTask(String jsonString) {
        logger.info("Parsing json from site to the record Task");
        try {
            JSONObject obj = new JSONObject(jsonString);
            logger.debug("JSONObject was created");
            String answer = obj.getString("answer");
            String question = obj.getString("question");
            int value = obj.getInt("value");
            logger.debug("The needed values were parsed from the string");
            return new Task(question, answer, value);
        } catch (JSONException e) {
            logger.error("Some of the data was not found", e);
            throw e;
        }
    }
}
