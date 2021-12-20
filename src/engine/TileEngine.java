package engine;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class TileEngine {
    private Tile[][] map;
    private Pane pane;
    int size;
    int width;
    int height;


    public void initialize(int x,int y,int size){
        this.size=size;
        this.width=x;
        this.height=y;
        this.pane = new Pane();
        pane.setPrefSize(width*size,height*size);
    }

    public Tile[][] getMap(){
        return map;
    }

    public void setMap(Tile[][] map){
        pane.getChildren().clear();
        this.map=map;
        int xMax = map.length;
        int yMax = map[0].length;
        for (int x=0;x<xMax;x+=1){
            for (int y=0; y<yMax; y+=1){
                Tile cTile = map[x][y].copy();
                cTile.setPos(x*(size),y*(size));
                pane.getChildren().add(cTile.getRec());
            }
        }
    }



    public Pane getPane(){
        return pane;
    }


}
