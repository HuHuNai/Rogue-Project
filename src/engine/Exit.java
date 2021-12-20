package engine;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Exit {
    Rectangle rectangle;
    double size;
    double xPos;
    double yPos;

    public Exit(double size,double xPos,double yPos){
        this.size=size;
        this.xPos=xPos;
        this.yPos=yPos;
        rectangle = new Rectangle(size,size);
        rectangle.setX(xPos);
        rectangle.setY(yPos);
        rectangle.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/Treasure.jpg"))));
    }

    public boolean checkCollision(Player player){
        double xDis =getCentre()[0]-player.getCentre()[0];
        double yDis = getCentre()[1]-player.getCentre()[1];
        return xDis*xDis+yDis*yDis<size*size;
    }

    public double[] getCentre(){
        return new double[]{xPos+size/2,yPos+size/2};
    }

    public Rectangle getShape(){
        return rectangle;
    }
}
