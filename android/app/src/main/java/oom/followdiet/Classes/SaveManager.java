package oom.followdiet.Classes;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import oom.followdiet.Activities.MainActivity;

public final class SaveManager {

    static String filename = "followdiet.save";

    static String profileKey = "-[@PROFIL@]-";
    static String foodsKey = "-[@FOODS@]-";
    static String historyKey = "-[@HISTORY@]-";
    static String historyDateKey = "[@HISTORY-DATE@]-";
    static String historyFoodKey = "[@HISTORY-FOOD@]-";
    static String separator = "-[@S3P4R4T0R@]-";

    public static String Serialize()
    {
        String content ="";
        //PROFILE
        content += profileKey + "\n";
        content += Profile.name + "\n";
        content += Profile.poids + "\n";
        content += Profile.objectivkcal + "\n";
        content += Profile.dietcetogene + "\n";

        //FOOD
        content += foodsKey + "\n";
        for (Food f : FoodsManager.foods)
        {
            content += f.name + separator + f.ProteinValue + separator + f.CarbohydrateValue + separator + f.LipidValue + separator + f.Unit + "\n";
        }

        //HISTORY
        content += historyKey + "\n";
        for(String k : History.dayfoods.keySet())
        {
            if (History.dayfoods.get(k).size() > 0) {
                content += historyDateKey + "\n" + k + "\n" + historyFoodKey + "\n";
                for (Food f : History.dayfoods.get(k)) {
                    content += f.name + separator + f.ProteinValue + separator + f.CarbohydrateValue + separator + f.LipidValue + separator + f.Unit + separator + f.Quantity + "\n";
                }
            }
        }
        return content;
    }


    private static int m_parseProfile(ArrayList<String> lines, int index)
    {
        index++;
        Profile.name = lines.get(index++);
        Profile.poids = Double.parseDouble(lines.get(index++));
        Profile.objectivkcal = Integer.parseInt(lines.get(index++));
        if(!lines.get(index).startsWith("-[@"))
            Profile.dietcetogene = Boolean.parseBoolean(lines.get(index++));
        return index;
    }

    private static int m_parseFoods(ArrayList<String> lines, int index)
    {
        index++;
        Food tmpf;
        String[] datas;
        while(index < lines.size() && !lines.get(index).startsWith("-[@")) //pas terrible hein
        {
            datas = lines.get(index).split(Pattern.quote(separator));
            tmpf = new Food();
            tmpf.name = datas[0];
            tmpf.ProteinValue = Double.parseDouble(datas[1]);
            tmpf.CarbohydrateValue = Double.parseDouble(datas[2]);
            tmpf.LipidValue = Double.parseDouble(datas[3]);
            tmpf.Unit = FoodUnit.fromString(datas[4]);
            FoodsManager.AddFood(tmpf);
            index++;
        }
        return index;
    }

    private static int m_parseHistory(ArrayList<String> lines, int index)
    {
        index++;
        Food tmpf;
        String[] datas;
        String currday ="";
        while(index < lines.size() && !lines.get(index).startsWith("-[@"))
        {
            if(lines.get(index).equals(historyDateKey))
                currday = lines.get(++index);
            else if (lines.get(index).equals(historyFoodKey))
            {
                ++index;
                while (index < lines.size() && !lines.get(index).startsWith("-[@") && !lines.get(index).startsWith("[@")) //bancale de ouf
                {
                    datas = lines.get(index).split(Pattern.quote(separator));
                    tmpf = new Food();
                    tmpf.name = datas[0];
                    tmpf.ProteinValue = Double.parseDouble(datas[1]);
                    tmpf.CarbohydrateValue = Double.parseDouble(datas[2]);
                    tmpf.LipidValue = Double.parseDouble(datas[3]);
                    tmpf.Unit = FoodUnit.fromString(datas[4]);
                    tmpf.Quantity = Double.parseDouble(datas[5]);
                    History.AddFoodToDay(currday, tmpf);
                    index++;
                }
                continue;
            }
            index++;
        }
        return index;
    }

    public static void Deserialize(ArrayList<String> lines)
    {
        int i = 0;
        for (i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).equals(profileKey))
                i = m_parseProfile(lines, i);
            if (lines.get(i).equals(foodsKey))
                i = m_parseFoods(lines, i);
            if (lines.get(i).equals(historyKey))
                i = m_parseHistory(lines, i);
            else
                i++;
        }
    }

    public static void SaveAll(Context context)
    {
        FileOutputStream outputStream;
        if (Profile.name != null)
        {
            try {
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                String content = Serialize();
                outputStream.write(content.getBytes());
                outputStream.close();

            } catch (Exception e) {
                MainActivity.Popup(context, "Erreur lors de l'écriture de la sauvegarde: " + e.toString());
                e.printStackTrace();
            }
        }
    }

    public static boolean SaveExisting(Context context)
    {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public static void DeleteSave(Context context)
    {
        File file = context.getFileStreamPath(filename);
        file.delete();
    }

    public static void LoadAll(Context context)
    {
        FileInputStream inputStream;

        try {
            inputStream = context.openFileInput(filename);
            if (inputStream != null)
            {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                ArrayList<String> lines = new ArrayList<>();
                while ((line = buffreader.readLine()) != null) {
                    lines.add(line);
                }
                Deserialize(lines);
                buffreader.close();
                inputreader.close();
                inputStream.close();
            }

        } catch (Exception e) {
            MainActivity.Popup(context, "Erreur lors du chargement de la sauvegarde: " + e.toString());
            e.printStackTrace();
        }
    }
}

