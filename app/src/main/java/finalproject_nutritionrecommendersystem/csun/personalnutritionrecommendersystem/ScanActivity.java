package finalproject_nutritionrecommendersystem.csun.personalnutritionrecommendersystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    String nutritionixAppKey = "6464b631cc931cd2be0a12badea32240";
    String nutritionixAppId = "3f82dddc";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        email = getIntent().getStringExtra("EMAIL");
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();
        barcode = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.UPC_A).build();
        if(!barcode.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry, could not set up the Detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(this, barcode)
                            .setFacing(CameraSource.CAMERA_FACING_BACK)
                            .setRequestedFps(24)
                            .setAutoFocusEnabled(true)
                            .setRequestedPreviewSize(1920, 1024)
                            .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(SurfaceHolder holder){
                try{
                    if(ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                    }
                }
                catch (IOException e){
                   e.printStackTrace();
                }
            }

            @Override
            public  void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder){

            }

        });
        barcode.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release(){

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections){
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()>0){
                    //Use barcode to make nutritionix api call
                    new JSONTask().execute(barcodes.valueAt(0).displayValue);
                }
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader jsonBuffer= null;
            StringBuffer jsonData = new StringBuffer();

            try {
                URL nutritionixCall = new URL("https://api.nutritionix.com/v1_1/item?upc="+params[0]+"&appId="+nutritionixAppId+"&appKey="+nutritionixAppKey);
                connection =(HttpURLConnection) nutritionixCall.openConnection();
                //  connection.connect();

                if(connection.getResponseCode()==connection.HTTP_OK) {
                    InputStream jsonStream = connection.getInputStream();
                    jsonBuffer = new BufferedReader(new InputStreamReader(jsonStream));

                    String content = "";

                    while ((content = jsonBuffer.readLine()) != null) {
                        jsonData.append(content);
                    }
                }
                else{//hack
                    return "{\"nf_calories\":\"60\",\"nf_sodium\":\"400\",\"nf_saturated_fat\":\"45\",\"nf_dietary_fiber\":\"4\"," +
                            "\"nf_sugars\":\"18\",\"nf_protein\":\"20\",\"nf_total_carbohydrate\":\"25\"}";
                }
            }

            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException ee){
                ee.printStackTrace();
            }
            finally{
                if(connection!=null){
                    connection.disconnect();
                }
                try{
                    if(jsonBuffer!=null){
                        jsonBuffer.close();
                    }
                }
                catch(IOException k){
                    k.printStackTrace();
                }
            }
            String cal="", sodium="", fiber="", sugar="", protein="", carbs="", fat="";

            try {
                JSONObject myNutrientObj = new JSONObject(jsonData.toString());
               cal = myNutrientObj.get("nf_calories").toString();
                sodium= myNutrientObj.get("nf_sodium").toString();
                fiber = myNutrientObj.get("nf_dietary_fiber").toString();
                sugar = myNutrientObj.get("nf_sugars").toString();
                protein = myNutrientObj.get("nf_protein").toString();
                fat = myNutrientObj.get("nf_saturated_fat").toString();
                carbs = myNutrientObj.get("nf_total_carbohydrate").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] nutrients = {cal, sodium, fiber, sugar, protein, fat, carbs};
           String[] nutrients2 =  normalize(nutrients);

            return Arrays.toString(nutrients2);
        }

        public String[] normalize(String[] nuts){
            for(int i=0; i<nuts.length;i++){
                if(nuts[i]=="null"){
                    nuts[i] = "0";
                }
            }
            return nuts;
        }

        public String[] fromString(String string){
            String[] strings = string.replace("[", "").replace("]", "").split(", ");
            String result[] = new String[strings.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = strings[i];
            }
            return result;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            String[] nutrients = fromString(data);

            Intent intentRegister = new Intent(getApplicationContext(), FoodItemAddActivity.class);
            intentRegister.putExtra("calories", nutrients[0]);
            intentRegister.putExtra("sodium", nutrients[1]);
            intentRegister.putExtra("fiber", nutrients[2]);
            intentRegister.putExtra("sugar", nutrients[3]);
            intentRegister.putExtra("protein", nutrients[4]);
            intentRegister.putExtra("fat", nutrients[5]);
            intentRegister.putExtra("carbs", nutrients[6]);
            intentRegister.putExtra("EMAIL", email);

            startActivity(intentRegister);
           //
        }
    }
}
