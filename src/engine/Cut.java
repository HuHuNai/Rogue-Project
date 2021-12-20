package engine;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Cut extends Collidable<Rectangle>{
    public double xPos;
    public double yPos;
    public int size=16;
    private double dir;
    private int damage;
    private Rotate innerRotate=new Rotate();
    private Rotate outerRotate=new Rotate();
    private final double pi = Math.PI;

    public Cut(double x,double y,double destX,double destY,boolean isByPlayer,int damage){
        xPos=x;
        yPos=y;
        shape = new Rectangle();
        shape.setWidth(size);
        shape.setHeight(size);
        dir=Math.atan2(destY-yPos,destX-xPos);
        shape.setX(xPos);
        shape.setY(yPos);
        this.isByPlayer=isByPlayer;
        this.damage=damage;
        innerRotate.setAngle(45);
        innerRotate.setPivotX(getCentre()[0]);
        innerRotate.setPivotY(getCentre()[1]);
        outerRotate.setAngle(dir/pi*180-7.5);
        outerRotate.setPivotX(x);
        outerRotate.setPivotY(y);
        shape.getTransforms().addAll(innerRotate,outerRotate);
        Image image = new Image("file:src/main/resources/Game/Attack/cut1.png");
        shape.setFill(new ImagePattern(image));
    }

    public double[] getCentre(){
        return new double[]{xPos+size/2,yPos+size/2};
    }

    @Override
    public void checkCollision(Player player, ArrayList<NPC> npcs) {
        if (!isByPlayer) {
            double x = getCentre()[0] - player.getCentre()[0];
            double y = getCentre()[1] - player.getCentre()[1];
            double dis = Math.sqrt(x * x + y * y);
            if (dis < (player.size+size) / 2) {
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
                System.out.println(dis);
                if (dis <(npc.size+size)/2){
                    npc.getHurt(damage,xPos,yPos);
                    toBeDeleted = true;
                    return;
                }
            }
        }
    }

    @Override
    public void move(Tile[][] map) {

    }
}
