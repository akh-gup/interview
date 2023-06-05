package se.akh.gup.king.se.king.game.score.configuration;

import se.akh.gup.king.se.king.game.score.controller.ScoreController;
import se.akh.gup.king.se.king.game.score.service.ScoreService;
import se.akh.gup.king.se.king.game.score.util.PropertiesReader;

public class ScoreConfiguration {

    static ScoreService scoreService;
    static ScoreController scoreController;


    /***
     * Creates Score service instance
     * @return          Score Service instance.
     */
    public static ScoreService getScoreService(){
        if (null == scoreService){
            scoreService = new ScoreService(SessionConfiguration.getSessionService(), PropertiesReader.priorityQueueCapacity, PropertiesReader.maxScoresPerUser);
        }
        return scoreService;
    }


    /***
     * Create Score Controller instance.
     * @return          Score Controller.
     */
    public static ScoreController getScoreController(){
        if (null == scoreController){
            scoreController = new ScoreController(getScoreService());
        }
        return scoreController;
    }
}
