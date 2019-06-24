package android.example.technomedic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import static android.example.technomedic.MainActivity.symptomList;

public class InformationActivity extends AppCompatActivity {
    public static ArrayList<Disease> diseaseList=new ArrayList<Disease>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        diseaseList.clear();
        recyclerView=findViewById(R.id.info_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        diseaseList.add(new Disease("Cold","Common cold",87.87,"Acute nasopharyngitis [common cold]","General practice"));
        final DiseaseAdapter mAdapter = new DiseaseAdapter(this,diseaseList);
        recyclerView.setAdapter(mAdapter);
    }
}
