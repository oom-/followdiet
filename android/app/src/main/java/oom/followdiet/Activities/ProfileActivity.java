package oom.followdiet.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import oom.followdiet.Classes.Food;
import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.Classes.Profile;
import oom.followdiet.Classes.SaveManager;
import oom.followdiet.R;

public class ProfileActivity extends AppCompatActivity {

    EditText textboxName, textboxPoids, textboxCals;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textboxName = findViewById(R.id.editText);
        textboxPoids = findViewById(R.id.editText2);
        textboxCals = findViewById(R.id.editText3);
        switch1 = findViewById(R.id.switch1);
        textboxName.setText(Profile.name);
        textboxPoids.setText(String.format("%.2f", Profile.poids));
        textboxCals.setText("" + Profile.objectivkcal);
        switch1.setChecked(Profile.dietcetogene);
    }

    public  void on_saveprofile(View v)
    {
        try
        {
            //Conversions
            String name = textboxName.getText().toString().trim();
            if (name.length() > 0) {
                double poids = Double.parseDouble(textboxPoids.getText().toString().trim().replace(',', '.'));
                int objectivkcal = Integer.parseInt(textboxCals.getText().toString().trim());
                boolean cetogen = switch1.isChecked();
                //Assignation
                Profile.name = name;
                Profile.poids = poids;
                Profile.objectivkcal = objectivkcal;
                Profile.dietcetogene = cetogen;
                MainActivity.Popup(this, "Sauvegarde du profil effectué !");
                if (cetogen) {
                    MainActivity.Popup(this, "Besoin normale de prot: " + Profile.GetTotalProteins());
                    MainActivity.Popup(this, "Besoin cétogène de prot: " + Profile.GetTotalProteins());
                }
                if (Profile.GetTotalCarbohydrateRealValue() < 0)
                    MainActivity.Popup(this, "ATTENTION ! Votre objectif calorique est impossible ! La valeur des glucides serait négative ! Vous devez réspectez vos apports journalier !", Toast.LENGTH_LONG);
                finish();
            }
        }
        catch (Exception ex)
        {
            MainActivity.Popup(this, "Une erreur est survenue lors de la sauvegarde du profil :( -> '" + ex.toString() + "'");
        }
    }

    public void on_deletesave(View v)
    {
        final Context context = this;
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        else
            builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer l'aliment");
        builder.setMessage("Êtes vous sur de vouloir supprimer la dernière sauvegarde et vos informations ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Profile.Reset();
                FoodsManager.Reset();
                History.Reset();
                SaveManager.DeleteSave(context);
                MainActivity.Popup(context, "Le fichier de sauvegarde a bien été supprimé !");
                finish();
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
