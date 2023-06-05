package se.akh.gup.king.se.king.game.score.service;


import se.akh.gup.king.se.king.game.score.model.SessionInfo;
import se.akh.gup.king.se.king.game.score.model.User;
import se.akh.gup.king.se.king.game.score.model.UserScore;
import se.akh.gup.king.se.king.game.score.util.LockSynchronizer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreService {

    private final SessionService sessionService;
    private final int priorityQueueCapacity;
    private final int maxScorePerUser;
    private final LockSynchronizer lockSynchronizer;

    public final Map<String, ConcurrentHashMap<String, SortedSet<UserScore>>> userScoresPerLevelMap = new ConcurrentHashMap<>();


    public ScoreService(SessionService sessionService, int priorityQueueCapacity, int maxScorePerUser) {

        this.sessionService = sessionService;
        this.priorityQueueCapacity = priorityQueueCapacity;
        this.maxScorePerUser = maxScorePerUser;

        this.lockSynchronizer = new LockSynchronizer();
    }


    /**
     * Updates score for the user.
     *
     * @param levelId   the level id
     * @param sessionId the session id of the user associated with the request
     * @param score     the score to be posted
     */
    public void postScore(String levelId, String sessionId, int score) {


        // Fetch session adn validate if it is still active
        SessionInfo sessionInfo = sessionService.getSessionInfo(sessionId);

        if (sessionService.isSessionValid(sessionInfo)) {

            // Fetch User id from session.
            String userId = sessionInfo.getUser().getUserId();

            // Add user score to the existing set.
            addUserScore(levelId, userId, score);

        } else {

            System.out.println("Session is expired. Session Id: " + sessionId + ". Re-login to add score.");
        }
    }

    /**
     *
     * Fetches High score for the specified level.
     * Fetches only highest score per user in that level.
     *
     * @param level             the level
     * @return                  the high score list mapped to string.
     */
    public String getHighScoreList(String level) {

        SortedSet<UserScore> highScoreSet = fetchHighestScorePerUser(userScoresPerLevelMap.get(level));

        return mapHighestUserScoresToString(highScoreSet);
    }


    /***
     *
     * Adds user score to the set.
     *
     * @param levelId           Level Identifier
     * @param userId            User Identifier
     * @param score             User Score to be added
     *
     */
    private void addUserScore(String levelId, String userId, Integer score){

        synchronized (lockSynchronizer.getLock(levelId)) {

            ConcurrentHashMap<String, SortedSet<UserScore>> userScoreMap = userScoresPerLevelMap.computeIfAbsent(levelId, k -> new ConcurrentHashMap<>());
            SortedSet<UserScore> userScoreSet = userScoreMap.computeIfAbsent(userId, k -> Collections.synchronizedSortedSet(new TreeSet<>()));

            userScoreSet.add(new UserScore(new User(userId), score));
        }
    }


    /***
     *
     * Fetch Highest score for each user.
     *
     * @param levelScoresMap            Scores for the specific level in which the highest score needs to be calculated.
     * @return                          Sorted set of user score in that level.
     */
    private SortedSet<UserScore> fetchHighestScorePerUser(ConcurrentHashMap<String, SortedSet<UserScore>> levelScoresMap){

        if (null == levelScoresMap || levelScoresMap.isEmpty()){
            return null;
        }

        SortedSet<UserScore> highScoreSet = Collections.synchronizedSortedSet(new TreeSet<>());

        for (Map.Entry<String, SortedSet<UserScore>> userScoreEntrySet: levelScoresMap.entrySet()){
            highScoreSet.add(userScoreEntrySet.getValue().first());
        }
        return highScoreSet;
    }


    /***
     *
     * Converts user's highest score from set to String.
     *
     * @param highScoreSet          Sorted set of user score in that level.
     * @return                      List of scores converted into string format.
     */
    private String mapHighestUserScoresToString(SortedSet<UserScore> highScoreSet){

        if (null == highScoreSet || highScoreSet.isEmpty()){
            return "";
        }

        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();

        for (UserScore userHighestScore : highScoreSet) {

            if (!stringBuilder.toString().equals("")) {
                stringBuilder.append(",");
            }

            stringBuilder
                    .append(userHighestScore.getUser().getUserId())
                    .append("=")
                    .append((int) userHighestScore.getScore());

            i++;
            if (i > priorityQueueCapacity) {
                break;
            }
        }

        return stringBuilder.toString();
    }
}
