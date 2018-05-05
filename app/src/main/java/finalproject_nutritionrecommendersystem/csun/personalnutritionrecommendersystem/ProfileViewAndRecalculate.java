package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import model.User;
import sql.DatabaseHelper;

public class ProfileViewAndRecalculate extends AppCompatActivity {
    String idStr;
    DatabaseHelper databaseHelper;
    User user;
    AppCompatButton appCompatButtonRecal;
    TableLayout UserDetails;
    EditText height,weight,age;
    TextView name, email,address,phone,sex, maxCal,maxPro,maxFiber, maxSodium, maxSugar, maxCarb;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view_and_recalculate);
        idStr=getIntent().getStringExtra("ID");
        initObjects();
        id=databaseHelper.getId(idStr);
        user=databaseHelper.fetchUserDetails(id);
        initViews();
        initListeners();
        setFields(user);

    }

    private void initObjects() {
        user=new User();
        databaseHelper=new DatabaseHelper();
    }

    private void initListeners() {

    }

    private void initViews() {
        UserDetails=(TableLayout)findViewById(R.id.UserDetails);
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        address=(TextView)findViewById(R.id.address);
        phone=(TextView)findViewById(R.id.phone);
        sex=(TextView)findViewById(R.id.sex);
        maxCal=(TextView)findViewById(R.id.calories);
        maxPro=(TextView)findViewById(R.id.protein);
        maxFiber=(TextView)findViewById(R.id.fiber);
        maxSodium=(TextView)findViewById(R.id.sodium);
        maxSugar=(TextView)findViewById(R.id.sugar);
        maxCarb=(TextView)findViewById(R.id.carb);
        height=(EditText)findViewById(R.id.height);
        weight=(EditText)findViewById(R.id.weight);
        age=(EditText)findViewById(R.id.Age);
        setFields(user);
        appCompatButtonRecal = (AppCompatButton) findViewById(R.id.appCompatButtonRecal);
    }

    private void setFields(User user) {
        name.setText(user.getName());
        email.setText(idStr);
        phone.setText(user.getPhone());
        sex.setText(user.getSex());
        maxCal.setText(String.valueOf(user.getMax_cal()));
        maxPro.setText(String.valueOf(user.getMax_protein()));
        maxFiber.setText(String.valueOf(user.getMax_fiber()));
        maxSodium.setText(String.valueOf(user.getMax_sodium()));
        maxSugar.setText(String.valueOf(user.getMax_sugar()));
        maxCarb.setText(String.valueOf(user.getMax_carb()));
        age.setText(String.valueOf(user.getAge()));
        weight.setText(String.valueOf(user.getWeight()));
        height.setText(String.valueOf(user.getHeight()));

    }
    public void onClick(View v) {
        if(v.getId()==R.id.appCompatButtonRecal){
            user.setHeight(Double.valueOf(height.getEditableText().toString().trim()));
            user.setWeight(Double.valueOf(weight.getEditableText().toString().trim()));
            user.setAge(Integer.valueOf(age.getEditableText().toString().trim()));
            user.setId(id);
            user=databaseHelper.calculateRequiredValues(user);
            databaseHelper.saveToUserTable(user);
            setFields(user);

        }
    }
}
