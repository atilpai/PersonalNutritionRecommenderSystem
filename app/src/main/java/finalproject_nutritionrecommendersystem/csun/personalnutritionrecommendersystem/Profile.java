package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;

import Helpers.InputValidation;
import model.User;
import sql.DatabaseHelper;

public class Profile extends AppCompatActivity {

    private final AppCompatActivity activity = Profile.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout phone;
    private TextInputLayout age;
    private TextInputLayout weight;
    private TextInputLayout height;
    private TextInputLayout heightIn;
    private TextInputLayout sex;

    private TextInputEditText textPhone;
    private TextInputEditText textAge;
    private TextInputEditText textWeight;
    private TextInputEditText textHeight;
    private TextInputEditText textHeightIn;
    private TextInputEditText textSex;

    private AppCompatButton appCompatButtonCompleteProfile;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;
    String name,email,psswd;
    private Double max_carb= Double.valueOf(0),max_protein= Double.valueOf(0),max_fiber= Double.valueOf(0), max_sugar= Double.valueOf(0), max_fat= Double.valueOf(0);
    private Integer max_cal=0, max_sodium=0;
    private Double activityLevelSelection = 1.5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initListeners() {

    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        phone=(TextInputLayout)findViewById(R.id.phone);
        age=(TextInputLayout)findViewById(R.id.age);
        weight=(TextInputLayout)findViewById(R.id.weight);
        height=(TextInputLayout)findViewById(R.id.height);
        heightIn=(TextInputLayout)findViewById(R.id.heightIn);
        sex=(TextInputLayout)findViewById(R.id.sex);

        textPhone=(TextInputEditText) findViewById(R.id.textphone);
        textAge=(TextInputEditText) findViewById(R.id.textage);
        textWeight=(TextInputEditText) findViewById(R.id.textweight);
        textHeight=(TextInputEditText) findViewById(R.id.textheight);
        textHeightIn=(TextInputEditText) findViewById(R.id.textheightIn);
        textSex = (TextInputEditText) findViewById(R.id.textSex);

        appCompatButtonCompleteProfile = (AppCompatButton) findViewById(R.id.appCompatButtonCompleteProfile);

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void activityLevel(View v){
        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.la: activityLevelSelection=1.45;
            case R.id.ma: activityLevelSelection=1.55;
            case R.id.va: activityLevelSelection=1.75;
        }
    }

    public void onClick(View v) {

                verifyFromHelper();
                email = getIntent().getStringExtra("EMAIL");
                psswd=getIntent().getStringExtra("PSSWD");
                name=getIntent().getStringExtra("NAME");
                //Receive in array list
                populateUserObject();
                user=databaseHelper.calculateRequiredValues(user, activityLevelSelection);
                databaseHelper.addUser(user);
                databaseHelper.addPerDayCounter(user);
                databaseHelper.saveToUserTable(user);
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                mainActivity.putExtra("ID",databaseHelper.getId(email));
                mainActivity.putExtra("EMAIL",email);
                mainActivity.putExtra("NAME",name);
                startActivity(mainActivity);

    }


    private void populateUserObject() {
        user=new User();
        user.setPassword(psswd);
        user.setEmail(email);
        user.setHeight(Double.parseDouble(textHeight.getEditableText().toString() + "." + textHeightIn.getEditableText().toString()));
        user.setWeight(Double.parseDouble(textWeight.getEditableText().toString()));
        user.setPhone(textPhone.getEditableText().toString());
        user.setSex(textSex.getEditableText().toString());
        user.setAge(Integer.parseInt(textAge.getEditableText().toString()));
        user.setMax_cal(max_cal);
        user.setMax_fiber(max_fiber);
        user.setMax_protein(max_protein);
        user.setMax_fat(max_fat);
        user.setMax_carb(max_carb);
        user.setMax_sodium(max_sodium);
        user.setMax_sugar(max_sugar);
        user.setName(name);
    }

    private void verifyFromHelper() {

        //Check if filled
        if (!inputValidation.isInputEditTextFilled(textPhone, phone, getString(R.string.error_message_phone))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textAge, age, getString(R.string.error_message_age))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textWeight, weight, getString(R.string.error_message_weight))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textHeight, height, getString(R.string.error_message_height))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textHeightIn, heightIn, getString(R.string.error_message_height))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textSex, sex, getString(R.string.error_message_sex))) {
            return;
        }
        /*// check format
        if(!inputValidation.isInputEditTextPhoneNumber(textPhone,phone,getString(R.string.error_message_weight))){
           return;
        }*/
       /* if(!inputValidation.isInputEditTextSex(textSex,sex,getString(R.string.error_message_weight))){
            return;
        }*/


    }




}
