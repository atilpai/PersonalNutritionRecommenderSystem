package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.Manifest;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

import model.ItemNutrient;
import model.UserCalorieCount;
import sql.DatabaseHelper;

public class FoodItemAddActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private DatabaseHelper databaseHelper=new DatabaseHelper();
    private UserCalorieCount userCalCount;
    int id, flag=0;
    SearchView simpleSearchView;
    ListView list;
    ListViewAdaptor adapter;
    String email, cal, sodium, sugar, carbs, fiber, protein;
    Barcode barcode;
    String selItem;
    ItemNutrient itemNutrient;
    TextInputLayout addQuan;
    TextInputEditText addQuantity;
    TextView calorieValue,proteinValue,fiberValue,sugarValue, sodiumValue, carbValue;
    //Barcode Scanner initiation
    TextView result;
    public static  final int REQUEST_CODE = 100;
    public  static  final int PERMISSION_REQUEST =200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_item_add);
        addQuan=(TextInputLayout) findViewById(R.id.quantity);
        addQuantity=(TextInputEditText) findViewById(R.id.AddQuantity);
        calorieValue=(TextView) findViewById(R.id.caloriesValue);
        proteinValue=(TextView)findViewById(R.id.protiensValue);
        fiberValue=(TextView) findViewById(R.id.fiberValue);
        sugarValue=(TextView) findViewById(R.id.sugarValue);
        sodiumValue=(TextView) findViewById(R.id.sodiumValue);
        carbValue=(TextView) findViewById(R.id.carbValue);
        email = getIntent().getStringExtra("EMAIL");
        cal = getIntent().getStringExtra("calories");
        sodium = getIntent().getStringExtra("sodium");
        sugar = getIntent().getStringExtra("sugar");
        fiber =getIntent().getStringExtra("fiber");
        protein = getIntent().getStringExtra("protein");
        carbs = getIntent().getStringExtra("carbs");
//        barcode = getIntent().getParcelableExtra("barcode");
        id=databaseHelper.getId(email);
        simpleSearchView=(SearchView)findViewById(R.id.searchView);
        simpleSearchView.setQueryHint("Search Food items");

        if(cal !=null) {
            calorieValue.setText(cal);
            carbValue.setText(carbs);
            fiberValue.setText(fiber);
            sugarValue.setText(sugar);
            sodiumValue.setText(sodium);
            proteinValue.setText(protein);
        }
        else{
            flag=2;
        }
            SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        simpleSearchView.setSearchableInfo(

                searchManager.getSearchableInfo(new ComponentName(this,FoodItemAddActivity.class)));

        ArrayList<String> items=databaseHelper.getResults();

        list = (ListView) findViewById(R.id.listview);
        adapter = new ListViewAdaptor(this, items);

        list.setAdapter(adapter);
        simpleSearchView.setOnQueryTextListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selItem = (String) list.getItemAtPosition(position);
               // simpleSearchView.setQueryHint(selItem);
                simpleSearchView.setQuery(selItem,false);
                list.setVisibility(View.INVISIBLE);


            }
        });

    }

    public void onClick(View v) {

        userCalCount = databaseHelper.fetchPreviousValue(id);
        userCalCount.setId(id);
        if(v.getId()==R.id.appCompatButtonCalculate) {
            flag=1;
            itemNutrient = databaseHelper.fetchValuesForItem(selItem.substring(0,selItem.indexOf(":")));

            calorieValue.setText(String.valueOf(itemNutrient.getCalories()));
            proteinValue.setText(String.valueOf(itemNutrient.getProteins()));
            fiberValue.setText(String.valueOf(itemNutrient.getFiber()));
            sodiumValue.setText(String.valueOf(itemNutrient.getSodium()));
            sugarValue.setText(String.valueOf(itemNutrient.getSugar()));
            carbValue.setText(String.valueOf(itemNutrient.getCarb()));
        }

        if(v.getId()==R.id.appCompatDone) {
            if(flag==1) {
                if (addQuantity.getText().toString() != null) {
                    Integer n = Integer.parseInt(addQuantity.getText().toString());
                    userCalCount.setTotal_cal((userCalCount.getTotal_cal() + (itemNutrient.getCalories() * n)));
                    userCalCount.setTotal_fiber(userCalCount.getTotal_fiber() + (itemNutrient.getFiber() * n));
                    userCalCount.setTotal_protein(userCalCount.getTotal_protein() + (itemNutrient.getProteins() * n));
                    userCalCount.setTotal_sugar(userCalCount.getTotal_sugar() + (itemNutrient.getSugar() * n));
                    userCalCount.setTotal_sodium(userCalCount.getTotal_sodium() + (itemNutrient.getSodium() * n));
                    userCalCount.setTotal_carb(userCalCount.getTotal_carb() + (itemNutrient.getCarb() * n));
                } else {
                    calculateNewValues();
                }
                databaseHelper.updateUserCalorieCountTable(userCalCount);
                flag=0;
            }
            else if(flag==2){
                Intent intentRegister = new Intent(getApplicationContext(), MainActivity.class);
                intentRegister.putExtra("EMAIL", email);
                startActivity(intentRegister);
            }
            else{
                userCalCount.setTotal_cal(userCalCount.getTotal_cal() +Integer.valueOf(cal));
                userCalCount.setTotal_fiber(userCalCount.getTotal_fiber() +Double.valueOf(fiber));
                userCalCount.setTotal_protein(userCalCount.getTotal_protein() +Double.valueOf(protein));
                userCalCount.setTotal_sugar(userCalCount.getTotal_sugar() +Double.valueOf(sugar));
                userCalCount.setTotal_sodium(userCalCount.getTotal_sodium() +Integer.valueOf(sodium));
                userCalCount.setTotal_carb(userCalCount.getTotal_carb() +Double.valueOf(carbs));

                databaseHelper.updateUserCalorieCountTable(userCalCount);
            }
            Intent intentRegister = new Intent(getApplicationContext(), MainActivity.class);
            intentRegister.putExtra("EMAIL", email);
            startActivity(intentRegister);
        }

    }

    public void onClick1(View v){
        if(v.getId()==R.id.scanBarCode){
            //result = (TextView)findViewById(R.id.result);

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
            }

            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
            intent.putExtra("EMAIL", email);
            startActivity(intent);
        }
    }

//    @Override
//    protected void onActivityResult(int  requestCode, int resultCode, Intent data){
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
//            if(data!=null){
//                final Barcode barcode = data.getParcelableExtra("barcode");
//                result.post(new Runnable() {
//                    @Override
//                    public void run() {
//                     //   result.setText(barcode.displayValue);
//                        calorieValue.setText(barcode.getCode());
//                    }
//                });
//            }
//        }
//    }

    private void calculateNewValues() {
        userCalCount.setTotal_cal((userCalCount.getTotal_cal()+itemNutrient.getCalories()));
        userCalCount.setTotal_fiber(userCalCount.getTotal_fiber()+itemNutrient.getFiber());
        userCalCount.setTotal_protein(userCalCount.getTotal_protein()+itemNutrient.getProteins());
        userCalCount.setTotal_sugar(userCalCount.getTotal_sugar()+itemNutrient.getSugar());
        userCalCount.setTotal_sodium(userCalCount.getTotal_sodium()+itemNutrient.getSodium());
        userCalCount.setTotal_carb(userCalCount.getTotal_carb()+itemNutrient.getCarb());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text =newText;
        adapter.filter(text);
        return false;
    }
}
