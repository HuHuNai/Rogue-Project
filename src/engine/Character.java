package engine;

import Utils.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Character {
//   digital position of the character
    public double xPos;
    public double yPos;
    public double size;
    public double speed;
    public Rectangle shape;
    public ArrayList<Shape> components = new ArrayList<>();
    final double sqrt2DividedBy2 = Math.sqrt(2)/2;
    public final long FRAME =1000000000/50;

    public boolean movable=true;
    private long movableTimer=0;

    private boolean gettingHurt=false;
    private long gettingHurtTimer=0;
    private int hurtDir;

     int hpMax=100;
     int hp;

    public boolean attackable=true;
    public long attackableTimer=0L;

    public boolean vincible=true;
    public long vincibleTimer;

    public boolean toBeDeleted;

    final double pi = Math.PI;

    public Character(){}

    public Character(double size,double xPos,double yPos){
        this.size=size;
        speed=(72*3-size)*((double)FRAME/1000000000*2);
        this.xPos=xPos;
        this.yPos=yPos;
        hp=hpMax;
    }


    public void checkDead(){
        toBeDeleted=(hp<=0);
    }




    public void move( int dir,Tile[][] map,double speedTime){
        double currentX = getCentre()[0];
        double currentY = getCentre()[1];
        int size = map[0][0].getSize();
        int widthDigital = map.length*size;
        int heightDigital = map[0].length*size;
        double nextXPos;
        double nextYPos;
        double speed=this.speed*speedTime;
        switch (dir){
            case 0:
                nextYPos=currentY-speed;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                break;
            case 1:
                nextYPos=currentY+speed;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                break;
            case 2:
                nextXPos=currentX-speed;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 3:
                nextXPos=currentX+speed;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 4:
                nextYPos=currentY-speed*sqrt2DividedBy2;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX-speed*sqrt2DividedBy2;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 5:
                nextYPos=currentY-speed*sqrt2DividedBy2;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX+speed*sqrt2DividedBy2;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 6:
                nextYPos=currentY+speed*sqrt2DividedBy2;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX-speed*sqrt2DividedBy2;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 7:
                nextYPos=currentY+speed*sqrt2DividedBy2;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX+speed*sqrt2DividedBy2;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
        }
    }





    public void move( int dir,Tile[][] map){
        double currentX = getCentre()[0];
        double currentY = getCentre()[1];
        int size = map[0][0].getSize();
        int widthDigital = map.length*size;
        int heightDigital = map[0].length*size;
        double nextXPos;
        double nextYPos;
        switch (dir){
            case 0:
                nextYPos=currentY-speed;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                break;
            case 1:
                nextYPos=currentY+speed;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                break;
            case 2:
                nextXPos=currentX-speed;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 3:
                nextXPos=currentX+speed;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 4:
                nextYPos=currentY-speed*sqrt2DividedBy2;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX-speed*sqrt2DividedBy2;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 5:
                nextYPos=currentY-speed*sqrt2DividedBy2;
                if (nextYPos>0 && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX+speed*sqrt2DividedBy2;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 6:
                nextYPos=currentY+speed*sqrt2DividedBy2;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX-speed*sqrt2DividedBy2;
                if (nextXPos>0 && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
                break;
            case 7:
                nextYPos=currentY+speed*sqrt2DividedBy2;
                if (nextYPos<heightDigital && map[(int)(currentX/size)][(int)(nextYPos/size)].isWalkable()){
                    yPos=nextYPos-this.size/2;
                }
                nextXPos=currentX+speed*sqrt2DividedBy2;
                if (nextXPos<widthDigital && map[(int)(nextXPos/size)][(int)(currentY/size)].isWalkable()){
                    xPos=nextXPos-this.size/2;
                }
        }
    }

//    public void move(int dir,Tile[][] map){
//        /* directions:
//            0:up
//            1:down
//            2:left
//            3:right
//            4:top-left
//            5:top-right
//            6:bottom-left
//            7:bottom-right
//         */
//        int size = map[0][0].getSize();
//        int widthDigital = map.length*size;
//        int heightDigital = map[0].length*size;
//        double nextXPos;
//        double nextYPos;
//        switch (dir){
//            case 0:
//                nextYPos = yPos-speed;
//                if (nextYPos>=0 && map[(int)Math.ceil(xPos/size)][(int)(nextYPos/size)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                return;
//            case 1:
//                nextYPos = yPos+speed;
//                if (nextYPos<=heightDigital && map[(int)Math.ceil(xPos/size)][(int)((nextYPos+16)/size+0.1)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                return;
//            case 2:
//                nextXPos = xPos-speed;
//                if (nextXPos>=0 && map[(int)(nextXPos/size)][(int)(yPos/size)].isWalkable()){
//                    xPos = nextXPos;
//                }
//                return;
//            case 3:
//                nextXPos = xPos+speed;
//                if (nextXPos<=widthDigital && map[(int)((nextXPos+16)/size+0.1)][(int)(yPos/size)].isWalkable()){
//                    xPos = nextXPos;
//                }
//                return;
//            case 4:
//                nextXPos = xPos-sqrt2DividedBy2*speed;
//                nextYPos = yPos-sqrt2DividedBy2*speed;
//                if (nextYPos>=0 && map[(int)(xPos/size)][(int)(nextYPos/size)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                if (nextXPos>=0 && map[(int)(nextXPos/size)][(int)(yPos/size)].isWalkable()){
//                    xPos=nextXPos;
//                }
//
//                return;
//            case 5:
//                nextXPos = xPos + sqrt2DividedBy2*speed;
//                nextYPos = yPos - sqrt2DividedBy2*speed;
//                if (nextXPos<=widthDigital && map[(int)((nextXPos+16)/size+0.1)][(int)nextYPos/size].isWalkable()){
//                    xPos=nextXPos;
//                }
//                if (nextYPos>=0 && map[(int)(xPos/size)][(int)(nextYPos/size)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                return;
//            case 6:
//                nextXPos = xPos-sqrt2DividedBy2*speed;
//                nextYPos = yPos+sqrt2DividedBy2*speed;
//                if (nextXPos>=0 && map[(int)(nextXPos/size)][(int)(yPos/size)].isWalkable()){
//                    xPos=nextXPos;
//                }
//                if(nextYPos<=heightDigital && map[(int)(xPos/size)][(int)((nextYPos+16)/size+0.1)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                return;
//            case 7:
//                nextXPos = xPos+sqrt2DividedBy2*speed;
//                nextYPos = yPos+sqrt2DividedBy2*speed;
//                if (nextXPos<=widthDigital && map[(int)((nextXPos+16)/size+0.1)][(int)(yPos/size)].isWalkable()){
//                    xPos=nextXPos;
//                }
//                if (nextYPos<=heightDigital && map[(int)(xPos/size)][(int)((nextYPos+16)/size+0.1)].isWalkable()){
//                    yPos=nextYPos;
//                }
//                return;
//        }
//    }
//

    public Pos getMapPos(){
        return new Pos((int)(getCentre()[0]/54),(int)(getCentre()[1]/54));
    }


    public double[] getCentre(){
        return new double[]{xPos+size/2,yPos+size/2};
    }



    public void getHurt(int damage,double xPos,double yPos){
        hp-=damage;
        if (hp<0){
            hp=0;
        }
        movableTimer=8*FRAME;
        movable=false;
        gettingHurtTimer=4*FRAME;
        gettingHurt=true;
        attackable=false;
        attackableTimer=8*FRAME;
        Double dir =Math.atan2(getCentre()[1]-yPos,getCentre()[0]-xPos);
        if (dir.compareTo(pi/8)<0 && dir.compareTo(-pi/8)>=0){
            hurtDir=3;
        }
        if (dir.compareTo(pi/8)>=0 && dir.compareTo(pi*3/8)<0){
            hurtDir=7;
        }
        if (dir.compareTo(pi*3/8)>=0 && dir.compareTo(pi*5/8)<0){
            hurtDir=1;
        }
        if (dir.compareTo(pi*5/8)>=0 && dir.compareTo(pi*7/8)<0){
            hurtDir=6;
        }
        if (dir.compareTo(pi*7/8)>=0 || dir.compareTo(-pi*7/8)<0){
            hurtDir=2;
        }
        if (dir.compareTo(-pi/8)<0 && dir.compareTo(-pi*3/8)>=0){
            hurtDir=5;
        }
        if (dir.compareTo(-pi*3/8)<0 && dir.compareTo(-pi*5/8)>=0){
            hurtDir=0;
        }
        if (dir.compareTo(-pi*5/8)<0 && dir.compareTo(-pi*7/8)>=0){
            hurtDir=4;
        }

    }

    public void checkVincible(){
        if (vincible==false && vincibleTimer>=FRAME){
            vincibleTimer-=FRAME;
        }
        else{
            vincible=true;
            vincibleTimer=0;
        }
    }

    public void checkMovable(){
        if (!movable && movableTimer>FRAME){
            movableTimer-=FRAME;
        }
        else{
            movable=true;
            movableTimer=0;
        }
    }

    public void checkAttackable(){
        if (!attackable && attackableTimer>FRAME){
            attackableTimer-=FRAME;
        }
        else{
            attackable=true;
            attackableTimer=0;
        }
    }

    public void checkGettingHurt(Tile[][] map){
        if (gettingHurt && gettingHurtTimer>FRAME){
            gettingHurtTimer-=FRAME;
            move(hurtDir,map,2);
        }
        else{
            gettingHurt=false;
            gettingHurtTimer=0;
        }
    }

    public Shape getShape(){
        return (Shape) shape;
    }
}
