package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity3 extends AppCompatActivity {

    PieChart piechart;
    String proteins, fat, calories, sugar, carb, sodium, fiber, remProteins, remFat, remCal, remFiber, remCarb, remSugar, remSodium;
    String email="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph3);

        proteins = String.valueOf(Float.valueOf(getIntent().getStringExtra("Proteins")));
        remProteins = getIntent().getStringExtra("remainingProteins");
        fat = String.valueOf(Float.valueOf(getIntent().getStringExtra("Fat")));
        remFat = getIntent().getStringExtra("remainingFat");
        calories = getIntent().getStringExtra("Calories");
        remCal = getIntent().getStringExtra("remainingCalories");
        fiber = getIntent().getStringExtra("Fiber");
        remFiber = getIntent().getStringExtra("remainingFiber");
        carb = getIntent().getStringExtra("Carbs");
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
        piechart = (PieChart) findViewById(R.id.idPieChart3);
        float consumedSodium = (Float.valueOf(sodium)-Float.valueOf(remSodium));
        float totalSodium = Float.valueOf(sodium);
        if(consumedSodium<=totalSodium){
            final String[] xData = {"Consumed:\n"+consumedSodium+"mg", "Remaining :\n"+remSodium+"mg"};
            float[] yData = {consumedSodium*100/totalSodium,Float.valueOf(remCal)*100/totalSodium};
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
            final String[] xData = {"Over Consumed:\n"+(consumedSodium-totalSodium)+"mg"};
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

        piechart.setCenterText("Sodium consumption");
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
        if(v.getId() ==R.id.back3){
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
    }
}
