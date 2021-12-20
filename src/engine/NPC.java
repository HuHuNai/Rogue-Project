package engine;

import Utils.Room;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

abstract public class NPC extends Character{
    Room room;

    public NPC(int size,double x, double y){
        super(size,x,y);
    }

    abstract public void attack(Player player, ArrayList<Collidable> collidable, Pane pane);

    abstract public void update();

    public boolean shareRoomWithPlayer(Player player){
        return room.isPlayerIn(player);
    }

}
