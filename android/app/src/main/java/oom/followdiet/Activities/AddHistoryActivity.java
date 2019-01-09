package oom.followdiet.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import oom.followdiet.Classes.Food;
import oom.followdiet.Classes.FoodUnit;
import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.R;

public class AddHistoryActivity extends AppCompatActivity {

    Spinner aliments;
    Button button;
    EditText quantityText;
    TextView textViewUnit;
    TextView addFoodDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);

        addFoodDay = findViewById(R.id.addFoodDay);
        addFoodDay.setText(History.GetDay());
        button = findViewById(R.id.button);
        aliments = findViewById(R.id.SpinnerNameFood);
        quantityText = findViewById(R.id.editText3);
        textViewUnit = findViewById(R.id.textViewUnit);
        aliments.setAdapter(new ArrayAdapter<Food>(this, R.layout.support_simple_spinner_dropdown_item, FoodsManager.foods));
        aliments.setSelection(0);
        aliments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Food f = (Food)aliments.getSelectedItem();
                textViewUnit.setText(f.Unit.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void on_savehistoryfood(View v)
    {
        try
        {
            Food f = (Food)aliments.getSelectedItem();
            double quantity = Double.parseDouble(quantityText.getText().toString().trim());
            History.AddFoodToDay(new Food(f, quantity));
            MainActivity.Popup(this, "L'aliment '"+ f.name +"' à bien été ajouté à l'historique d'aujourd'hui !");
            finish();
        }
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de l'ajout de l'aliment :( -> '" + ex.toString() + "'");
        }
    }
}
