package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import model.User;
import model.UserCalorieCount;
import sql.DatabaseHelper;

import static finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem.R.id.textInputEditTextEmail;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView greeting;
    String email="",name="";
    DatabaseHelper databaseHelper=new DatabaseHelper();
    TextView cal,pro,fiber,sugar, sodium, carb;
    int id;
    UserCalorieCount userCalCount;
    User user;
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView=(NestedScrollView) findViewById(R.id.nestedScrollView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //main activity page-->Welcome Page
        email = getIntent().getStringExtra("EMAIL");
        id=databaseHelper.getId(email);
        intViews();
        name=databaseHelper.getNameFromID(id);
        greeting=(TextView)findViewById(R.id.Greeting);
        greeting.setText("Welcome "+name);
        userCalCount=databaseHelper.fetchPreviousValue(id);
        user=databaseHelper.fetchUserDetails(id);
        updateTableFields(userCalCount,user);
        //
    }

    private void intViews() {
        cal=(TextView)findViewById(R.id.remainingCal);
        pro=(TextView)findViewById(R.id.remainingPro);
        fiber=(TextView)findViewById(R.id.remainingfiber);
        sugar=(TextView)findViewById(R.id.remainingsugar);
        sodium=(TextView)findViewById(R.id.remainingsodium);
        carb=(TextView)findViewById(R.id.remainingcarb);
    }


    private void updateTableFields(UserCalorieCount userCalCount, User user) {
        if(userCalCount.getTotal_cal()>user.getMax_cal()){
            cal.setBackgroundColor(Color.RED);
            cal.setTextColor(Color.WHITE);
            cal.setText("+ "+String.valueOf(userCalCount.getTotal_cal()-user.getMax_cal()));
        }
        else {
            cal.setText(String.valueOf(userCalCount.getTotal_cal()));
        }
        if(userCalCount.getTotal_protein()>user.getMax_protein()){
            pro.setBackgroundColor(Color.RED);
            pro.setTextColor(Color.WHITE);
            pro.setText("+ "+String.valueOf((float)(userCalCount.getTotal_protein()-user.getMax_protein())));
        }
        else {
            pro.setText(String.valueOf((float)userCalCount.getTotal_protein()));
        }
        if(userCalCount.getTotal_fiber()>user.getMax_fiber()){
            fiber.setBackgroundColor(Color.RED);
            fiber.setTextColor(Color.WHITE);
            fiber.setText("+ "+String.valueOf(userCalCount.getTotal_fiber()-user.getMax_fiber()));
        }
        else {
            fiber.setText(String.valueOf(userCalCount.getTotal_fiber()));
        }
        if(userCalCount.getTotal_sugar()>user.getMax_sugar()){
            sugar.setBackgroundColor(Color.RED);
            sugar.setTextColor(Color.WHITE);
            sugar.setText("+ "+String.valueOf(userCalCount.getTotal_sugar()-user.getMax_sugar()));
        }
        else {
            sugar.setText(String.valueOf(userCalCount.getTotal_sugar()));
        }
        if(userCalCount.getTotal_sodium()>user.getMax_sodium()){
            sodium.setBackgroundColor(Color.RED);
            sodium.setTextColor(Color.WHITE);
            sodium.setText("+ "+String.valueOf(userCalCount.getTotal_sodium()-user.getMax_sodium()));
        }
        else {
            sodium.setText(String.valueOf(userCalCount.getTotal_sodium()));
        }
        if(userCalCount.getTotal_carb()>user.getMax_carb()){
            carb.setBackgroundColor(Color.RED);
            carb.setTextColor(Color.WHITE);
            carb.setText("+ "+String.valueOf(userCalCount.getTotal_carb()-user.getMax_carb()));
        }
        else {
            carb.setText(String.valueOf(userCalCount.getTotal_carb()));
        }

        StringBuilder notification=new StringBuilder("You have consumed extra ");
        int i=0;
        if((user.getMax_cal()-userCalCount.getTotal_cal())<0){
            notification.append("- Calories");
            i=i+1;
        }
        if((user.getMax_protein()-userCalCount.getTotal_protein())<0){
            notification.append("- Proteins");
            i=i+1;
        }
        if((user.getMax_fiber()-userCalCount.getTotal_fiber())<0){
            notification.append("- Fiber");
            i=i+1;
        }
        if((user.getMax_sugar()-userCalCount.getTotal_sugar())<0){
            notification.append("- Sugar");
            i=i+1;
        }
        if((user.getMax_sodium()-userCalCount.getTotal_sodium())<0){
            notification.append("- Sodium");
            i=i+1;
        }
        if((user.getMax_carb()-userCalCount.getTotal_carb())<0){
            notification.append("- Carbs");
            i=i+1;
        }
        if(i>0){
            Snackbar.make(nestedScrollView,notification,Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.todaycount) {
            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            intentMain.putExtra("EMAIL",email);
            startActivity(intentMain);
        } else if (id == R.id.profile) {
            Intent intentProfileView=new Intent(getApplicationContext(),ProfileViewAndRecalculate.class);
            intentProfileView.putExtra("ID",email);
            startActivity(intentProfileView);

        } else if(id == R.id.deleteUser){
            User user=new User();
            user.setId(databaseHelper.getId(email));
            databaseHelper.deleteUser(user);
            Intent loginActivity=new Intent(getApplicationContext(),Login.class);
            startActivity(loginActivity);
        }
        else if(id == R.id.logout){
            finish();
            moveTaskToBack(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onClick(View v) {
        if(v.getId()==R.id.appCompatButtonMeal){
            Intent addItem=new Intent(getApplicationContext(),FoodItemAddActivity.class);
            addItem.setAction(Intent.ACTION_SEARCH);
            addItem.setType(Intent.ACTION_VIEW);
            addItem.putExtra("EMAIL",email);
            startActivity(addItem);
        }
        else if(v.getId()== R.id.nextView){
            Intent graphView = new Intent(getApplicationContext(), GraphActivity.class);
            UserCalorieCount maxCount = databaseHelper.fetchMaxNutrients(id);
            graphView.putExtra("Proteins", String.valueOf(maxCount.getTotal_protein()));
            graphView.putExtra("remainingProteins", String.valueOf(user.getMax_protein()-userCalCount.getTotal_protein()));
            graphView.putExtra("Fiber", String.valueOf(maxCount.getTotal_fiber()));
            graphView.putExtra("remainingFiber", String.valueOf(user.getMax_fiber()-userCalCount.getTotal_fiber()));
            graphView.putExtra("Sugar", String.valueOf(maxCount.getTotal_sugar()));
            graphView.putExtra("remainingSugar", String.valueOf(user.getMax_sugar()-userCalCount.getTotal_sugar()));
            graphView.putExtra("Sodium", String.valueOf(maxCount.getTotal_sodium()));
            graphView.putExtra("remainingSodium", String.valueOf(user.getMax_sodium()-userCalCount.getTotal_sodium()));
            graphView.putExtra("Carbohydrates", String.valueOf(maxCount.getTotal_carb()));
            graphView.putExtra("remainingCarbs", String.valueOf(user.getMax_carb()-userCalCount.getTotal_carb()));
            graphView.putExtra("Calories", String.valueOf(maxCount.getTotal_cal()));
            graphView.putExtra("remainingCalories", String.valueOf(user.getMax_cal()-userCalCount.getTotal_cal()));
            graphView.putExtra("EMAIL",email);
            graphView.putExtra("ID",String.valueOf(id));
            startActivity(graphView);
        }

    }

}
