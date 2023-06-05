package se.akh.gup.king.se.king.game.score.handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseHandler {


    /***
     *
     * Send API response.
     * @param httpExchange      Http Exchange instance to send response headers.
     * @param resCode           Response code
     * @param resMessage        Response Message
     */
    public static void sendResponse(HttpExchange httpExchange, int resCode, String resMessage){

        OutputStream output = null;

        try {

            httpExchange.sendResponseHeaders(resCode, resMessage.getBytes().length);

            if (!"".equals(resMessage)) {

                output = httpExchange.getResponseBody();
                output.write(resMessage.getBytes());
            }

        } catch (IOException ex) {

            System.out.println("Failed to send response. Exception: " + ex);

        } finally {

            if (null != output){
                try {
                    output.flush();
                } catch (IOException ex){
                    System.out.println("Failed to close output stream. Exception: " + ex);
                }
            }
            httpExchange.close();
        }
    }
}
