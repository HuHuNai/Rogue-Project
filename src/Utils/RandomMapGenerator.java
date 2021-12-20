package Utils;

import engine.Player;
import engine.Tile;
import engine.TileSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class RandomMapGenerator {
    private ArrayList<Room> rooms = new ArrayList<>();
    private Random random;
    private final int WIDTH;
    private final int HEIGHT;
    private ArrayList<Hallway> hallways = new ArrayList<>();
    private Room centre;

    public   Room randRoom(){
        return rooms.get(0);
    }

    public RandomMapGenerator(Random random,int width,int height){
        this.random=random;
        this.WIDTH=width;
        this.HEIGHT=height;
    }

    private boolean generateRandomRoom(Tile[][] world){
        int x,y;
        Pos start;
        Pos end;
        boolean out=true;
        long timeJustNow = System.currentTimeMillis();
        do {
            long timeNow =System.currentTimeMillis();
            if ((timeNow-timeJustNow)/1000>3){
                return out;
            }
            x = RandomUtils.uniform(random, 5, WIDTH - 13);
            y = RandomUtils.uniform(random, 5, HEIGHT - 13);
            start = new Pos(x, y);
            out=true;
            end = new Pos(x + RandomUtils.uniform(random, 6, 8), y + RandomUtils.uniform(random, 6, 8));
            for (Room r:rooms){
                if (((start.x<=r.getStart().x && end.x+3>=r.getStart().x) || (end.x>=r.getEnd().x && start.x-3<=r.getEnd().x))
                        && ((start.y<=r.getStart().y && end.y+3>=r.getStart().y) || (end.y>=r.getEnd().y && start.y-3<=r.getEnd().y))){
                    out=false;
                    break;
                }
                /*out=neighbor(start,end,r);*/
            }
        }while (!out);
        rooms.add(new Room(start,end));
        for (int i=start.x;i<end.x;i+=1){
            for (int j=start.y;j<end.y;j+=1){
                world[i][j]= TileSet.randomFloor(random);
            }
        }
        return out;
    }

    private Room findCenter(Tile[][] world){
        centre=rooms.get(0);
        for (Room r:rooms){
            if (Math.abs(WIDTH/2-(r.getStart().x+r.getEnd().x)/2)+Math.abs(HEIGHT/2-(r.getStart().y+r.getEnd().y)/2)<
                    Math.abs(WIDTH/2-(centre.getStart().x+centre.getEnd().x)/2)+Math.abs(HEIGHT/2-(centre.getStart().y+centre.getEnd().y)/2)){
                centre=r;
            }
        }
        return centre;
    }

    private void setWall(Tile[][] world,int x,int y){
        for (int i=x-1;i<x+2;i+=1){
            for (int j=y-1;j<y+2;j+=1){
                if (world[i][j].equals(TileSet.Nothing)){
                    world[i][j]=TileSet.WALL;
                }
            }
        }
    }

    public void createWalls(Tile[][] world){
        for (int x=1;x<WIDTH-1;x+=1){
            for (int y=1;y<HEIGHT-1;y+=1){
                if (world[x][y].isWalkable()) {
                    setWall(world, x, y);
                }
            }
        }
    }

    private int countFloors(Tile[][] world,int x,int y){
        int count=0;
        for (int i =x-1;i<x+2;i+=1){
            for (int j =y-1;j<y+2;j+=1){
                if(world[i][j].isWalkable()){
                    count+=1;
                }
            }
        }
        return count;
    }

    private void dropWalls(Tile[][] world){
        for (int x=1;x<WIDTH-1;x+=1){
            for (int y=1;y<HEIGHT-1;y+=1){
                Tile currentTile = world[x][y];
                if (currentTile.equals(TileSet.WALL) && countFloors(world,x,y)>=8){
                    world[x][y]=TileSet.randomFloor(random);
                }
                if (currentTile.equals(TileSet.WALL) && world[x][y-1].equals(TileSet.WALL) && world[x][y+1].equals(TileSet.WALL) && world[x-1][y].equals(TileSet.WALL) && world[x+1][y].equals(TileSet.WALL)){
                    world[x][y]=TileSet.Nothing;
                }
            }
        }
    }

    public Tile[][] randomWorld() {
        rooms.clear();
        hallways.clear();
        long startTime =  System.currentTimeMillis();
        Tile[][] randomWorld = new Tile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                randomWorld[x][y] = TileSet.Nothing;
            }
        }
        int roomNum = RandomUtils.uniform(random,6,8);
        for (int i = 0; i < roomNum; i++) {
            boolean out = generateRandomRoom(randomWorld);
            if (!out){
                return randomWorld();
            }
        }

//        connecting rooms
        QuickUnion<Room> quickUnion = new QuickUnion<>(rooms.toArray(new Room[roomNum]));
        for (int i=0;i<roomNum-1;i+=1){
            Room cRoom = rooms.get(i);
            Room  nearest = findNearestRoom(cRoom,quickUnion);
            easyConnect(randomWorld,cRoom,nearest);
            quickUnion.union(cRoom,nearest);
        }
//        connect(randomWorld,rooms.get(0),rooms.get(roomNum-1));
//        connect(randomWorld,rooms.get(0),rooms.get(roomNum-2));

/*         Room center = findCenter(randomWorld);
       boolean[] connected = new boolean[rooms.size()];
          for (int i = 0; i < rooms.size(); i += 1) {
            int j;
            do {
                j = RandomUtils.uniform(random, rooms.size());
            } while (i == j);
            if (!connected[i] || !connected[j]) {
                connect(randomWorld, rooms.get(i), rooms.get(j));
                connected[i] = true;
                connected[j] = true;
            }
        }
            for (int i = 0; i < connected.length; i += 1) {
                Room r = rooms.get(i);
                if (r != center && !connected[i]) {
                    connect(randomWorld, r, center);
                }
            }*/



/*        for (int i=0;i<rooms.size()-1;i+=1){
            connect(randomWorld,rooms.get(i),rooms.get(i+1));
        }*/

        detectHallway(randomWorld);
//        randomDoubleHallway(randomWorld);
        createWalls(randomWorld);
        dropWalls(randomWorld);


        return randomWorld;
    }

    private boolean isRowHallway(Tile[][] world,int x,int y){
        return world[x][y].isWalkable() && world[x][y-1].isWalkable() && world[x][y+1].isWalkable();
    }

    private void detectRow(Tile[][] world,int row){
        int currentX=1;
        int count =0;
        while (currentX<WIDTH-1){
            if (isRowHallway(world,currentX,row)){
                count+=1;
            }
            else{
                if (count>=3){
                    Pos end = new Pos(currentX-1,row);
                    Pos start = new Pos(currentX-count,row);
                    hallways.add(new Hallway(start,end));
                }
                count=0;
            }
            currentX+=1;
        }
    }

    private boolean isColumnHallway(Tile[][] world,int x,int y){
        return (world[x][y].isWalkable()) && world[x-1][y].equals(TileSet.Nothing) && world[x+1][y].equals(TileSet.Nothing);
    }

    private void detectColumn(Tile[][] world,int column){
        int currentY=1;
        int count =0;
        while (currentY<HEIGHT-1){
            if (isColumnHallway(world,column,currentY)){
                count+=1;
            }
            else{
                if (count>=3){
                    Pos end = new Pos(column,currentY-1);
                    Pos start = new Pos(column,currentY-count);
                    hallways.add(new Hallway(start,end));
                }
                count=0;
            }
            currentY+=1;
        }
    }

    private void detectHallway(Tile[][] world){
        for (int y=1;y<HEIGHT-1;y+=1){
            detectRow(world,y);
        }
        for (int x=1;x<WIDTH-1;x+=1){
            detectColumn(world,x);
        }
    }

    private Room findNearestRoom(Room room,QuickUnion quickUnion){
        Room nearest=null;
        double nearestDis = Double.POSITIVE_INFINITY;
        for (Room room1:rooms){
            if (!room.equals(room1)){
                double cDis= room1.getCenter().getDistance(room.getCenter());
                if (cDis<nearestDis && !quickUnion.isConnected(room,room1)){
                    nearest=room1;
                    nearestDis=cDis;
                }
            }
        }
        return nearest;
    }

    public void randomDoubleHallway(Tile[][] world){
        for (Hallway hallway:hallways){
            if (hallway.direction == 0) {
                doubleRow(world, hallway);
            }
            if (hallway.direction == 1) {
                doubleColumn(world, hallway);
            }

        }
    }

    private void doubleColumn(Tile[][] world,Hallway hallway){
        int x=hallway.start.x;
        int startY = hallway.start.y;
        int endY = hallway.end.y;
        if (world[x-1][startY-1].isWalkable() || world[x-1][endY+1].isWalkable()){
            for (int y=startY;y<=endY;y+=1){
                world[x-1][y]=TileSet.randomFloor(random);
            }
        }
        if (world[x+1][startY-1].isWalkable() || world[x+1][endY+1].isWalkable()){
            for (int y=startY;y<=endY;y+=1){
                world[x+1][y]=TileSet.randomFloor(random);
            }
        }
    }

    private void doubleRow(Tile[][] world,Hallway hallway){
        int y=hallway.start.y;
        int startX = hallway.start.x;
        int endX = hallway.end.x;
        if (world[startX-1][y-1].isWalkable() || world[endX+1][y-1].isWalkable()){
            for (int x=startX;x<=endX;x+=1){
                world[x][y-1]=TileSet.randomFloor(random);
            }
        }
        if (world[startX-1][y+1].isWalkable() || world[endX+1][y+1].isWalkable()){
            for (int x=startX;x<=endX;x+=1){
                world[x][y+1]=TileSet.randomFloor(random);
            }
        }
    }

    private void easyConnect(Tile[][] world,Room r1,Room r2){

        Pos start = r1.getCenter();
        Pos end = r2.getCenter();
        int vectorX = end.x-start.x-1;
        int vectorY = end.y-start.y-1;
        List<Pos> path = new ArrayList<>();
        path.add(start);
        while (vectorX>0){
            Pos lastPos =path.get(path.size()-1);
            path.add(new Pos(lastPos.x+1,lastPos.y));
            vectorX-=1;
        }
        while (vectorX<0){
            Pos lastPos =path.get(path.size()-1);
            path.add(new Pos(lastPos.x-1,lastPos.y));
            vectorX+=1;
        }
        while (vectorY>0){
            Pos lastPos =path.get(path.size()-1);
            path.add(new Pos(lastPos.x,lastPos.y+1));
            vectorY-=1;
        }
        while (vectorY<0){
            Pos lastPos =path.get(path.size()-1);
            path.add(new Pos(lastPos.x,lastPos.y-1));
            vectorY+=1;
        }
        for (Pos pos:path){
            world[pos.x][pos.y]=TileSet.randomFloor(random).copy();
//            world[pos.x+1][pos.y+1]=TileSet.randomFloor(random).copy();
        }
//        TODO:modify getNearest to accept quickUnion
    }

    private void connect(Tile[][] world,Room r1,Room r2){
        ArrayList<Pos> route = new ArrayList<>();
        Pos start =randomBorder(r1);
        Pos end = randomBorder(r2);
        Integer x = end.x - start.x;
        Integer y = end.y - start.y;
        int xNoise,yNoise;
        if (x>3 || x<-3) {
            xNoise = x > 0 ? -RandomUtils.uniform(random, x / 4) : -RandomUtils.uniform(random, x / 4, 0);
        }
        else{
            xNoise =0;
        }
        if (y>3 || y<-3) {
            yNoise = y > 0 ? -RandomUtils.uniform(random, y / 4) : -RandomUtils.uniform(random, y / 4, 0);
        }
        else {
            yNoise = 0;
        }
        int left = x>0? -xNoise:xNoise-x;
        int right = x>0? x-xNoise:xNoise;
        int up = y>0? y-yNoise:yNoise;
        int down = y>0? -yNoise:yNoise-y;
        while (true){
            if (start.equals(end)){
                break;
            }
            int randDirection = RandomUtils.uniform(random,2);
            if (randDirection==0 && start.y+1<HEIGHT-1){
                if (up+down==0){
                    continue;
                }
                if (RandomUtils.bernoulli(random,up/(double)(up+down))){
                    if (up>0){
                        up-=1;
                        start = new Pos(start.x,start.y+1);
                        route.add(start);
                    }
                }
                else if (down>0 && start.y-1>0){
                    down-=1;
                    start = new Pos(start.x,start.y-1);
                    route.add(start);
                }
            }
            else{
                if (left+right==0){
                    continue;
                }
                if (RandomUtils.bernoulli(random,right/(double)(left+right))){
                    if (right>0){
                        right-=1;
                        start = new Pos(start.x+1,start.y);
                        route.add(start);
                    }
                }
                else if (left>0){
                    left-=1;
                    start = new Pos(start.x-1,start.y);
                    route.add(start);
                }
            }
        }
        for (Pos p:route){
            world[p.x][p.y]=TileSet.randomFloor(random).copy();
//            world[p.x][p.y+1]=TileSet.randomFloor(random).copy();
        }
    }

    private Pos randomBorder(Room room){
        ArrayList<Pos> borders = new ArrayList<>();
        for (int x=room.getStart().x;x<room.getEnd().x;x+=1){
            borders.add(new Pos(x,room.getStart().y));
            borders.add(new Pos(x,room.getEnd().y));
        }
        for (int y=room.getStart().y;y<room.getEnd().y;y+=1){
            borders.add(new Pos(room.getStart().x,y));
            borders.add(new Pos(room.getEnd().x,y));
        }
        return borders.get(RandomUtils.uniform(random,borders.size()));
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

}
