package Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class QuickUnion<T> {
    private int[] connected ;
    private Hashtable<T,Integer> hashtable = new Hashtable();
    private int[] size;
    private int count;

    public QuickUnion(T[] items)  {
        connected=new int[items.length];
        size = new int[items.length];
        for (int i=0;i<items.length;i+=1){
            connected[i]=i;
            hashtable.put((T)items[i],i);
        }
        count=items.length;
    }


    private int findBoss(int p) {
        while (p != connected[p])
            p = connected[p];
        return p;
    }

    public void union(T a,T b){
        Integer aNum = hashtable.get(a);
        Integer bNum = hashtable.get(b);
        Integer aBoss = findBoss(aNum);
        Integer bBoss = findBoss(bNum);
        if (size[aBoss]>size[bBoss]){
            connected[bBoss]=aBoss;
            size[aBoss]+=size[bBoss];
        }
        else{
            connected[aBoss]=bBoss;
            size[bBoss]+=size[aBoss];
        }
        count-=1;
    }

    public boolean isConnected(T a,T b){
        Integer aNum = hashtable.get(a);
        Integer bNum = hashtable.get(b);
        return findBoss(aNum)==findBoss(bNum);
    }

    public int getCount(){
        return count;
    }
}
