package Utils;

public class Pos {
    public final int x;
    public final int y;
    public Pos(int x,int y){
        this.x=x;
        this.y=y;
    }

    public boolean equals(Pos pos1){
        if (pos1==null){
            return false;
        }
        if (pos1==this){
            return true;
        }
        return (this.x==pos1.x && this.y==pos1.y);
    }

    public double getDistance(Pos pos){
        return Math.sqrt(Math.pow(this.x-pos.x,2)+Math.pow(this.y- pos.y,2));
    }
}
