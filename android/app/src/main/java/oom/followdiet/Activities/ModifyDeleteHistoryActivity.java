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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import oom.followdiet.Classes.Food;
import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.R;

public class ModifyDeleteHistoryActivity extends AppCompatActivity {

    TextView textViewUnit;
    Spinner SpinnerNameDate, SpinnerNameFood;
    EditText quantityText;


    private void disableSpinner()
    {
        SpinnerNameFood.setEnabled(false);
        SpinnerNameFood.setClickable(false);
        textViewUnit.setText("");
    }

    private void enableSpinner()
    {
        SpinnerNameFood.setEnabled(true);
        SpinnerNameFood.setClickable(true);
    }

    public void autoSetFood()
    {
        String date = (String)SpinnerNameDate.getSelectedItem();
        if (History.dayfoods.containsKey(date))
        {
            if (History.dayfoods.get(date).size() > 0)
            {
                SpinnerNameFood.setAdapter(new ArrayAdapter<Food>(this, R.layout.support_simple_spinner_dropdown_item, History.dayfoods.get(date)));
                enableSpinner();
            }
            else
            {
                disableSpinner();
                MainActivity.Popup(this, "Ce jour est vide :(");
            }
        }
        else
            disableSpinner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_delete_history);
        final Context context = this;

        textViewUnit = findViewById(R.id.textViewUnit);
        SpinnerNameDate = findViewById(R.id.SpinnerNameDate);
        SpinnerNameFood = findViewById(R.id.SpinnerNameFood);
        quantityText = findViewById(R.id.editText3);

        String[] keys = History.dayfoods.keySet().toArray(new String[History.dayfoods.keySet().size()]);
        SpinnerNameDate.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, keys));
        String currdate = History.GetDay();
        if (History.dayfoods.containsKey(currdate))
        {
            int i = 0;
            for (i = 0; i < keys.length; i++)
            {
                if (keys.equals(currdate))
                    break;
            }
            SpinnerNameDate.setSelection(i >= keys.length ? 0 : i);
        }
        else
            SpinnerNameDate.setSelection(keys.length - 1);
        autoSetFood();

        SpinnerNameDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                autoSetFood();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        SpinnerNameFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Food f = (Food)SpinnerNameFood.getSelectedItem();
                textViewUnit.setText(f.Unit.toString());
                quantityText.setText("" + f.Quantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    public void on_edithistoryfood(View v)
    {
        try
        {
            double newq = Double.parseDouble(quantityText.getText().toString().trim());
            Food f = (Food)SpinnerNameFood.getSelectedItem();
            f.Quantity = newq;
            MainActivity.Popup(this, "L'historique de l'aliment à bien été modifié !");
            finish();
        }
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de l'édition de l'aliment :( -> '" + ex.toString() + "'");
        }
    }

    public void on_deletehistoryfood(View v)
    {
        try
        {
            final Context context = this;
            final String date = (String)SpinnerNameDate.getSelectedItem();
            final Food f = (Food)SpinnerNameFood.getSelectedItem();
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
            else
                builder = new AlertDialog.Builder(this);
            builder.setTitle("Supprimer l'aliment");
            builder.setMessage("Êtes vous sur de vouloir supprimer " + f.name + " de la date '" + date + "' ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //String date = (String)SpinnerNameDate.getSelectedItem();
                    //Food f = (Food)SpinnerNameFood.getSelectedItem();
                    History.DeleteFoodToDay(date, f);
                    if (History.dayfoods.keySet().size() == 0)
                        finish();
                    SpinnerNameDate.setAdapter(new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, History.dayfoods.keySet().toArray(new String[History.dayfoods.keySet().size()])));
                    if (!History.dayfoods.containsKey(date))
                        SpinnerNameDate.setSelection(History.dayfoods.keySet().size() - 1);
                    autoSetFood();
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
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de la suppression de l'aliment :( -> '" + ex.toString() + "'");
        }
    }
}
