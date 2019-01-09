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
import oom.followdiet.Classes.Profile;
import oom.followdiet.R;


public class FillerActivity extends AppCompatActivity {

    Spinner SpinnerNameFood;
    TextView valueprot, valueglucid, valuelipids, fillerdaylabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filler);
        String defaulttext = " ?gr restant  ➡️ ?gr";
        SpinnerNameFood = findViewById(R.id.SpinnerNameFood);
        valueprot = findViewById(R.id.valueprot);
        valueglucid = findViewById(R.id.valueglucid);
        valuelipids = findViewById(R.id.valuelipids);
        fillerdaylabel= findViewById(R.id.fillerdaylabel);

        fillerdaylabel.setText(History.GetDay());
        valueprot.setText(defaulttext);
        valueglucid.setText(defaulttext);
        valuelipids.setText(defaulttext);

        SpinnerNameFood.setAdapter(new ArrayAdapter<Food>(this, R.layout.support_simple_spinner_dropdown_item, FoodsManager.foods));
        SpinnerNameFood.setSelection(0);
        SpinnerNameFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Food f = (Food)SpinnerNameFood.getSelectedItem();
                double protRestD = Profile.GetTotalProteins() - History.GetTodayTotalProteinsConsumed();
                double carRestD = Profile.GetTotalCarbohydrate() - History.GetTodayTotalCarbohydrateConsumed();
                double lipRestD = Profile.GetTotalLipids() - History.GetTodayTotalLipidConsumed();

                protRestD = protRestD < 0 ? 0 : protRestD;
                carRestD = carRestD < 0 ? 0 : carRestD;
                lipRestD = lipRestD < 0 ? 0 : lipRestD;

                double qp1 = f.Unit == FoodUnit.GR ? f.ProteinValue / 100.0 : f.ProteinValue;
                double qc1 = f.Unit == FoodUnit.GR ? f.CarbohydrateValue / 100.0 : f.CarbohydrateValue;
                double ql1 = f.Unit == FoodUnit.GR ? f.LipidValue / 100.0 : f.LipidValue;

                double calcp = (protRestD / qp1);
                double calcc = (carRestD / qc1);
                double calcl = (lipRestD / ql1);

                calcp = f.ProteinValue > 0 ? calcp : 0;
                calcc = f.CarbohydrateValue > 0 ? calcc : 0;
                calcl = f.LipidValue > 0 ? calcl : 0;

                String formatp = f.Unit == FoodUnit.GR ?  calcp <= 0 ? "impossible" : Math.round(calcp) + "gr" :  calcp <= 0 ? "impossible" : String.format("%.2f", calcp)+"unité";
                String formatc = f.Unit == FoodUnit.GR ?  calcc <= 0 ? "impossible" : Math.round(calcc) + "gr" :  calcc <= 0 ? "impossible" : String.format("%.2f", calcc)+"unité";
                String formatl = f.Unit == FoodUnit.GR ?  calcl <= 0 ? "impossible" : Math.round(calcl) + "gr" :  calcl <= 0 ? "impossible" :  String.format("%.2f", calcl)+"unité";

                valueprot.setText(Math.round(protRestD) + "gr restant ➡️ " + formatp);
                valueglucid.setText(Math.round(carRestD) + "gr restant ➡️ " + formatc);
                valuelipids.setText(Math.round(lipRestD) + "gr restant ➡️ " + formatl);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
