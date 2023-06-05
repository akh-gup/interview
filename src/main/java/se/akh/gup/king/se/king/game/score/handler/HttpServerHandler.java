package se.akh.gup.king.se.king.game.score.handler;

import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HttpServerHandler {


    private HttpServer httpServer;


    /***
     * Initialise Http Server instance.
     * @param portNum Port number on which server will be started.
     *
     */
    private boolean initialiseHttpServer(int portNum) {

        try {

            httpServer = HttpServer.create(new InetSocketAddress(portNum), 0);
            httpServer.setExecutor(Executors.newCachedThreadPool());

            System.out.println("Server started and listening at port " + httpServer.getAddress().getPort());

        } catch (IOException ioException){

            System.out.println("Failed to start server on port " + portNum + " . Check if port is not in use/blocked. Exception: " + ioException);
            return false;
        }
        return true;
    }


    /**
     *
     * Starts the httpserver over at the given port.
     * Executes each request in a thread.
     *
     */
    public void startApplication(int portNum) {

        if (!initialiseHttpServer(portNum)) {
            return;
        }

        new RequestHandler().registerAPIs(httpServer);

        httpServer.start();
    }
}
