package Game;

import Utils.MyButton;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class StartingUI {
    Pane pane = new Pane();
    MyButton startGame;
    MyButton loadGame;
    MyButton exit;
    Rectangle circle;
    Rectangle rectangle;

    Image image = new Image(getClass().getResourceAsStream("/Background/Title.png"));
    Image choosingImage =new Image(getClass().getResourceAsStream("/Background/choose.png"));

    public void setBackground(){
        BackgroundImage image = new BackgroundImage(new Image(getClass().getResourceAsStream("/Background/Background1.png")),null,null,null,null);
        Background background = new Background(image);
        pane.setBackground(background);
        pane.setPrefSize(1080,720);
        startGame = new MyButton("Start");
        startGame.setLayoutX(270);
        startGame.setLayoutY(480);
        loadGame = new MyButton("Load");
        loadGame.setLayoutX(1080-270-190);
        loadGame.setLayoutY(480);
        exit = new MyButton("Exit");
        exit.setLayoutX(540-190/2);
        exit.setLayoutY(600);
        Rectangle title = new Rectangle();
        title.setHeight(384);
        title.setWidth(384);
        title.setFill(new ImagePattern(this.image));
        title.setX(1080/2-384/2);
        title.setY(0);

        circle= new Rectangle();
        circle.setWidth(128);
        circle.setHeight(128);
        circle.setX(1080/4-128/2);
        circle.setY(480);
        rectangle = new Rectangle();
        rectangle.setWidth(128);
        rectangle.setHeight(128);
        rectangle.setX(1080/4*3-128/2);
        rectangle.setY(480);
        ImagePattern imagePattern1 = new ImagePattern(new Image(getClass().getResourceAsStream("/Characters/circle.png")));
        ImagePattern imagePattern2 = new ImagePattern(new Image(getClass().getResourceAsStream("/Characters/rec.png")));
        circle.setFill(imagePattern1);
        rectangle.setFill(imagePattern2);



        pane.getChildren().addAll(startGame,loadGame,exit,title);

        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                choose();
            }
        });
    }

    public void choose(){
        Rectangle choosingTitle = new Rectangle();
        choosingTitle.setHeight(384);
        choosingTitle.setWidth(384);
        choosingTitle.setX(1080/2-384/2);
        choosingTitle.setY(0);
        choosingTitle.setFill(new ImagePattern(this.choosingImage));
        pane.getChildren().clear();
        pane.getChildren().addAll(rectangle,circle,choosingTitle);
    }
}
