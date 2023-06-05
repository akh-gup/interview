package se.akh.gup.king.se.king.game.score.controller;

import se.akh.gup.king.se.king.game.score.service.LoginService;

public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }


    /***
     *
     * Login controller exposes API endpoint.
     *
     * Sample URL: GET http://localhost:8081/4711/login --> UICSNDK
     */
    public String handleLoginOperation(String userId){

        return loginService.doLogin(userId);
    }
}
