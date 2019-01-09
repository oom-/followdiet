package oom.followdiet.Classes;

/**
 * Created by OOM on 17/09/2018.
 */

public final class Profile {
    public static String name;
    public static double poids;
    public static int objectivkcal;
    public static boolean dietcetogene;

    private static double getPercentOfObjectivkcal(double percent)
    {
        return objectivkcal * (percent / 100);
    }

    public static double GetTotalProteins()
    {
        return dietcetogene ? (getPercentOfObjectivkcal(25) / 4) : (poids * 2.5);
    }

    public static double GetTotalLipids()
    {
        return dietcetogene ? (getPercentOfObjectivkcal(70) / 9) : poids;
    }

    public static double GetTotalCarbohydrate()
    {
        double total = ((objectivkcal - (GetTotalProteins() * 4 + GetTotalLipids() * 9)) /4);
        return dietcetogene ? (getPercentOfObjectivkcal(5) / 4) : (total < 0 ? 0 : total);
    }

    public static double GetTotalCarbohydrateRealValue()
    {
        double total = ((objectivkcal - (GetTotalProteins() * 4 + GetTotalLipids() * 9)) /4);
        return total;
    }

    public static void Reset()
    {
        name = null;
        poids = 0f;
        objectivkcal = 0;
        dietcetogene = false;
    }
}
