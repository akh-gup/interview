package se.akh.gup.king.se.king.game.score.handler;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import se.akh.gup.king.se.king.game.score.configuration.LoginConfiguration;
import se.akh.gup.king.se.king.game.score.configuration.ScoreConfiguration;
import se.akh.gup.king.se.king.game.score.controller.LoginController;
import se.akh.gup.king.se.king.game.score.controller.ScoreController;
import se.akh.gup.king.se.king.game.score.util.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

public class RequestHandler {

    LoginController loginController;
    ScoreController scoreController;

    RequestHandler(){

        loginController = LoginConfiguration.getLoginController();
        scoreController = ScoreConfiguration.getScoreController();
    }


    /***
     *
     * Register all endpoints applicable.
     * @param httpServer        Http Server instance to register endpoints.
     *
     */
    public void registerAPIs(HttpServer httpServer){

        HttpContext rootContext = httpServer.createContext("/");
        rootContext.setHandler((httpExchange) -> {

            String opType = getOperationType(httpExchange.getRequestURI().getPath());

            if (Constants.LOGIN.equals(opType)) {

                String userId = getIdentifier(httpExchange.getRequestURI().getPath());
                String payload = loginController.handleLoginOperation(userId);

                ResponseHandler.sendResponse(httpExchange, 200, payload);

            } else if (Constants.SCORE.equals(opType)) {

                int score = fetchScore(httpExchange);

                if (score == 0) {
                    ResponseHandler.sendResponse(httpExchange, 400, "Please enter a valid score.");
                    return;
                }

                String sessionId = fetchURIQuery(httpExchange.getRequestURI());
                if (sessionId == null){
                    ResponseHandler.sendResponse(httpExchange, 400, "Invalid session id.");
                    return;
                }

                scoreController.handleScoreUpdate(getIdentifier(httpExchange.getRequestURI().getPath()), sessionId, score);
                ResponseHandler.sendResponse(httpExchange, 200, "");

            } else if (Constants.HIGHSCORELIST.equals(opType)) {

                String payload = scoreController.handleHighScore(getIdentifier(httpExchange.getRequestURI().getPath()));
                ResponseHandler.sendResponse(httpExchange, 200, payload);
            }
        });
    }


    /***
     * Get operation type from uri.
     * @param uri       URI of request
     * @return          Operation type.
     *
     */
    private String getOperationType(String uri){

        String[] urlSplits =  uri.split("/");
        return urlSplits[urlSplits.length - 1];
    }


    /***
     * Fetches user id for which operation is to be performed.
     * @param uri       HTTP URI path
     * @return          User Id
     */
    private String getIdentifier(String uri){

        String[] urlSplits = uri.split("/");
        return urlSplits[urlSplits.length - 2];
    }


    /***
     *  Fetch score from request body
     * @param httpExchange          Http exchange instance
     * @return                      Score
     * @throws IOException          Exception in case unable to read request body.
     */
    private int fetchScore(HttpExchange httpExchange) throws IOException {

        try (BufferedReader requestBodyReader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()))) {

            return Integer.parseInt(requestBodyReader.readLine());

        } catch (NumberFormatException e) {

            System.out.println("Invalid score entered. Score should be valid number.");
            return 0;
        }
    }


    /**
     * Fetch Query params at the specified location.
     * @param uri                   Request URI
     * @return                      Value of query
     */
    private String fetchURIQuery(URI uri){

        if (null == uri.getQuery() || "".equals(uri.getQuery()) || uri.getQuery().trim().equals("")){
            return "";
        }
        return uri.getQuery().split("=")[1];
    }
}
