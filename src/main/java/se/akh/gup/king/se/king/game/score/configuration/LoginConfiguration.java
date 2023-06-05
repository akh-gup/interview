package se.akh.gup.king.se.king.game.score.configuration;

import se.akh.gup.king.se.king.game.score.controller.LoginController;
import se.akh.gup.king.se.king.game.score.service.LoginService;

public class LoginConfiguration {

    private static LoginService loginService;
    private static LoginController loginController;

    /***
     * Creates login service instance.
     * @return      Login Service instance.
     */
    public static LoginService getLoginService(){

        if (null == loginService){
            loginService = new LoginService(SessionConfiguration.getSessionService());
        }
        return loginService;
    }


    /***
     * Created Login Controller instance
     * @return      Login Controller
     */
    public static LoginController getLoginController(){
        if (null == loginController){
            loginController = new LoginController(getLoginService());
        }
        return loginController;
    }
}
