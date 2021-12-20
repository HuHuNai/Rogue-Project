package Utils;

import engine.Tile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStar {
    public static class PathCost implements Comparable<PathCost>{
        public Pos pos;
        public int cost;
        public PathCost(Pos path,int cost) {
            this.pos =path;
            this.cost=cost;
        }

        @Override
        public int compareTo(PathCost o) {
            return this.cost-o.cost;
        }
    }
    public static LinkedList<Pos> search(Tile[][] map, Pos start, Pos goal){
        PriorityQueue<PathCost> frontier = new PriorityQueue<>();
        frontier.add(new PathCost(start,0));
        int[][] costSoFar = new int[map.length][map[0].length];
        LinkedList<Pos> path = new LinkedList<>();
        while (!frontier.isEmpty()){
            Pos current = frontier.poll().pos;

            if (current.equals(goal)){
                break;
            }
            Pos[] neighbors = new Pos[]{new Pos(current.x-1,current.y),new Pos(current.x+1, current.y),
                    new Pos(current.x, current.y-1),new Pos(current.x, current.y+1)};
            for (Pos next:neighbors){
                if (!map[next.x][next.y].isWalkable()){
                    continue;
                }
                int newCost = costSoFar[current.x][current.y]+1;
                if (costSoFar[next.x][next.y]==0 ||newCost<costSoFar[next.x][next.y]){
                    costSoFar[next.x][next.y]=newCost;
                    int priority = newCost +Math.abs(next.x- goal.x)+Math.abs(next.y- goal.y);
                    frontier.add(new PathCost(next,priority));
                }
            }
            path.add(current);
        }
        path.remove(0);
        return path;
    }
}
