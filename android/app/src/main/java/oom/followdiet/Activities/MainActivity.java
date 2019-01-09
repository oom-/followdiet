package oom.followdiet.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.txusballesteros.widgets.FitChart;

import java.util.Timer;
import java.util.TimerTask;

import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.Classes.Profile;
import oom.followdiet.Classes.ProgressBarAnimation;
import oom.followdiet.Classes.SaveManager;
import oom.followdiet.R;

public class MainActivity extends AppCompatActivity {

    FitChart fitChart;
    TextView datetext, textRestant, totalday, textViewProtein, textViewGLucid, textViewLipid;
    ProgressBar progressBarP, progressBarGlucid, progressBarLipid;
    ScrollView mainScrollView;
    Timer timer;
    ProgressBarAnimation progressBarAnimationP, progressBarAnimationG, progressBarAnimationL;
    Button oupsbutton, statsbutton, modifydeletealiment, eatbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fitChart = findViewById(R.id.fitchart1);
        datetext = findViewById(R.id.datetext);
        textRestant = findViewById(R.id.textRestant);
        totalday = findViewById(R.id.totalday);
        textViewProtein = findViewById(R.id.textViewProtein);
        textViewGLucid = findViewById(R.id.textViewGLucid);
        textViewLipid = findViewById(R.id.textViewLipid);
        progressBarP = findViewById(R.id.progressBarP);
        progressBarGlucid = findViewById(R.id.progressBarGlucid);
        progressBarLipid = findViewById(R.id.progressBarLipid);
        mainScrollView = findViewById(R.id.mainScrollView);
        oupsbutton = findViewById(R.id.button4);
        statsbutton = findViewById(R.id.button8);
        modifydeletealiment = findViewById(R.id.button6);
        eatbutton = findViewById(R.id.button3);
        progressBarAnimationP = new ProgressBarAnimation(progressBarP, 1000);
        progressBarAnimationG = new ProgressBarAnimation(progressBarGlucid, 1000);
        progressBarAnimationL = new ProgressBarAnimation(progressBarLipid, 1000);


    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (timer != null)
            timer.cancel();
        timer = null;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (Profile.name == null && SaveManager.SaveExisting(this))
            SaveManager.LoadAll(this);
        else
            SaveManager.SaveAll(this);
        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
        updateDisplay();
        progressBarAnimationP.refresh();
        progressBarAnimationG.refresh();
        progressBarAnimationL.refresh();
        ShowFitChart();

        //Buttons
        if (History.GetCountDay() > 0)
            oupsbutton.setEnabled(true);
        else
            oupsbutton.setEnabled(false);
        if (FoodsManager.foods.size() > 0)
        {
            modifydeletealiment.setEnabled(true);
            statsbutton.setEnabled(true);
        }
        else
        {
            modifydeletealiment.setEnabled(false);
            statsbutton.setEnabled(false);
        }

        if (FoodsManager.foods.size() > 0)
            eatbutton.setEnabled(true);
        else
            eatbutton.setEnabled(false);

        if (Profile.name == null && Profile.objectivkcal == 0 && Profile.poids == 0)
            on_profil(null);
    }

    public void updateDisplay()
    {
        textViewProtein.setText((int)History.GetTodayTotalProteinsConsumed() + "/" + (int)Profile.GetTotalProteins() + " gr");
        textViewGLucid.setText((int)History.GetTodayTotalCarbohydrateConsumed() + "/" + (int)Profile.GetTotalCarbohydrate() + " gr");
        textViewLipid.setText((int)History.GetTodayTotalLipidConsumed() + "/" + (int)Profile.GetTotalLipids() + " gr");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        datetext.setText((Profile.name == null ? "" : Profile.name + " - ") + History.GetDay());
                        textRestant.setText("Temps restant: " + History.GetRemaningTime());
                    }
                });

            }
        }, 0, 1000);

    }

    public static void Popup(Context context, String msg)
    {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void Popup(Context context, String msg, int duration)
    {
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public void ShowFitChart()
    {
        totalday.setText((int)History.GetTodayTotalKcalConsumed() +  "/" + ((int)Profile.objectivkcal) + " kcal");
        fitChart.setMinValue(0f);
        fitChart.setMaxValue((float)Profile.objectivkcal);
       /* Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue((float)History.GetTodayTotalKcalConsumed(), 0x00E676));
        Tuple[] infos = new Tuple[] {   new Tuple((float)History.GetTodayTotalProteinsKcalConsumed(), 0xFF1744),
                                        new Tuple((float)History.GetTodayTotalCarbohydrateKcalConsumed(), 0xFFEA00),
                                        new Tuple((float)History.GetTodayTotalLipidKcalConsumed(), 0x3D5AFE)};
        Arrays.sort(infos);
        for (Tuple p : infos)
            values.add(new FitChartValue(p.x, p.y));
        fitChart.setValues(values);*/
        fitChart.setValue((float)History.GetTodayTotalKcalConsumed());
        progressBarAnimationP.setMax((int)Profile.GetTotalProteins());
        progressBarAnimationP.setProgress((int)History.GetTodayTotalProteinsConsumed());
        progressBarAnimationG.setMax((int)Profile.GetTotalCarbohydrate());
        progressBarAnimationG.setProgress((int)History.GetTodayTotalCarbohydrateConsumed());
        progressBarAnimationL.setMax((int)Profile.GetTotalLipids());
        progressBarAnimationL.setProgress((int)History.GetTodayTotalLipidConsumed());
    }

    public void on_iate(View v)
    {
        Intent myIntent = new Intent(this, AddHistoryActivity.class);
        this.startActivity(myIntent);
    }

    public void on_oups(View v)
    {
        Intent myIntent = new Intent(this, ModifyDeleteHistoryActivity.class);
        this.startActivity(myIntent);
    }

    public void on_addaliment(View v)
    {
        Intent myIntent = new Intent(this, AddFoodActivity.class);
        this.startActivity(myIntent);
    }

    public void on_delaliment(View v)
    {
        Intent myIntent = new Intent(this, ModifyDeleteFoodActivity.class);
        this.startActivity(myIntent);
    }

    public void on_profil(View v)
    {
        Intent myIntent = new Intent(this, ProfileActivity.class);
        this.startActivity(myIntent);
    }

    public void on_stats(View v)
    {
        Intent myIntent = new Intent(this, StatsActivity.class);
        this.startActivity(myIntent);
    }

    public void on_filler(View view)
    {
        Intent myIntent = new Intent(this, FillerActivity.class);
        this.startActivity(myIntent);
    }
}
