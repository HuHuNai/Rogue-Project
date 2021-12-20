package engine;

import java.util.*;

public class TileSet {
    private static final List<Tile> FLOORSET = new ArrayList<>(){{
        add(new Tile("floor1"));
        add(new Tile("floor2"));
        add(new Tile("floor3"));
        add(new Tile("floor4"));
        add(new Tile("floor5"));
        add(new Tile("floor6"));
        add(new Tile("floor7"));
        add(new Tile("floor8"));
    }};
    public static final Tile WALL = new Tile("wall");
    public static final Tile Nothing = new Tile("nothing");

    public static Tile randomFloor(Random random){
        return FLOORSET.get(random.nextInt(8)).copy();
    }
}
