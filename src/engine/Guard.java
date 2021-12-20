package engine;

import Utils.Room;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Guard extends NPC{
    private double bulletSpeed=200*FRAME/1000000000*3;
    private double r=8;
    private Long shootingTimer = 0L;
    private int damageOfBullet;

    public Guard( double x, double y,Room room,int level){
        super(16*3,x,y);
        hp=25+5*level;
        damageOfBullet=10+level;
        hpMax=hp;
        this.shape=new Rectangle();
        shape.setHeight(size);
        shape.setWidth(size);
        this.room=room;
        shape.setX(x);
        shape.setY(y);
    }



    public void attack(Player player, ArrayList<Collidable> bullets , Pane pane){
        if (!attackable){
            return;
        }
        shootingTimer.equals(0L);
        if (shootingTimer.equals(0L)){
            if (shareRoomWithPlayer(player) ){
            Bullet bullet = new Bullet(xPos,yPos,bulletSpeed,player.getCentre()[0],player.getCentre()[1],false,damageOfBullet);
            bullets.add(bullet);
            pane.getChildren().add(bullet.shape);
            shootingTimer+=1000000000L;
            }
        }
        else {
            shootingTimer-=FRAME;
        }


    }

    public void update(){
        shape.setX(xPos);
        shape.setY(yPos);
    }
}
