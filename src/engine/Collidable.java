package engine;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public abstract class  Collidable <T>{
    public T shape;
    public boolean isByPlayer;
    public boolean toBeDeleted = false;
    abstract double[] getCentre();
    abstract public void checkCollision(Player player, ArrayList<NPC> npcs);


    abstract public void move(Tile[][] map);
//    TODO:void collision(Collidable collidable1);
}
