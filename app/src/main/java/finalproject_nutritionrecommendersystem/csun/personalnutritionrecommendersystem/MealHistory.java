package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import sql.DatabaseHelper;

public class MealHistory extends AppCompatActivity {

    ListView listView;
    DatabaseHelper databaseHelper = new DatabaseHelper();
    ArrayList<String> mealItems = new ArrayList();
    int userID;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_history);

        listView = (ListView) findViewById(R.id.mealHistoryListView);
        userID = getIntent().getIntExtra("userId",1);
        initDatabaseFetch();
    }

    private void initDatabaseFetch(){
        mealItems = databaseHelper.getMealHistory(userID);
        adapter = new ArrayAdapter(MealHistory.this, android.R.layout.simple_list_item_1, mealItems);
        listView.setAdapter(adapter);
    }
}
