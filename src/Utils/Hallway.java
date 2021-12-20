package Utils;

import java.io.Serializable;

public class Hallway implements Serializable {
    public Pos start;
    public Pos end;
    public int direction;
//    0 for horizontal and 1 for vertical
    public Hallway(Pos start,Pos end){
        this.start=start;
        this.end=end;
        if (start.x==end.x){
            direction=1;
        }
        else {
            direction=0;
        }
    }
}
