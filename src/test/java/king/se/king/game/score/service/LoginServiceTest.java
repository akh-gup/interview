package king.se.king.game.score.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import se.akh.gup.king.se.king.game.score.service.LoginService;
import se.akh.gup.king.se.king.game.score.service.SessionService;

import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoginService.class)
public class LoginServiceTest {

    private LoginService service;
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        sessionService = new SessionService(11, 11, 8, 10);
        service = new LoginService(sessionService);
    }

    @Test
    public void doLoginTest() {

        String sessionId = service.doLogin("abc");

        Map sessionInfoMap = Whitebox.getInternalState(sessionService, "sessionInfoMap");
        Assertions.assertEquals(8, sessionId.length());
        Assertions.assertEquals(1, sessionInfoMap.size());
    }


    @Test
    public void doLogin_DuplicateUserId() {

        String sessionId = service.doLogin("abc");

        Map sessionInfoMap = Whitebox.getInternalState(sessionService, "sessionInfoMap");
        Assertions.assertEquals(8, sessionId.length());
        Assertions.assertEquals(1, sessionInfoMap.size());

        String sessionId2 = service.doLogin("def");

        Map sessionInfoMap2 = Whitebox.getInternalState(sessionService, "sessionInfoMap");
        Assertions.assertEquals(8, sessionId2.length());
        Assertions.assertEquals(2, sessionInfoMap2.size());

        sessionId = service.doLogin("abc");

        Map sessionInfoMap3 = Whitebox.getInternalState(sessionService, "sessionInfoMap");
        Assertions.assertEquals(8, sessionId.length());
        Assertions.assertEquals(2, sessionInfoMap3.size());
    }
}
