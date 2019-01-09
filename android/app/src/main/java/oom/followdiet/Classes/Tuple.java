package oom.followdiet.Classes;

import android.support.annotation.NonNull;

public class Tuple implements  Comparable<Tuple>{
    public final float x;
    public final int y;
    public Tuple(float x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public int compareTo(@NonNull Tuple tuple) {
        if (x > tuple.x)
            return 1;
        if (x < tuple.x)
            return -1;
        return 0;
    }
}
