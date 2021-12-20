package engine;

import Utils.Pos;
import Utils.RandomUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character implements Serializable {
    private static final long serialVersionUID=1L;
    public int isDodging=-1;
    public long dodgingTimer;
    public int ep =100;
    private boolean isRemoteAttack=true;
    private double bulletSpeed=400*FRAME/1000000000*4;
    private int damage=10;

    private Random random;
    private int level=1;
    private int exp=0;
    private int requiredExp=100;

    private long shootingTimer=0L;
    private long maxShootingTimer=12*FRAME;

    private long skillTimer;
    private long maxSkillTimer;

    private int hpMax;

    private long skillDuringTimer=0L;
    private long maxSkillDuringTimer=3*50*FRAME;

    private int type;

    public Player(double x,double y,int type,Random random){
        super(16*3,x,y);
        hpMax=100;
        hp=hpMax;
        speed*=1.5;
        shape=new Rectangle();
        Image image=null;
        if (type==0) {
            image = new Image(getClass().getResourceAsStream("/Characters/circle.png"));
            maxSkillTimer=10*50*FRAME;
        }
        else if(type==1){
            image = new Image(getClass().getResourceAsStream("/Characters/rec.png"));
            maxSkillTimer=5*50*FRAME;
        }
        shape.setFill(new ImagePattern(image));
        shape.setWidth(size);
        shape.setHeight(size);
        shape.setX(xPos);
        shape.setY(yPos);
        this.random=random;
        this.type=type;
    }

    public void refresh(){
        if (ep<100){
            ep+=1;
        }
    }

    public void dodge(Tile[][] map){
        if (isDodging>=0 && dodgingTimer>=FRAME){
            move(isDodging,map,1.5);
            dodgingTimer-=FRAME;
        }
        if (dodgingTimer<=0){
            isDodging=-1;
        }
    }

    public void attack(double xPos, double yPos, ArrayList<Collidable> collidables, Pane pane){
        if (!attackable ){
            return;
        }
        if (shootingTimer>0){
            return;
        }
        if (isRemoteAttack){
            shootingTimer=maxShootingTimer;
            Bullet bullet = new Bullet(getCentre()[0],getCentre()[1],bulletSpeed,xPos,yPos,true,damage);
            collidables.add(bullet);
            pane.getChildren().add(bullet.shape);
        }
    }

    public void checkShootingTimer(){
        if (shootingTimer>0){
            shootingTimer-=FRAME;
        }
        if (shootingTimer<0){
            shootingTimer=0;
        }
    }

    public void expUp(int x){
        int nextExp = exp+x;
        if (nextExp>=requiredExp){
            level+=1;
            exp=nextExp-requiredExp;
            requiredExp+=level;
            hpMax+=5;
            damage+=1;
            maxSkillTimer*=0.99;
            maxShootingTimer*=0.99;
            int rand = RandomUtils.uniform(random,4);
            switch (rand){

                case 0:hpMax+=50;
                break;
                case 1:damage+=5;
                break;
                case 2:maxSkillTimer*=0.98;
                break;
                case 3:maxShootingTimer*=0.98;
                break;
            }
            hp=hpMax;
        }
        else{
            exp=nextExp;
        }
    }

    public void heal(int healing){
        int nextHp = healing+hp;
        hp=nextHp>hpMax? hpMax:nextHp;
    }

    public void update(){
        shape.setX(xPos);
        shape.setY(yPos);
    }

    public void setPos(double xPos,double yPos){
        this.xPos=xPos;
        this.yPos=yPos;
    }

    public void skill(ArrayList<Fighter> fighters,ArrayList<Guard> guards){
        if (skillTimer>0){
            return;
        }
        if (type==0){
            speed*=2;
            maxShootingTimer/=4;
            skillDuringTimer=maxSkillDuringTimer;
        }
        if (type==1){
            for (Guard guard:guards){
                if (guard.shareRoomWithPlayer(this)){
                    guard.getHurt(3*damage,getCentre()[0],getCentre()[1]);
                }
            }
            for (Fighter fighter:fighters){
                if (fighter.shareRoomWithPlayer(this)){
                    fighter.getHurt(3*damage,getCentre()[0],getCentre()[1]);
                }
            }
        }
        skillTimer=maxSkillTimer;
    }

    private void backToNormal(){
        if (type==0){
        speed/=2;
        maxShootingTimer*=4;
        }
    }

    public void checkSkill(){
        if (skillDuringTimer>0){
            skillDuringTimer-=FRAME;
            if (skillDuringTimer<=0){
                skillDuringTimer=0;
                backToNormal();
        }

        }
        if (skillTimer>0){
            skillTimer-=FRAME;
        }
        if (skillTimer<0){
            skillTimer=0;
        }
    }

    /** return an array of {hp,hpMax,ep,exp,level,requiredExp,skillTimer/maxSkillTimer}*/
    public double[] getInfo(){
        return new double[]{hp,hpMax,ep,exp,level,requiredExp,(double)skillTimer/maxSkillTimer};
    }

    public void copyInfo(Player player){
        level=player.level;
        exp=player.exp;
        hpMax=player.hpMax;
        hp=hpMax;
        requiredExp=player.requiredExp;
        maxSkillTimer=player.maxSkillTimer;
        maxShootingTimer=player.maxShootingTimer;
        type=player.type;
    }

    public int getType(){
        return type;
    }
}
