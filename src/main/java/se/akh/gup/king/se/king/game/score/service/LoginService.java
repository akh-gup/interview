package se.akh.gup.king.se.king.game.score.service;


import se.akh.gup.king.se.king.game.score.model.User;

public class LoginService {

    private final SessionService sessionService;

    public LoginService(SessionService sessionService){
        this.sessionService = sessionService;
    }


    /**
     * Login for the user id and returns session id.
     *
     * @param userId        user id
     * @return              sessionId
     */
    public String doLogin(String userId) {

        return sessionService.createUserSession(new User(userId)).getSessionId();
    }
}
