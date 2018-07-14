package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Array;
import java.util.ArrayList;

public class GraphActivity2 extends AppCompatActivity {

    BarChart barChart;
    String proteins, calories, fat, sugar, carb, sodium, fiber, remProteins, remFat, remCal, remFiber, remCarb, remSugar, remSodium;
    String email="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);

        proteins = getIntent().getStringExtra("Proteins");
        remProteins = getIntent().getStringExtra("remainingProteins");
        fat = getIntent().getStringExtra("Fat");
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

        setupBarChart();
    }

    private void setupBarChart(){
        barChart = (BarChart) findViewById(R.id.idBarChart);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);

        ArrayList <BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, Float.parseFloat(proteins)));
        barEntries.add(new BarEntry(2, Float.parseFloat(sugar)));
        barEntries.add(new BarEntry(3, Float.parseFloat(fiber)));
        barEntries.add(new BarEntry(4, Float.parseFloat(carb)));
        barEntries.add(new BarEntry(5, Float.parseFloat(fat)));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Nutrient consumption");
        barDataSet.setColors(colors);

        ArrayList <BarEntry> barEntries1 = new ArrayList<>();

        barEntries1.add(new BarEntry(1, Float.parseFloat(proteins)-Float.parseFloat(remProteins)));
        barEntries1.add(new BarEntry(2, Float.parseFloat(sugar)-Float.parseFloat(remSugar)));
        barEntries1.add(new BarEntry(3,Float.parseFloat(fiber)- Float.parseFloat(remFiber)));
        barEntries1.add(new BarEntry(4, Float.parseFloat(carb)-Float.parseFloat(remCarb)));
        barEntries1.add(new BarEntry(5, Float.parseFloat(fat)-Float.parseFloat(remFat)));

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "");
        barDataSet1.setColors(Color.RED);

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        dataSets.add(barDataSet1);
        barDataSet.setValueTextSize(8.5f);
        barDataSet1.setValueTextSize(8.5f);
        BarData barData = new BarData(barDataSet1, barDataSet);

        barChart.setData(barData);
        Description descr = new Description();
        descr.setText(" Proteins(g)       Sugar(g)          Fiber(g)           Carbs(g)               Fat(g)        ");
        descr.setTextColor(Color.BLACK);
        descr.setTextSize(10.5f);
        barChart.setDescription(descr);
        barData.setBarWidth(0.8f);
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter{
        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return mValues[(int)value];
        }
    }

    public void onClick(View v){
        if(v.getId()==R.id.nextView2){
            Intent nextView=new Intent(getApplicationContext(),GraphActivity3.class);
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
        else if(v.getId() ==R.id.back2){
            Intent backToMain=new Intent(getApplicationContext(),GraphActivity.class);
            backToMain.putExtra("remainingProteins", remProteins);
              backToMain.putExtra("remainingFat", remFat);
            backToMain.putExtra("remainingFiber", remFiber);
            backToMain.putExtra("remainingSugar", remSugar);
            backToMain.putExtra("remainingSodium", remSodium);
            backToMain.putExtra("remainingCarbs", remCarb);
            backToMain.putExtra("remainingCalories", remCal);
            backToMain.putExtra("EMAIL",email);
            backToMain.putExtra("ID",id);
            backToMain.putExtra("Proteins", proteins);
              backToMain.putExtra("Fat", fat);
            backToMain.putExtra("Fiber", fiber);
            backToMain.putExtra("Sugar",sugar);
            backToMain.putExtra("Sodium", sodium);
            backToMain.putExtra("Carbohydrates", carb);
            backToMain.putExtra("Calories", calories);
            startActivity(backToMain);
        }
    }
}
