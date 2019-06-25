package android.example.technomedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class InformationActivity extends AppCompatActivity {
    public static  ArrayList<Disease> diseaseList=new ArrayList<Disease>();
    RecyclerView recyclerView;
    DiseaseAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Intent intent = getIntent();
        String token=intent.getStringExtra("token");
        String gender=intent.getStringExtra("gender");
        String birthyear=intent.getStringExtra("birthyear");
        String symptoms=intent.getStringExtra("symptoms");
        diseaseList.clear();
        new FetchData().execute(token,symptoms,gender,birthyear);
        recyclerView=findViewById(R.id.info_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiseaseAdapter(this,diseaseList);
        recyclerView.setAdapter(mAdapter);
    }

    public class FetchData extends AsyncTask<String, Void, ArrayList<Disease>> {
        boolean flag = true;
        String result = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.content), "Loading Results", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        @Override
        protected ArrayList<Disease> doInBackground(String... params) {
            ArrayList<Disease> arrayresult=new ArrayList<Disease>();
            String token=params[0];
            String symptoms=params[1];
            String gender=params[2];
            String birthyear=params[3];
            JSONArray array,object3;
            JSONObject object1,object2;
            try {
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
                            spec+=(j+1)+". "+object3.getJSONObject(j).getString("Name")+"\n";
                        }
                        arrayresult.add(new Disease(name,proname,accuracy,desc,spec));
                    }

                     return arrayresult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
            return arrayresult;
        }
        @Override
        protected void onPostExecute(ArrayList<Disease> backresult) {
            super.onPostExecute(backresult);
            if (flag) {
                diseaseList.addAll(backresult);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        }

    }

}
