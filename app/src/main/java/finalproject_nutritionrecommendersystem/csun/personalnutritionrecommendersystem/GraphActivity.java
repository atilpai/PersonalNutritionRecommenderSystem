package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    PieChart piechart;
    String proteins, fat, calories, sugar, carb, sodium, fiber, remProteins, remFat, remCal, remFiber, remCarb, remSugar, remSodium;
    String email="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        proteins = String.valueOf(Float.valueOf(getIntent().getStringExtra("Proteins")));
        remProteins = getIntent().getStringExtra("remainingProteins");
        fat = String.valueOf(Float.valueOf(getIntent().getStringExtra("Fat")));
        remFat = getIntent().getStringExtra("remainingFat");
        calories = getIntent().getStringExtra("Calories");
        remCal = getIntent().getStringExtra("remainingCalories");
        fiber = getIntent().getStringExtra("Fiber");
        remFiber = getIntent().getStringExtra("remainingFiber");
        carb = getIntent().getStringExtra("Carbohydrates");
        remCarb = getIntent().getStringExtra("remainingCarbs");
        sugar = getIntent().getStringExtra("Sugar");
        remSugar = getIntent().getStringExtra("remainingSugar");
        sodium = getIntent().getStringExtra("Sodium");
        remSodium = getIntent().getStringExtra("remainingSodium");

        email = getIntent().getStringExtra("EMAIL");
        id=getIntent().getStringExtra("ID");

        setupPieChart();
    }

    private void setupPieChart(){
        piechart = (PieChart) findViewById(R.id.idPieChart);
        float consumedCalories = (Float.valueOf(calories)-Float.valueOf(remCal));
        float totalCal = Float.valueOf(calories);
        if(consumedCalories<=totalCal){
            final String[] xData = {"Consumed:\n"+consumedCalories+"kcal", "Remaining :\n"+remCal+"kcal"};
            float[] yData = {consumedCalories*100/totalCal,Float.valueOf(remCal)*100/totalCal};
            List<PieEntry> pieEntries = new ArrayList<>();
            for(int i=0; i<yData.length;i++){
                pieEntries.add(new PieEntry(yData[i], xData[i]));
            }
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);

            PieDataSet dataSet = new PieDataSet(pieEntries, "");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(2);
            dataSet.setSelectionShift(5f);
            dataSet.setValueTextSize(0f);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setHighlightEnabled(true);
            PieData data = new PieData(dataSet);
            piechart.setData(data);
        }
        else{
            final String[] xData = {"Over Consumed:\n"+(consumedCalories-totalCal)+"kcal"};
            float[] yData = {100f};
            List<PieEntry> pieEntries = new ArrayList<>();
            for(int i=0; i<yData.length;i++){
                pieEntries.add(new PieEntry(yData[i], xData[i]));
            }
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);

            PieDataSet dataSet = new PieDataSet(pieEntries, "");
            dataSet.setColors(colors);
            dataSet.setSliceSpace(2);
            dataSet.setSelectionShift(5f);
            dataSet.setValueTextSize(0f);
            dataSet.setValueTextColor(Color.BLACK);
            dataSet.setHighlightEnabled(true);
            PieData data = new PieData(dataSet);
            piechart.setData(data);
        }

        piechart.setCenterText("Calories consumption");
        piechart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
        piechart.setEntryLabelColor(Color.BLACK);
        piechart.setEntryLabelTextSize(13.5f);
        piechart.setHoleRadius(35f);
        piechart.setTransparentCircleAlpha(0);
        piechart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        piechart.getDescription().setEnabled(false);
        piechart.animateY(1000);
        piechart.invalidate();
    }

    public void onClick(View v){
        if(v.getId()==R.id.nextView){
            Intent nextView=new Intent(getApplicationContext(),GraphActivity2.class);
            nextView.putExtra("remainingProteins", remProteins);
            nextView.putExtra("remainingFat", remFat);
            nextView.putExtra("remainingFiber", remFiber);
            nextView.putExtra("remainingSugar", remSugar);
            nextView.putExtra("remainingSodium", remSodium);
            nextView.putExtra("remainingCarbs", remCarb);
            nextView.putExtra("remainingCalories", remCal);
            nextView.putExtra("EMAIL",email);
            nextView.putExtra("ID",id);
            nextView.putExtra("Proteins", proteins);
            nextView.putExtra("Fat", fat);
            nextView.putExtra("Fiber", fiber);
            nextView.putExtra("Sugar",sugar);
            nextView.putExtra("Sodium", sodium);
            nextView.putExtra("Carbs", carb);
            nextView.putExtra("Calories", calories);
            startActivity(nextView);
        }
        else if(v.getId() ==R.id.back){
            Intent backToMain=new Intent(getApplicationContext(),MainActivity.class);
            backToMain.putExtra("EMAIL",email);
            backToMain.putExtra("ID",id);
            startActivity(backToMain);
        }
    }
}
