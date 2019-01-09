package oom.followdiet.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;

import oom.followdiet.Classes.FoodsManager;
import oom.followdiet.Classes.History;
import oom.followdiet.Classes.Profile;
import oom.followdiet.Classes.SaveManager;
import oom.followdiet.R;

public class StatsActivity extends AppCompatActivity {

    TextView joursrespectesv, totalprot, totalgluc, totallipid, totalkcallabel;
    RelativeLayout relative;
    FitChart fitchart1;

    int lastid = 0;

    private static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private LinearLayout GenerateTemplate(LinearLayout prev, String date, boolean sucess)
    {
        LinearLayout ly;
        prev = prev == null ? (LinearLayout)findViewById(R.id.entete) : prev;
        lastid = lastid == 0 ? 0x7f071000 : lastid; //to not override already existant id
        ly = new LinearLayout(this);
        RelativeLayout.LayoutParams pm = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pm.addRule(RelativeLayout.BELOW, prev.getId());
        pm.bottomMargin = dpToPx(5);
        ly.setLayoutParams(pm);
        ly.setOrientation(LinearLayout.HORIZONTAL);
        ly.setGravity(1);//center horizontal
        ly.setId(lastid + 1);

        TextView tvdate, tvsucc;
        tvdate = new TextView(this);
        tvsucc = new TextView(this);
        tvdate.setText(date);
        tvsucc.setText(sucess ? "\uD83E\uDD5D" : "❌");

        //weight1 1 width 0px
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        tvdate.setLayoutParams(params);
        tvsucc.setLayoutParams(params);
        tvdate.setGravity(1); //center
        tvsucc.setGravity(1); //center
        ly.addView(tvdate);
        ly.addView(tvsucc);
        lastid = ly.getId();
        return ly;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        joursrespectesv = findViewById(R.id.joursrespectesv);
        totalprot = findViewById(R.id.totalprot);
        totalgluc = findViewById(R.id.totalgluc);
        totallipid = findViewById(R.id.totallipid);
        totalkcallabel = findViewById(R.id.totalkcal);
        relative = findViewById(R.id.relative);
        fitchart1 = findViewById(R.id.fitchart1);

        boolean succed = false;
        int totalrespected = 0;
        int totalp = 0, totalg = 0, totall = 0;
        double totalkcal = 0;
        LinearLayout prev = null;
        for(String key : History.dayfoods.keySet())
        {
            succed = History.CheckDaySuccess(key);
            totalrespected = succed ? totalrespected + 1 : totalrespected;
            totalp = totalp + (int)(History.GetTodayTotalProteinsKcalConsumed(key) / 4);
            totalg = totalg + (int)(History.GetTodayTotalCarbohydrateKcalConsumed(key) / 4);
            totall = totall + (int)(History.GetTodayTotalLipidKcalConsumed(key) / 9);
            prev = GenerateTemplate(prev, key, succed);
            relative.addView(prev);
        }
        joursrespectesv.setText(totalrespected + "/" + History.dayfoods.size() + " jours");
        totalkcal = totalp * 4 + totalg * 4 + totall * 9;

        String protstr = "Total " + totalp + " gr de proteines -> ";
        String glucstr = "Total " + totalg + " gr de glucides";
        String lipistr = "Total " + totall + " gr de lipides";
        String kcalstr = "Total " + ((int)totalkcal) + " kcal";

        if (totalp >= 280000) //cheval
            protstr += "\uD83D\uDC34";
        else if (totalp >= 208000) //vache
            protstr += "\uD83D\uDC2E";
        else if (totalp >= 81000) //cochon
            protstr += "\uD83D\uDC37";
        else if (totalp >= 70840) //poulet x 230
            protstr += "\uD83D\uDC14 x 230";
        else if (totalp >= 49400) //canard x 260
            protstr += "\uD83E\uDD86 x 260";
        else if (totalp >= 34800) //jabba x 100
            protstr += "\uD83D\uDC38 x 100";
        else if (totalp >= 14760) //lapin x 90
            protstr += "\uD83D\uDC30 x 90";
        else if (totalp >= 3080) //poulet x 10
            protstr += "\uD83D\uDC14 x 10";
        else if (totalp >= 1900) //canard x 10
            protstr += "\uD83E\uDD86 x 10";
        else if (totalp >= 820) //lapin x 5
            protstr += "\uD83D\uDC30 x 5";
        else if (totalp >= 348) //jabba
            protstr += "\uD83D\uDC38";
        else if (totalp >= 308) //poulet
            protstr += "\uD83D\uDC14";
        else if (totalp >= 190) //canard
            protstr += "\uD83E\uDD86";
        else if (totalp >= 164) //lapin
            protstr += "\uD83D\uDC30";
        else
            protstr += "rien";


        fitchart1.setMinValue(0f);
        fitchart1.setMaxValue((float)History.dayfoods.size());
        fitchart1.setValue((float)totalrespected);

        totalprot.setText(protstr);
        totalgluc.setText(glucstr);
        totallipid.setText(lipistr);
        totalkcallabel.setText(kcalstr);
    }

    public void on_clearhistory(View v)
    {
        final Context context = this;
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        else
            builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer l'historique");
        builder.setMessage("Êtes vous sur de vouloir supprimer l'ensemble de votre historique de consomation journalier ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                History.Reset();
                SaveManager.SaveAll(context);
                MainActivity.Popup(context, "L'historique journalier à bien été supprimé !");
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
