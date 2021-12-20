package engine;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Bullet extends Collidable<Circle>{
    public double xPos;
    public double yPos;
    public int size=3*3;
    private double dir;
    private double speed;
    final double sqrt2DividedBy2 = Math.sqrt(2)/2;
    private int damage;

    public Bullet(double x,double y,double speed,double destX,double destY,boolean isByPlayer,int damage){
        xPos=x;
        yPos=y;
        this.speed = speed;
        shape = new Circle(size);
        shape.setCenterX(x);
        shape.setCenterY(y);
        dir  = Math.atan2(destY-y,destX-x);
        this.isByPlayer=isByPlayer;
        this.damage=damage;
        Image image;
        if (isByPlayer) {
            image = new Image(getClass().getResourceAsStream("/Attack/bullet.png"));
        }
        else {
            image= new Image(getClass().getResourceAsStream("/Attack/bullet1.png"));
        }
        shape.setFill(new ImagePattern(image));
    }

    public void move(Tile[][] map){
        double currentX = getCentre()[0];
        double currentY = getCentre()[1];
        int size = map[0][0].getSize();
        int widthDigital = map.length*size;
        int heightDigital = map[0].length*size;
        double nextXPos=currentX+speed*Math.cos(dir);
        double nextYPos=currentY+speed*Math.sin(dir);
        if (nextXPos>0 && nextYPos>0 && nextXPos<widthDigital && nextYPos<heightDigital
        && map[(int)(nextXPos/size)][(int)(nextYPos/size)].isWalkable()){
            xPos=nextXPos;
            yPos=nextYPos;
            shape.setCenterX(xPos);
            shape.setCenterY(yPos);
        }
        else{
            toBeDeleted=true;
        }

    }


    public void checkCollision(Player player, ArrayList<NPC> npcs){
        if (!isByPlayer) {
            double x = getCentre()[0] - player.getCentre()[0];
            double y = getCentre()[1] - player.getCentre()[1];
            double dis = Math.sqrt(x * x + y * y);
            if (dis < (player.size+size) / 2 && player.vincible) {
                player.getHurt(damage, xPos, yPos);
                toBeDeleted = true;
                return;
            }
        }
        if (isByPlayer){
            for (NPC npc:npcs){
                double x = getCentre()[0]-npc.getCentre()[0];
                double y = getCentre()[1]-npc.getCentre()[1];
                double dis = Math.sqrt(x*x+y*y);
                if (dis <(npc.size+size)/2){
                    npc.getHurt(damage,xPos,yPos);
                    toBeDeleted = true;
                    return;
                }
            }
        }
    }



    public double[] getCentre(){
        return new double[]{xPos,yPos};
    }
}
