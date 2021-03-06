package android.example.technomedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


@SuppressWarnings("ALL")
public class InformationActivity extends AppCompatActivity {
    public static  ArrayList<Disease> diseaseList=new ArrayList<>();
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
        FetchData task = new FetchData(InformationActivity.this);
        task.execute(token,symptoms,gender,birthyear);
        recyclerView=findViewById(R.id.info_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiseaseAdapter(this,diseaseList);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_dev:
                Intent intent = new Intent(InformationActivity.this, Dev.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchData extends AsyncTask<String, Void, ArrayList<Disease>> {
        boolean flag = true;
        StringBuffer response=new StringBuffer();
        private ProgressDialog dialog;

        public FetchData(InformationActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading results");
            dialog.show();
        }

        @Override
        protected ArrayList<Disease> doInBackground(String... params) {
            ArrayList<Disease> arrayresult=new ArrayList<>();
            String token=params[0];
            String symptoms=params[1];
            String gender=params[2];
            String birthyear=params[3];
            JSONArray array,object3;
            JSONObject object1,object2;
            try {
                URL url;
                url=new URL("https://healthservice.priaid.ch/diagnosis?symptoms="+symptoms+"&gender="+gender+"&year_of_birth="+birthyear+"&token="+token+"&format=json&language=en-gb");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if (httpURLConnection.getResponseCode() != 200) {
                    flag = false;
                } else {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while (line != null) {
                        line = bufferedReader.readLine();
                        response.append(line);
                    }

                    array= new JSONArray(response.toString());
                    for(int i=0;i<array.length();i++){
                        object1=array.getJSONObject(i);
                        object2=object1.getJSONObject("Issue");
                        String name=object2.getString("Name");
                        String desc=object2.getString("IcdName");
                        String proname=object2.getString("ProfName");
                        double accuracy=object2.getDouble("Accuracy");
                        object3=object1.getJSONArray("Specialisation");
                        StringBuffer spec=new StringBuffer();
                        for(int j=0;j<object3.length();j++){
                            spec.append((j+1)+". "+object3.getJSONObject(j).getString("Name")+"\n");
                        }
                        arrayresult.add(new Disease(name,proname,accuracy,desc,spec.toString()));
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
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(backresult.size()==0){
                Toast.makeText(getApplicationContext(), "Invalid combinaton of Symptoms", Toast.LENGTH_LONG).show();
                finish();
            }
            if (flag) {
                diseaseList.addAll(backresult);
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
                finish();
            }
        }

    }

}
