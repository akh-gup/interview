package king.se.king.game.score.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import se.akh.gup.king.se.king.game.score.model.SessionInfo;
import se.akh.gup.king.se.king.game.score.model.User;
import se.akh.gup.king.se.king.game.score.service.LoginService;
import se.akh.gup.king.se.king.game.score.service.ScoreService;
import se.akh.gup.king.se.king.game.score.service.SessionService;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginService.class, ScoreService.class,ConcurrentHashMap.class})
public class ScoreServiceTest {

    ScoreService scoreService;
    SessionService sessionService;

    @BeforeEach
    public void setUp() {

        sessionService = new SessionService(10, 10, 8, 10);
        scoreService = new ScoreService(sessionService,15, 5);
    }

    @Test
    public void postScoreTest(){

        SessionInfo sessionInfo = new SessionInfo("ABCDEFGH", Instant.now(), new User("12345"));

        sessionService.sessionInfoMap.put("ABCDEFGH", sessionInfo);

        scoreService.postScore("1", "ABCDEFGH", 1000);

        Assertions.assertNotNull(scoreService.userScoresPerLevelMap.get("1"));
        Assertions.assertNotNull(scoreService.userScoresPerLevelMap.get("1").get("12345"));
        Assertions.assertNotNull(scoreService.userScoresPerLevelMap.get("1").get("12345").first());
        Assertions.assertEquals(1000.0, scoreService.userScoresPerLevelMap.get("1").get("12345").first().getScore());
        Assertions.assertEquals("12345", scoreService.userScoresPerLevelMap.get("1").get("12345").first().getUser().getUserId());
    }

    @Test
    public void postScoreInvalidSessionTest(){

        SessionInfo sessionInfo = new SessionInfo("ABCDEFGH", Instant.now().minusSeconds(60 * 20), new User("12345"));

        sessionService.sessionInfoMap.put("ABCDEFGH", sessionInfo);

        scoreService.postScore("2", "ABCDEFGH", 1000);

        Assertions.assertNull(scoreService.userScoresPerLevelMap.get("2"));
    }

    @Test
    public void getHighScoreTest(){

        SessionInfo sessionInfo = new SessionInfo("ABCDEFGH", Instant.now(), new User("12345"));
        sessionService.sessionInfoMap.put("ABCDEFGH", sessionInfo);

        scoreService.postScore("3", "ABCDEFGH", 1000);

        Assertions.assertEquals("12345=1000", scoreService.getHighScoreList("3"));
    }

    @Test
    public void getHighScoreLimitTest(){

        configureSessions();

        scoreService.postScore("3", "ABCDEFGH", 1);
        scoreService.postScore("3", "ABCDEFGI", 10);
        scoreService.postScore("3", "ABCDEFGJ", 100);
        scoreService.postScore("3", "ABCDEFGK", 1000);
        scoreService.postScore("3", "ABCDEFGL", 10000);
        scoreService.postScore("3", "ABCDEFGM", 2);
        scoreService.postScore("3", "ABCDEFGN", 20);
        scoreService.postScore("3", "ABCDEFGO", 200);
        scoreService.postScore("3", "ABCDEFGP", 2000);
        scoreService.postScore("3", "ABCDEFGQ", 20000);
        scoreService.postScore("3", "ABCDEFGR", 3);
        scoreService.postScore("3", "ABCDEFGS", 30);
        scoreService.postScore("3", "ABCDEFGT", 300);
        scoreService.postScore("3", "ABCDEFGU", 3000);
        scoreService.postScore("3", "ABCDEFGV", 30000);
        scoreService.postScore("3", "ABCDEFGW", 4);

        scoreService.postScore("3", "ABCDEFGW", 40000);

        String expectedList = "12305=40000,12395=30000,12344=20000,12349=10000,12385=3000,12343=2000,12348=1000,12375=300,12342=200,12347=100,12365=30,12341=20,12346=10,12355=3,12340=2";
        Assertions.assertEquals(expectedList, scoreService.getHighScoreList("3"));
    }

    public void configureSessions(){

        SessionInfo sessionInfo = new SessionInfo("ABCDEFGH", Instant.now(), new User("12345"));
        SessionInfo sessionInfo1 = new SessionInfo("ABCDEFGI", Instant.now(), new User("12346"));
        SessionInfo sessionInfo2 = new SessionInfo("ABCDEFGJ", Instant.now(), new User("12347"));
        SessionInfo sessionInfo3 = new SessionInfo("ABCDEFGK", Instant.now(), new User("12348"));
        SessionInfo sessionInfo4 = new SessionInfo("ABCDEFGL", Instant.now(), new User("12349"));
        SessionInfo sessionInfo5 = new SessionInfo("ABCDEFGM", Instant.now(), new User("12340"));
        SessionInfo sessionInfo6 = new SessionInfo("ABCDEFGN", Instant.now(), new User("12341"));
        SessionInfo sessionInfo7 = new SessionInfo("ABCDEFGO", Instant.now(), new User("12342"));
        SessionInfo sessionInfo8 = new SessionInfo("ABCDEFGP", Instant.now(), new User("12343"));
        SessionInfo sessionInfo9 = new SessionInfo("ABCDEFGQ", Instant.now(), new User("12344"));
        SessionInfo sessionInfo10 = new SessionInfo("ABCDEFGR", Instant.now(), new User("12355"));
        SessionInfo sessionInfo11 = new SessionInfo("ABCDEFGS", Instant.now(), new User("12365"));
        SessionInfo sessionInfo12 = new SessionInfo("ABCDEFGT", Instant.now(), new User("12375"));
        SessionInfo sessionInfo13 = new SessionInfo("ABCDEFGU", Instant.now(), new User("12385"));
        SessionInfo sessionInfo14 = new SessionInfo("ABCDEFGV", Instant.now(), new User("12395"));
        SessionInfo sessionInfo15 = new SessionInfo("ABCDEFGW", Instant.now(), new User("12305"));

        sessionService.sessionInfoMap.put("ABCDEFGH", sessionInfo);
        sessionService.sessionInfoMap.put("ABCDEFGI", sessionInfo1);
        sessionService.sessionInfoMap.put("ABCDEFGJ", sessionInfo2);
        sessionService.sessionInfoMap.put("ABCDEFGK", sessionInfo3);
        sessionService.sessionInfoMap.put("ABCDEFGL", sessionInfo4);
        sessionService.sessionInfoMap.put("ABCDEFGM", sessionInfo5);
        sessionService.sessionInfoMap.put("ABCDEFGN", sessionInfo6);
        sessionService.sessionInfoMap.put("ABCDEFGO", sessionInfo7);
        sessionService.sessionInfoMap.put("ABCDEFGP", sessionInfo8);
        sessionService.sessionInfoMap.put("ABCDEFGQ", sessionInfo9);
        sessionService.sessionInfoMap.put("ABCDEFGR", sessionInfo10);
        sessionService.sessionInfoMap.put("ABCDEFGS", sessionInfo11);
        sessionService.sessionInfoMap.put("ABCDEFGT", sessionInfo12);
        sessionService.sessionInfoMap.put("ABCDEFGU", sessionInfo13);
        sessionService.sessionInfoMap.put("ABCDEFGV", sessionInfo14);
        sessionService.sessionInfoMap.put("ABCDEFGW", sessionInfo15);
    }
//    @Test
//    public void postScores_InsertsUserScore_WhenInvoked()
//    {
//        LoginService loginServiceMock = mock(LoginService.class);
//        SessionInfo sessionInfo = new SessionInfo("EXAMPLEE", Instant.now(), new User("kartik"));
//        when(loginServiceMock.getSessionInfo("EXAMPLEE")).thenReturn(sessionInfo);
//        when(loginServiceMock.isSessionValid(sessionInfo)).thenReturn(true);
//        Whitebox.setInternalState(scoreService, "loginService", loginServiceMock);
//        scoreService.postScore("2", "EXAMPLEE",1000);
//
//        Map<String, ConcurrentHashMap<String, Integer>> userHighestScorePerLevel = Whitebox.getInternalState(scoreService, "userHighestScorePerLevel");
//        int score = userHighestScorePerLevel.get("2").get("kartik");
//        assertEquals(1000, score);
//
//        Map<String, SortedSet<UserScore>> levelHighScoresMap = Whitebox.getInternalState(scoreService, "levelHighScoresMap");
//        assertEquals(new UserScore(new User("kartik"),1000),levelHighScoresMap.get("2").first() );
//    }
//
//    @Test
//    public void postScores_DoesNotInsertUserScore__WhenHigherScoreExists()
//    {
//        LoginService loginServiceMock = mock(LoginService.class);
//        SessionInfo sessionInfo = new SessionInfo("EXAMPLEE", Instant.now(), new User("kartik"));
//        Map<String, ConcurrentHashMap<String, Integer>> userHighestScorePerLevel = new ConcurrentHashMap<>();
//        userHighestScorePerLevel.put("2", new ConcurrentHashMap<>());
//        userHighestScorePerLevel.get("2").put("kartik",6000);
//        Whitebox.setInternalState(scoreService,"userHighestScorePerLevel",userHighestScorePerLevel);
//        when(loginServiceMock.getSessionInfo("EXAMPLEE")).thenReturn(sessionInfo);
//        when(loginServiceMock.isSessionValid(sessionInfo)).thenReturn(true);
//        Whitebox.setInternalState(scoreService, "loginService", loginServiceMock);
//        when(loginServiceMock.isSessionValid(sessionInfo)).thenReturn(true);
//
//        scoreService.postScore("2", "EXAMPLEE",1000);
//        userHighestScorePerLevel = Whitebox.getInternalState(scoreService, "userHighestScorePerLevel");
//        int score = userHighestScorePerLevel.get("2").get("kartik");
//        assertEquals(6000, score);
//    }
//
//    @Test
//    public void getHighScoreList_ReturnsSortedScores_ForLevelWithScores()
//    {
//        SortedSet<UserScore> userScores = Collections.synchronizedSortedSet(new TreeSet<>());
//        userScores.add(new UserScore(new User("1"), 1000));
//        userScores.add(new UserScore(new User("2"), 2000));
//        userScores.add(new UserScore(new User("3"), 2000));
//        userScores.add(new UserScore(new User("3"), 3000));
//
//        Map<String, SortedSet<UserScore>> levelHighScoresMap = new ConcurrentHashMap<>();
//        levelHighScoresMap.put("2", userScores);
//
//        Whitebox.setInternalState(scoreService, "levelHighScoresMap", levelHighScoresMap);
//        String scores = scoreService.getHighScoreList("2");
//        assertEquals("3=3000,2=2000,1=1000", scores);
//    }
//
//
//
//    @Test
//    public void getHighScoreList_ReturnsBlankString_ForLevelWithNoScores()
//    {
//        assertEquals("", scoreService.getHighScoreList(""));
//    }
}
