package se.akh.gup.king.se.king.game.score.controller;

import se.akh.gup.king.se.king.game.score.service.ScoreService;

public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService){
        this.scoreService = scoreService;
    }


    /***
     *
     * Request API path: POST /{{levelId}}/score?sessionkey={{sessionId}} (Body: {{score}})
     * @param levelId       Level identifier
     * @param sessionId     Session identifier
     * @param score         Score to be saved
     *
     */
    public void handleScoreUpdate(String levelId, String sessionId, int score) {
        scoreService.postScore(levelId, sessionId, score);
    }


    /***
     *
     * Request API Path: /{{levelId}}/highscorelist
     * @param levelId       Level identifier
     *
     */
    public String handleHighScore(String levelId) {
        return scoreService.getHighScoreList(levelId);
    }
}
