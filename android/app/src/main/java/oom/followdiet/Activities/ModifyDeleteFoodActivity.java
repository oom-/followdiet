package oom.followdiet.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import oom.followdiet.Classes.Food;
import oom.followdiet.Classes.FoodUnit;
import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.R;

public class ModifyDeleteFoodActivity extends AppCompatActivity {

    EditText textBoxProt, textBoxCar, textBoxLip;
    Spinner spinner2, dropBoxUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_delete_food);
        spinner2 = findViewById(R.id.SpinnerNameFood);
        textBoxProt = findViewById(R.id.editText5);
        textBoxCar = findViewById(R.id.editText6);
        textBoxLip = findViewById(R.id.editText7);
        dropBoxUnit = findViewById(R.id.spinner);

        //Set data spinner
        dropBoxUnit.setAdapter(new ArrayAdapter<FoodUnit>(this, R.layout.support_simple_spinner_dropdown_item, FoodUnit.values()));
        spinner2.setAdapter(new ArrayAdapter<Food>(this, R.layout.support_simple_spinner_dropdown_item, FoodsManager.foods));
        spinner2.setSelection(0);
        if(FoodsManager.foods.size() > 0)
            FillViewFromFood(FoodsManager.foods.get(0));
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Food f = (Food)spinner2.getSelectedItem();
                FillViewFromFood(f);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void FillViewFromFood(Food f)
    {
        textBoxProt.setText(Double.toString(f.ProteinValue));
        textBoxCar.setText(Double.toString(f.CarbohydrateValue));
        textBoxLip.setText(Double.toString(f.LipidValue));
        int index;
        FoodUnit[] foodunit = FoodUnit.values();
        for (index = 0; index < foodunit.length; index++)
            if (foodunit[index].equals(f.Unit))
                break;
        index = index == foodunit.length ? index - 1 : index;
        dropBoxUnit.setSelection(index);
    }

    public void on_savefood(View v)
    {
        try
        {
            //Conversions
            double proteins = Double.parseDouble(textBoxProt.getText().toString().trim());
            double carbohydrate = Double.parseDouble(textBoxCar.getText().toString().trim());
            double lipid = Double.parseDouble(textBoxLip.getText().toString().trim());
            FoodUnit unit = (FoodUnit)dropBoxUnit.getSelectedItem();

            //Assignation
            Food f = (Food)spinner2.getSelectedItem();
            f.ProteinValue = proteins;
            f.CarbohydrateValue = carbohydrate;
            f.LipidValue = lipid;
            f.Unit = unit;
            History.ModifyAllFoodWithName(f);
            MainActivity.Popup(this, "L'aliment '"+ f.name +"' à bien été modifié (et dans l'historique aussi) !");
            finish();
        }
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de l'ajout de l'aliment :( -> '" + ex.toString() + "'");
        }
    }

    public void on_deletefood(View v)
    {
        final Food f = (Food)spinner2.getSelectedItem();
        final Context context = this;
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        else
            builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer l'aliment");
        builder.setMessage("Êtes vous sur de vouloir supprimer " + f.name + " ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //OK
                FoodsManager.DeleteFood(f);
                MainActivity.Popup(context, "L'aliment '"+ f.name +"' à bien été supprimé !");
                if(FoodsManager.foods.size() == 0)
                    finish();
                else
                {
                    spinner2.setAdapter(new ArrayAdapter<Food>(context, R.layout.support_simple_spinner_dropdown_item, FoodsManager.foods));
                    spinner2.setSelection(0);
                }

            }
        });
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Cancel
            }
        });
        builder.show();
    }
}
