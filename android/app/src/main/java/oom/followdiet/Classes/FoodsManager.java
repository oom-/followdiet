package oom.followdiet.Classes;

import java.util.ArrayList;

/**
 * Created by OOM on 17/09/2018.
 */

public final class FoodsManager {
    public static ArrayList<Food> foods = new ArrayList<>();

    public static void AddFood(Food f)
    {
        foods.add(f);
    }

    public static boolean FoodNameAlreadyExisting(String name)
    {
        for(Food fit: foods)
        {
            if (fit.name.equals(name))
                return true;
        }
        return false;
    }

    public static void Reset()
    {
        foods.clear();
    }

    public static void DeleteFood(Food f)
    {
        foods.remove(f);
    }
}
