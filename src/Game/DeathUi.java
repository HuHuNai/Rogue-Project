package Game;

import Utils.MyButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DeathUi {
    Pane pane;
    MyButton tryAgain;
    MyButton exit;


    public DeathUi(){
        pane=new Pane();
        pane.setPrefSize(1080,720);
        pane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("/Background/Hell.png")),null,null,null,null)));
        Image image = new Image(getClass().getResourceAsStream("/Background/Death.png"));
        Rectangle rectangle=new Rectangle(512,512);
        rectangle.setX(540-512/2);
        rectangle.setY(-50);
        rectangle.setFill(new ImagePattern(image));
        tryAgain=new MyButton("Retry");
        tryAgain.setLayoutX(540-95);
        tryAgain.setLayoutY(450);
        exit=new MyButton("Exit");
        exit.setLayoutX(540-95);
        exit.setLayoutY(550);
        pane.getChildren().addAll(tryAgain,exit,rectangle);
    }
}
