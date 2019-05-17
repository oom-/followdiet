package oom.followdiet.Classes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by OOM on 17/09/2018.
 */

public final class History {
    public static HashMap<String, ArrayList<Food>> dayfoods = new HashMap<>();

    public static void AddFoodToDay(Food f)
    {
        if (dayfoods.containsKey(GetDay()))
        {
            ArrayList<Food> list = dayfoods.get(GetDay());
            list.add(f);
        }
        else
        {
            ArrayList<Food> list = new ArrayList<Food>();
            dayfoods.put(GetDay(), list);
            list.add(f);
        }
    }

    public static  void Reset()
    {
        for (String s : dayfoods.keySet())
        {
            dayfoods.get(s).clear();
        }
        dayfoods.clear();
    }

    public static void AddFoodToDay(String day, Food f)
    {
        if (dayfoods.containsKey(day))
        {
            ArrayList<Food> list = dayfoods.get(day);
            list.add(f);
        }
        else
        {
            ArrayList<Food> list = new ArrayList<Food>();
            dayfoods.put(day, list);
            list.add(f);
        }
    }

    public static boolean DeleteFoodToDay(String day, Food f)
    {
        if (dayfoods.containsKey(day) && dayfoods.get(day).contains(f))
        {
            dayfoods.get(day).remove(f);
            if (dayfoods.get(day).size() == 0)
                dayfoods.remove(day);
            return true;
        }
        return false;
    }

    public static void ModifyAllFoodWithName(Food f)
    {
        Set<String> days = dayfoods.keySet();
        ArrayList<Food> list;
        for (String d: days)
        {
            list = dayfoods.get(d);
            for (Food o : list)
            {
                if(o.name.equals(f.name))
                {
                    o.CarbohydrateValue = f.CarbohydrateValue;
                    o.ProteinValue = f.ProteinValue;
                    o.LipidValue = f.LipidValue;
                    o.Unit = f.Unit;
                }
            }
        }
    }

    /*Today*/
    public static double GetTodayTotalProteinsConsumed()
    {
        double total = 0;

        if (dayfoods.containsKey(GetDay())) {
            ArrayList<Food> list = dayfoods.get(GetDay());
            for (Food f : list)
            {
               total += f.GetTotalProteins();
            }
        }
        return total;
    }

    public static double GetTodayTotalCarbohydrateConsumed()
    {
        double total = 0;

        if (dayfoods.containsKey(GetDay())) {
            ArrayList<Food> list = dayfoods.get(GetDay());
            for (Food f : list)
            {
                total += f.GetTotalCarbohydrate();
            }
        }
        return total;
    }

    public static double GetTodayTotalLipidConsumed()
    {
        double total = 0;

        if (dayfoods.containsKey(GetDay())) {
            ArrayList<Food> list = dayfoods.get(GetDay());
            for (Food f : list)
            {
                total += f.GetTotalLipd();
            }
        }
        return total;
    }

    public static boolean CheckDaySuccess(String daykey)
    {
        int objectivkcal = Profile.objectivkcal;
        objectivkcal = objectivkcal - (int)GetTodayTotalProteinsKcalConsumed(daykey);
        objectivkcal = objectivkcal - (int)GetTodayTotalCarbohydrateKcalConsumed(daykey);
        objectivkcal = objectivkcal - (int)GetTodayTotalLipidKcalConsumed(daykey);
        return (objectivkcal >= -10 && objectivkcal <= 0);
    }

    public static double GetTodayTotalProteinsKcalConsumed(String daykey)
    {
        double total = 0;

        if (dayfoods.containsKey(daykey)) {
            ArrayList<Food> list = dayfoods.get(daykey);
            for (Food f : list)
            {
                total += f.GetTotalProteins();
            }
        }
        return total * 4;
    }

    public static double GetTodayTotalCarbohydrateKcalConsumed(String daykey)
    {
        double total = 0;

        if (dayfoods.containsKey(daykey)) {
            ArrayList<Food> list = dayfoods.get(daykey);
            for (Food f : list)
            {
                total += f.GetTotalCarbohydrate();
            }
        }
        return total * 4;
    }

    public static double GetTodayTotalLipidKcalConsumed(String daykey)
    {
        double total = 0;

        if (dayfoods.containsKey(daykey)) {
            ArrayList<Food> list = dayfoods.get(daykey);
            for (Food f : list)
            {
                total += f.GetTotalLipd();
            }
        }
        return total * 9;
    }

    public static double GetTodayTotalKcalConsumed()
    {
        double total = 0;

        if (dayfoods.containsKey(GetDay()))
        {
            ArrayList<Food> list = dayfoods.get(GetDay());
            for (Food f : list)
            {
                total += f.GetTotalProteins() * 4;
                total += f.GetTotalLipd() * 9;
                total += f.GetTotalCarbohydrate() * 4;
            }
        }
        return total;
    }

    public static int GetCountDay()
    {
        if (dayfoods.containsKey(GetDay()))
            return dayfoods.get(GetDay()).size();
        return 0;
    }

    public static String GetDay()
    {
        Calendar c = Calendar.getInstance();
        return (String.format("%02d", (c.get(Calendar.DAY_OF_MONTH))) + '/' + String.format("%02d", (c.get(Calendar.MONTH) + 1)) + '/' + Integer.toString(c.get(Calendar.YEAR)));
    }

    public static String GetRemaningTime()
    {
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long passed = now - c.getTimeInMillis();
        passed = 86400000  - passed;
        long seconds = passed / 1000;
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;

        return h + "h" + m + "m" + s + "s";
    }
}
