package engine;

import Utils.AStar;
import Utils.Pos;
import Utils.Room;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.LinkedList;

public class Fighter extends NPC{
    private LinkedList<Pos> path= new LinkedList<>();
    private Pos goal;

    private boolean chasing=false;
    private long attackTimer=0L;

    private int damage;

    public Fighter(double x, double y, Room room,int level){
        super(16*4,x,y);
        this.damage=5+level;
        hp=50+10*level;
        hpMax=hp;
        this.shape=new Rectangle();
        speed*=0.5;
        shape.setHeight(size);
        shape.setWidth(size);
        this.room=room;
        shape.setX(x);
        shape.setY(y);
    }



    public void chase(Player player,Tile[][] map){
        if (shareRoomWithPlayer(player)){
            Double dir;
            int moveDir=0;
            dir =Math.atan2(player.getCentre()[1]-getCentre()[1],player.getCentre()[0]-getCentre()[0]);
            if (dir.compareTo(pi/8)<0 && dir.compareTo(-pi/8)>=0){
                moveDir=3;
            }
            if (dir.compareTo(pi/8)>=0 && dir.compareTo(pi*3/8)<0){
                moveDir=7;
            }
            if (dir.compareTo(pi*3/8)>=0 && dir.compareTo(pi*5/8)<0){
                moveDir=1;
            }
            if (dir.compareTo(pi*5/8)>=0 && dir.compareTo(pi*7/8)<0){
                moveDir=6;
            }
            if (dir.compareTo(pi*7/8)>=0 || dir.compareTo(-pi*7/8)<0){
                moveDir=2;
            }
            if (dir.compareTo(-pi/8)<0 && dir.compareTo(-pi*3/8)>=0){
                moveDir=5;
            }
            if (dir.compareTo(-pi*3/8)<0 && dir.compareTo(-pi*5/8)>=0){
                moveDir=0;
            }
            if (dir.compareTo(-pi*5/8)<0 && dir.compareTo(-pi*7/8)>=0){
                moveDir=4;
            }
            if (movable) {
                move(moveDir, map);
            }
        }

    }

    public void checkCollision(Player player){
        if (attackTimer==0){
            double disX = player.getCentre()[0]-getCentre()[0];
            double disY = player.getCentre()[1]-getCentre()[1];
            if (disX*disX+disY*disY<size*size/4 &&player.vincible){
                player.getHurt(damage,getCentre()[0],getCentre()[1]);
                attackTimer=48*FRAME;
            }
        }
        else{
            attackTimer-=FRAME;
        }
    }

//TODO: AStar doesn't work!!!!
//
//    public void chase(Player player,Tile[][] map){
//        if (!chasing){
//            if (shareRoomWithPlayer(player)){
//                chasing=true;
//            }
//        }
//        else {
//            Pos selfPos = getMapPos();
//            if (!player.getMapPos().equals(goal)){
//                goal=player.getMapPos();
//                path= AStar.search(map,selfPos,goal);
//            }
//            Pos next = path.get(0);
//            next = new Pos(next.x,next.y);
//            if (next.equals(getMapPos())){
//                path.remove(0);
//            }
//            int dir=0;
//            if (next.x>selfPos.x){
//                dir=3;
//            }
//            else if(next.x<selfPos.x){
//                dir=2;
//            }
//            else if(next.y<selfPos.y){
//                dir=0;
//            }
//            else if(next.y>selfPos.y){
//                dir=1;
//            }
//
//            move(dir,map);
//        }
//    }

    public void attack(Player player, ArrayList<Collidable> bullets, Pane pane){


    }

    public void update(){
        shape.setX(xPos);
        shape.setY(yPos);
    }


}
