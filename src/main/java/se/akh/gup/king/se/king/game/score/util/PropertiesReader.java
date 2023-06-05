package se.akh.gup.king.se.king.game.score.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public static int initialDelay;
    public static int delay;
    public static int sessionIdLength;
    public static int sessionTimeOutMins;
    public static int priorityQueueCapacity;
    public static int maxScoresPerUser;
    public static int port;

    /**
     * Loads property file.
     *
     * @param propFilePath the prop file path
     */
    public static void loadPropertyFile(String propFilePath) {

        Properties props = new Properties();
        try {
            props.load(new FileReader(propFilePath));

        } catch (IOException e) {
            System.out.println("Property File not found. Using default values");
        }

        initialDelay = Integer.parseInt(props.getProperty(Constants.INITIAL_DELAY, "11"));
        delay = Integer.parseInt(props.getProperty(Constants.DELAY, "11"));
        sessionIdLength = Integer.parseInt(props.getProperty(Constants.SESSION_ID_LENGTH, "8"));
        sessionTimeOutMins = Integer.parseInt(props.getProperty(Constants.SESSION_TIME_OUT_MINS, "10"));
        priorityQueueCapacity = Integer.parseInt(props.getProperty(Constants.PRIORITY_QUEUE_CAPACITY, "15"));
        maxScoresPerUser = Integer.parseInt(props.getProperty(Constants.MAX_SCORE_PER_USER, "2"));
        port = Integer.parseInt(props.getProperty(Constants.WEB_SERVER_PORT, "8081"));
    }
}
