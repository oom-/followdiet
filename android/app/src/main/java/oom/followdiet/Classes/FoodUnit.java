package oom.followdiet.Classes;

/**
 * Created by OOM on 17/09/2018.
 */

public enum FoodUnit {
    GR("gr"),
    UNIT("unité"),
    OTHER("autre");

    private String friendlyName;
    private FoodUnit(String friendlyName)
    {
        this.friendlyName = friendlyName;
    }

    public static FoodUnit fromString(String text) {
        for (FoodUnit b : FoodUnit.values()) {
            if (b.friendlyName.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return OTHER;
    }

    @Override public String toString(){return this.friendlyName;}
}
