package Game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

public class InputListener {
    ScrollPane scrollPane;
    Pane pane;


    protected boolean isAPressed=false;
    protected boolean isWPressed=false;
    protected boolean isSPressed=false;
    protected boolean isDPressed=false;
    protected boolean isFPressed=false;
    protected boolean isSpacePressed=false;
    protected boolean isEPressed=false;
    protected boolean isEscPressed=false;

    protected boolean isLeftMousePressed;
    protected boolean isRightMousePressed;
    protected double mousePosX;
    protected double mousePosY;

    public InputListener(ScrollPane scrollPane,Pane pane){
        this.scrollPane=scrollPane;
        this.pane=pane;
        scrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A){
                    isAPressed=true;
//                    if (isDPressed){
//                        isDPressed=false;
//                    }
                }
                if (keyEvent.getCode() == KeyCode.W){
                    isWPressed=true;
//                    if (isSPressed){
//                        isSPressed=false;
//                    }
                }
                if (keyEvent.getCode() == KeyCode.S){
                    isSPressed=true;
//                    if (isWPressed){
//                        isWPressed=false;
//                    }
                }
                if (keyEvent.getCode() == KeyCode.D){
                    isDPressed=true;
//                    if (isAPressed){
//                        isAPressed=false;
//                    }
                }
                if (keyEvent.getCode() == KeyCode.SPACE){
                    isSpacePressed=true;
                }
                if (keyEvent.getCode() == KeyCode.E){
                    isEPressed=true;
                }
                if (keyEvent.getCode() == KeyCode.F){
                    isFPressed=true;
                }
                if (keyEvent.getCode() == KeyCode.ESCAPE){
                    isEscPressed=true;
                }
            }
        });

        scrollPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A){
                    isAPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.W){
                    isWPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.S){
                    isSPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.D){
                    isDPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.SPACE){
                    isSpacePressed=false;
                }
                if (keyEvent.getCode() == KeyCode.E){
                    isEPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.F){
                    isFPressed=false;
                }
                if (keyEvent.getCode() == KeyCode.ESCAPE){
                    isEscPressed=false;
                }
            }
        });


        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isLeftMousePressed = true;
                }
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    isRightMousePressed = true;
                }
            }
        });

        pane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    isLeftMousePressed = false;
                }
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    isRightMousePressed = false;
                }
            }
        });

        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mousePosX = mouseEvent.getX();
                mousePosY = mouseEvent.getY();
            }
        });

        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                    mousePosX = mouseEvent.getX();
                    mousePosY = mouseEvent.getY();

            }
        });

    }

}
