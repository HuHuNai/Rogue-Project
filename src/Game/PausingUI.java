package Game;

import Utils.MyButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PausingUI extends Application {
    Pane pausingPane;
    MyButton save;
    MyButton back;
    MyButton exit;

    @Override
    public void start(Stage stage) throws Exception {
        PausingUI pausingUI = new PausingUI();
        Scene scene = new Scene(pausingUI.pausingPane);
        stage.setScene(scene);
        stage.show();
    }

    public PausingUI(){
        pausingPane = new Pane();
        pausingPane.setPrefSize(240,360);
        pausingPane.setMaxSize(240,360);
        pausingPane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("/Background/Background3.png")),null,null,null,null)));
        save = new MyButton("Save");
        save.setLayoutX(120-95);
        save.setLayoutY(120-23);
        back = new MyButton("Back");
        back.setLayoutX(120-95);
        back.setLayoutY(180-23);
        exit = new MyButton("Exit");
        exit.setLayoutX(120-95);
        exit.setLayoutY(240-23);
        pausingPane.getChildren().addAll(save,back,exit);
    }
}
