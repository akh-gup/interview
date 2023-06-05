package se.akh.gup.king.se.king.game.score;


import se.akh.gup.king.se.king.game.score.handler.HttpServerHandler;
import se.akh.gup.king.se.king.game.score.util.PropertiesReader;

public class Main {

    public static void main(String[] args){

        String propFilePath = null != args && args.length > 0 ? args[0] : "";
        PropertiesReader.loadPropertyFile(propFilePath);

        HttpServerHandler server = new HttpServerHandler();

        server.startApplication(PropertiesReader.port);
    }
}
