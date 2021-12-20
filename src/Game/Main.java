package Game;

import Utils.RandomUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        Game game = new Game(System.currentTimeMillis());
//        game.startGame();
        game.startingMenu();
        Scene scene = game.getScene();
        stage.setScene(scene);
        stage.setResizable(false);
//        stage.setHeight(1080);
//        stage.setWidth(1920);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
