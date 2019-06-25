package android.example.technomedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.example.technomedic.MainActivity.symptomList;

public class InformationActivity extends AppCompatActivity {
    public static ArrayList<Disease> diseaseList=new ArrayList<Disease>();
    RecyclerView recyclerView;
    TextView testing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent = getIntent();
        String token=intent.getStringExtra("token");
        String gender=intent.getStringExtra("gender");
        String birthyear=intent.getStringExtra("birthyear");
        String symptoms=intent.getStringExtra("symptoms");
        testing=findViewById(R.id.testing);
        diseaseList.clear();
        recyclerView=findViewById(R.id.info_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        new FetchData().execute(token,symptoms,gender,birthyear);
        final DiseaseAdapter mAdapter = new DiseaseAdapter(this,diseaseList);
        recyclerView.setAdapter(mAdapter);
    }

    public class FetchData extends AsyncTask<String, Void, String> {
        boolean flag = true;
        String result = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<Disease> arrayresult=new ArrayList<Disease>();
            String token=params[0];
            String symptoms=params[1];
            String gender=params[2];
            String birthyear=params[3];
            try {
                JSONArray array,object3;
                JSONObject object1,object2;
                URL url;


                url=new URL("https://healthservice.priaid.ch/diagnosis?symptoms="+symptoms+"&gender="+gender+"&year_of_birth="+birthyear+"&token="+token+"&format=json&language=en-gb");                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() != 200) {
                    flag = false;
                } else {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        result = result + line;
                    }
                    /*
                    array= new JSONArray(result);
                    for(int i=0;i<array.length();i++){
                        object1=array.getJSONObject(i);
                        object2=object1.getJSONObject("Issue");
                        String name=object2.getString("Name");
                        String desc=object2.getString("IcdName");
                        String proname=object2.getString("ProfName");
                        double accuracy=object2.getDouble("Accuracy");
                        object3=object1.getJSONArray("Specialisation");
                        String spec="";
                        for(int j=0;j<object3.length();j++){
                            spec+=object3.getJSONObject(i).getString("Name")+" ";
                        }
                        arrayresult.add(new Disease(name,proname,accuracy,desc,spec));
                    }
                    return arrayresult;
                    */
                    return result;

                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
            return result;
        }
        @Override
        protected void onPostExecute(String backresult) {
            super.onPostExecute(backresult);
            if (flag) {
                //diseaseList.addAll(backresult);
                testing.setText(backresult);
            } else {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        }

    }

}
