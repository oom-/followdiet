package oom.followdiet.Classes;

/**
 * Created by OOM on 17/09/2018.
 */

public class Food {
    public String name;
    public double CarbohydrateValue;
    public double LipidValue;
    public double ProteinValue;
    public double Quantity;
    public FoodUnit Unit;

    public Food() {}

    public Food(Food f, double q)
    {
        name = f.name;
        CarbohydrateValue = f.CarbohydrateValue;
        LipidValue = f.LipidValue;
        ProteinValue = f.ProteinValue;
        Quantity = q;
        Unit = f.Unit;
    }

    @Override public String toString(){return this.name;}

    public double GetTotalProteins()
    {
        double total = 0;

        if(Unit == FoodUnit.GR)
            total += (ProteinValue / 100) * Quantity;
        else if (Unit == FoodUnit.UNIT)
            total += ProteinValue * Quantity;
        else
            total += ProteinValue * Quantity;
        return total;
    }

    public double GetTotalCarbohydrate()
    {
        double total = 0;

        if(Unit == FoodUnit.GR)
            total += (CarbohydrateValue / 100) * Quantity;
        else if (Unit == FoodUnit.UNIT)
            total += CarbohydrateValue * Quantity;
        else
            total += CarbohydrateValue * Quantity;
        return total;
    }

    public double GetTotalLipd()
    {
        double total = 0;

        if(Unit == FoodUnit.GR)
            total += (LipidValue / 100) * Quantity;
        else if (Unit == FoodUnit.UNIT)
            total += LipidValue * Quantity;
        else
            total += LipidValue * Quantity;
        return total;
    }
}
