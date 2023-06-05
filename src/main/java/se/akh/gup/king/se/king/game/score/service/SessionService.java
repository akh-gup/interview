package se.akh.gup.king.se.king.game.score.service;

import se.akh.gup.king.se.king.game.score.model.SessionInfo;
import se.akh.gup.king.se.king.game.score.model.User;
import se.akh.gup.king.se.king.game.score.util.Constants;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionService {

    public final Map<String, SessionInfo> sessionInfoMap = new ConcurrentHashMap<>();

    private final int sessionIdLength;
    private final int sessionTimeOutMins;


    public SessionService(int initialDelay, int delay, int sessionIdLength, int sessionTimeOutMins){

        this.sessionIdLength = sessionIdLength;
        this.sessionTimeOutMins = sessionTimeOutMins;

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleWithFixedDelay(this::cleanup, initialDelay, delay, TimeUnit.MINUTES);
    }


    /**
     * Creates session id for shared user and store in map for configured time.
     *
     * @param user      the user object for which session is to be generated
     * @return          A random string
     */
    public SessionInfo createUserSession(User user) {

        SessionInfo sessionInfo = fetchUserSessionIfExists(user);
        if(null != sessionInfo){

            return sessionInfo;
        }

        Instant timeStamp = Instant.now();

        String sessionId = generateNewSessionId();
        sessionInfo = new SessionInfo(sessionId, timeStamp, user);

        sessionInfoMap.put(sessionInfo.getSessionId(), sessionInfo);

        return sessionInfo;
    }


    /**
     * Generates and returns a random sessionId.
     *
     * @return          A random string of default length 8.
     */
    private String generateNewSessionId() {

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sessionIdLength; i++) {

            char randomChar = (char) (Math.round(Math.random() * (Constants.Z - Constants.A)) + Constants.A);
            builder.append(randomChar);
        }
        return builder.toString();
    }


    /**
     * Validates if session is expired or not.
     *
     * @param sessionInfo       Session info object which is to be checked.
     * @return                  True if session hasn't timed Out. False otherwise.
     */
    public boolean isSessionValid(SessionInfo sessionInfo) {
        return sessionInfo != null && Duration.between(sessionInfo.getTimestamp(), Instant.now()).toMinutes() <= sessionTimeOutMins;
    }


    /**
     * Gets session info.
     *
     * @param sessionId         the session id
     * @return                  the session info
     */
    SessionInfo getSessionInfo(String sessionId) {
        return sessionInfoMap.get(sessionId);
    }


    /**
     * Clears expired sessions from map.
     */
    private void cleanup() {
        sessionInfoMap.values().forEach(sessionInfo -> {

            if (!isSessionValid(sessionInfo)) {
                sessionInfoMap.remove(sessionInfo.getSessionId());
            }
        });
    }


    /***
     * Fetch user session if already exists.
     * @param user            User information
     * @return                Session information.
     */
    private SessionInfo fetchUserSessionIfExists(User user){

        for(Map.Entry<String, SessionInfo> sessionInfoEntry: sessionInfoMap.entrySet()){

            if (user.getUserId().equals(sessionInfoEntry.getValue().getUser().getUserId())){

                return sessionInfoEntry.getValue();
            }
        }
        return null;
    }
}
