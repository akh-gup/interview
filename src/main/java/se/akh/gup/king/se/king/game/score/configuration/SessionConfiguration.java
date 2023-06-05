package se.akh.gup.king.se.king.game.score.configuration;

import se.akh.gup.king.se.king.game.score.service.SessionService;
import se.akh.gup.king.se.king.game.score.util.PropertiesReader;

public class SessionConfiguration {

    static SessionService sessionService;

    /***
     *
     * Create Session Service instance
     *
     * @return          Session Service instance.
     */
    public static SessionService getSessionService(){

        if (null == sessionService){

            sessionService = new SessionService(
                                                PropertiesReader.initialDelay,
                                                PropertiesReader.delay,
                                                PropertiesReader.sessionIdLength,
                                                PropertiesReader.sessionTimeOutMins);
        }
        return sessionService;
    }
}
