package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class Animation extends AppCompatActivity {

    private ImageView img;
    String remProteins, remCal, remFiber, remCarb, remSugar, remSodium, email, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //change

        remProteins = getIntent().getStringExtra("remainingProteins");
        remCal = getIntent().getStringExtra("remainingCalories");
        remFiber = getIntent().getStringExtra("remainingFiber");
        remCarb = getIntent().getStringExtra("remainingCarbs");
        remSugar = getIntent().getStringExtra("remainingSugar");
        remSodium = getIntent().getStringExtra("remainingSodium");

        email = getIntent().getStringExtra("EMAIL");
        id=getIntent().getStringExtra("ID");

        if((Integer.valueOf(remCal)<0 || Double.valueOf(remCarb)<0.0)&&(Double.valueOf(remSugar)<0.0 || Integer.valueOf(remSodium)<0)){
            setContentView(R.layout.animation_m_thintofat_red);
            img= (ImageView) findViewById(R.id.mtfr) ;
        }
        else if(Double.valueOf(remSugar)<0.0 || Integer.valueOf(remSodium)<0){
            setContentView(R.layout.animation_m_red);
            img= (ImageView) findViewById(R.id.mr) ;
        }
        else if(Integer.valueOf(remCal)<0 || Double.valueOf(remCarb)<0.0 || Double.valueOf(remSugar)<0.0){
            setContentView(R.layout.animation_m_thintofat);
            img= (ImageView) findViewById(R.id.mtf) ;
        }


        else {
            setContentView(R.layout.defaultanimation);
            img = (ImageView) findViewById(R.id.da);
        }

        img.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable)img.getBackground()).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        if(v.getId()==R.id.back){
            Intent backToMain=new Intent(getApplicationContext(),MainActivity.class);
            backToMain.putExtra("EMAIL",email);
            backToMain.putExtra("ID",id);
            startActivity(backToMain);
        }
    }
}
