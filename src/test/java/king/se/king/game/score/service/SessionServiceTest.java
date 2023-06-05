package king.se.king.game.score.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import se.akh.gup.king.se.king.game.score.model.SessionInfo;
import se.akh.gup.king.se.king.game.score.model.User;
import se.akh.gup.king.se.king.game.score.service.SessionService;

import java.time.Instant;

public class SessionServiceTest {

    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        sessionService = new SessionService(11, 11, 8, 10);
    }


    @Test
    public void createUserSessionTest(){
        SessionInfo sessionInfo = sessionService.createUserSession(new User("12345"));

        Assertions.assertEquals(sessionInfo.getSessionId().length(), 8);
        Assertions.assertEquals(sessionInfo.getUser().getUserId(), "12345");

        SessionInfo sessionInfo2 = sessionService.createUserSession(new User("12345"));

        Assertions.assertEquals(sessionInfo.getSessionId().length(), 8);
        Assertions.assertEquals(sessionInfo.getUser().getUserId(), "12345");
        Assertions.assertEquals(sessionInfo.getSessionId(), sessionInfo2.getSessionId());
    }


    @Test
    public void generateNewSessionTest() {
        try {

            String id = Whitebox.invokeMethod(sessionService, "generateNewSessionId");
            Assertions.assertEquals(id.length(), 8);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateNullSessionTest() {
        Assertions.assertFalse(sessionService.isSessionValid(null));
    }

    @Test
    public void timedOutSessionTest() {
        SessionInfo sessionInfo = new SessionInfo("a", Instant.now().minusSeconds(60 * 1000), new User("a"));
        Assertions.assertFalse(sessionService.isSessionValid(sessionInfo));
    }

    @Test
    public void validSessionTest() {
        SessionInfo sessionInfo = new SessionInfo("a", Instant.now().minusSeconds(300), new User("a"));
        Assertions.assertTrue(sessionService.isSessionValid(sessionInfo));
    }
}
