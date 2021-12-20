package engine;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Tile {
    private Rectangle rec;
    private int size=(int)(18*3);
    private Image image;
    String type;
    private boolean walkable;

    public int getSize(){
        return size;
    }

    public Tile(String type){
        this.type=type;
        this.rec=new Rectangle();
        rec.setWidth(size+1);
        rec.setHeight(size+1);
        if (type.substring(0,2).equals("fl")){
            switch (this.type.charAt(5)){
//            TODO:set image and walkable
            case '1':
                image=imageForFloor(1);
                walkable=true;
                break;
            case '2':
                image=imageForFloor(2);
                walkable=true;
                break;
            case '3':
                image=imageForFloor(3);
                walkable=true;
                break;
            case '4':
                image=imageForFloor(4);
                walkable=true;
                break;
            case '5':
                image=imageForFloor(5);
                walkable=true;
                break;
            case '6':
                image=imageForFloor(6);
                walkable=true;
                break;
            case '7':
                image=imageForFloor(7);
                walkable=true;
                break;
            case '8':
                image=imageForFloor(8);
                walkable=true;
                break;
            }
        }
       else {
           switch (type){
            case "wall":
                image=new Image(this.getClass().getResourceAsStream("/Marble/tile_0003.png"));
                walkable=false;
                break;
            case "nothing":
                image=new Image(this.getClass().getResourceAsStream("/Nothing/Nothing.png"));
                walkable=false;
                break;
        }
    }
        rec.setFill(new ImagePattern(image));

    }

    private Image imageForFloor(int i){
        Image image = new Image(this.getClass().getResourceAsStream("/Floor/Floor"+i+".png"));
        return image;
    }

    public void setPos(double x,double y){
        rec.setX(x);
        rec.setY(y);
    }

    public Rectangle getRec(){
        return rec;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public Tile copy(){
        Tile tile = new Tile(type);
        return tile;
    }
}
