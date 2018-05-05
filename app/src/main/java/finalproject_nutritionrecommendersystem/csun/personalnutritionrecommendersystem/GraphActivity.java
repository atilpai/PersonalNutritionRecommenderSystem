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
    String proteins, calories, sugar, carb, sodium, fiber, remProteins, remCal, remFiber, remCarb, remSugar, remSodium;
    String email="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        proteins = String.valueOf(Float.valueOf(getIntent().getStringExtra("Proteins")));
        remProteins = getIntent().getStringExtra("remainingProteins");
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
        final String[] xData = {"Proteins:\n"+proteins,"Sugar:\n"+sugar,"Sodium(mg):\n"+sodium,
                "Fiber:\n"+fiber,"Carbs:\n"+carb,"Calories(kCal):\n"+calories};
        float[] yData = {16f,16f,16f,16f,16f,17f};
        final String[] yData2 = {remProteins, remSugar, remSodium, remFiber, remCarb,remCal};

        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i=0; i<yData.length;i++){
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.CYAN);


        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(2);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(0f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setHighlightEnabled(true);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);

        piechart = (PieChart) findViewById(R.id.idPieChart);
        piechart.setData(data);

        piechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos = (int) h.getX();
                Toast.makeText(GraphActivity.this,"Remaining "+ xData[pos].substring(0,xData[pos].indexOf(':')) + ": "+yData2[pos], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        piechart.setCenterText("Max consumable Nutrient breakdown");
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
            nextView.putExtra("remainingFiber", remFiber);
            nextView.putExtra("remainingSugar", remSugar);
            nextView.putExtra("remainingSodium", remSodium);
            nextView.putExtra("remainingCarbs", remCarb);
            nextView.putExtra("remainingCalories", remCal);
            nextView.putExtra("EMAIL",email);
            nextView.putExtra("ID",id);
            nextView.putExtra("Proteins", proteins);
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
