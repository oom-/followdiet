package oom.followdiet.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import oom.followdiet.Classes.Food;
import oom.followdiet.Classes.FoodUnit;
import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.R;

public class AddFoodActivity extends AppCompatActivity {

    EditText textBoxName, textBoxProt, textBoxCar, textBoxLip;

    Spinner dropBoxUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        textBoxName = findViewById(R.id.ceatletextname);
        textBoxProt = findViewById(R.id.editText5);
        textBoxCar = findViewById(R.id.editText6);
        textBoxLip = findViewById(R.id.editText7);
        dropBoxUnit = findViewById(R.id.spinner);

        //Set data spinner
        dropBoxUnit.setAdapter(new ArrayAdapter<FoodUnit>(this, R.layout.support_simple_spinner_dropdown_item, FoodUnit.values()));
        dropBoxUnit.setSelection(0);
    }

    public void on_addfoodreturn(View v)
    {
        on_addfood(v);
        finish();
    }

    public void on_addfood(View v)
    {
        try
        {
            //Conversions
            String name = textBoxName.getText().toString().trim();
            double proteins = Double.parseDouble(textBoxProt.getText().toString().trim());
            double carbohydrate = Double.parseDouble(textBoxCar.getText().toString().trim());
            double lipid = Double.parseDouble(textBoxLip.getText().toString().trim());
            FoodUnit unit = (FoodUnit)dropBoxUnit.getSelectedItem();

            if (FoodsManager.FoodNameAlreadyExisting(name))
                MainActivity.Popup(this, "Un aliment avec le nom '" + name + "' existe déjà.");
            else
            {
                //Assignation
                Food f = new Food();
                f.name = name;
                f.ProteinValue = proteins;
                f.CarbohydrateValue = carbohydrate;
                f.LipidValue = lipid;
                f.Unit = unit;
                FoodsManager.AddFood(f);
                MainActivity.Popup(this, "L'aliment '"+ name +"' à bien été ajouté !");
                textBoxName.setText("");
                textBoxProt.setText("");
                textBoxCar.setText("");
                textBoxLip.setText("");
                dropBoxUnit.setSelection(0);
            }

        }
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de l'ajout de l'aliment :( -> '" + ex.toString() + "'");
        }
    }
}
