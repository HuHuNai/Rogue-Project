package Utils;

import engine.Guard;
import engine.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    private Pos start;
    private Pos end;
    private ArrayList<Guard> guards = new ArrayList<>();

    public Room(Pos start,Pos end){
        this.start=start;
        this.end=end;
    }

    public boolean isPlayerIn(Player player){
        Pos playerPos =player.getMapPos();
        boolean in=false;
        if (playerPos.x>=start.x && playerPos.x<=end.x &&
        playerPos.y>=start.y && playerPos.y<=end.y){
             in=true;
        }
        return in;
    }



    public Pos getStart(){
        return start;
    }

    public Pos getEnd(){
        return end;
    }

    public Pos getCenter(){
        return new Pos((start.x+end.x)/2,(start.y+end.y)/2);
    }
}
