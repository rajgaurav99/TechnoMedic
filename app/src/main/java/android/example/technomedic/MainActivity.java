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
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    static String token;
    String gender="male";

    public static ArrayList<Symptom> symptomList=new ArrayList<>();
    public static ArrayList<Symptom> symptomList2=new ArrayList<>();
    public static HashSet<Integer> searchpara=new HashSet<>();
    RecyclerView recyclerView;
    SearchView msearch;
    Button diagnose,reset;
    TextView birthyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(token==null){
            FetchData task = new FetchData(MainActivity.this);
            task.execute();
        }
        symptomList.clear();
        birthyear=findViewById(R.id.age);
        diagnose=findViewById(R.id.diagnose);
        reset=findViewById(R.id.reset);
        msearch=findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setup();
        symptomList2.addAll(symptomList);
        final SymptomAdapter mAdapter = new SymptomAdapter(this,symptomList);
        recyclerView.setAdapter(mAdapter);

        msearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return false;
            }
        } );

        diagnose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age=birthyear.getText().toString();
                    if(age.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please enter/check age", Toast.LENGTH_LONG).show();
                    }
                    else if(searchpara.size()==0){
                        Toast.makeText(getApplicationContext(), "Please enter symptoms", Toast.LENGTH_LONG).show();
                    }
                    else{
                        int birthyeardata=Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(age);
                        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                        intent.putExtra("birthyear",""+birthyeardata);
                        intent.putExtra("symptoms",searchpara.toString());
                        intent.putExtra("token",token);
                        intent.putExtra("gender",gender);
                        startActivity(intent);
                    }
            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomList2.clear();
                searchpara.clear();
                finish();
                startActivity(getIntent());
            }
        });

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
                Intent intent = new Intent(MainActivity.this, Dev.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender="male";
                    break;
            case R.id.female:
                if (checked)
                    gender="female";
                    break;
        }
    }

    public static void setup(){
        symptomList.clear();
        symptomList.add(new Symptom("Abdominal guarding",188));
        symptomList.add(new Symptom("Abdominal pain",10));
        symptomList.add(new Symptom("Abdominal pain associated with menstruation",223));
        symptomList.add(new Symptom("Absence of a pulse",984));
        symptomList.add(new Symptom("Aggressiveness",974));
        symptomList.add(new Symptom("Agitation",981));
        symptomList.add(new Symptom("Ankle deformity",996));
        symptomList.add(new Symptom("Ankle swelling",147));
        symptomList.add(new Symptom("Anxiety",238));
        symptomList.add(new Symptom("Arm pain",1009));
        symptomList.add(new Symptom("Arm swelling",971));
        symptomList.add(new Symptom("Back deformity",998));
        symptomList.add(new Symptom("Back pain",104));
        symptomList.add(new Symptom("Black stools",180));
        symptomList.add(new Symptom("Blackening of vision",57));
        symptomList.add(new Symptom("Blackhead",24));
        symptomList.add(new Symptom("Bleeding from vagina",284));
        symptomList.add(new Symptom("Bleeding in the conjunctiva of the eye",176));
        symptomList.add(new Symptom("Bloated feeling in the stomach",48));
        symptomList.add(new Symptom("Blood in stool",190));
        symptomList.add(new Symptom("Bloody cough",233));
        symptomList.add(new Symptom("Blue colored skin",991));
        symptomList.add(new Symptom("Blue spot on skin",240));
        symptomList.add(new Symptom("Blurred vision",77));
        symptomList.add(new Symptom("Bold area among hair on the head",239));
        symptomList.add(new Symptom("Bone fracture",156));
        symptomList.add(new Symptom("Breathing-related pains",250));
        symptomList.add(new Symptom("Brittleness of nails",979));
        symptomList.add(new Symptom("Bulging abdominal wall",192));
        symptomList.add(new Symptom("Burning eyes",75));
        symptomList.add(new Symptom("Burning in the throat",46));
        symptomList.add(new Symptom("Burning nose",288));
        symptomList.add(new Symptom("Burning sensation when urinating",107));
        symptomList.add(new Symptom("Changes in the nails",91));
        symptomList.add(new Symptom("Cheek swelling",170));
        symptomList.add(new Symptom("Chest pain",17));
        symptomList.add(new Symptom("Chest tightness",31));
        symptomList.add(new Symptom("Chills",175));
        symptomList.add(new Symptom("Coarsening of the skin structure",218));
        symptomList.add(new Symptom("Cold feet",89));
        symptomList.add(new Symptom("Cold hands",978));
        symptomList.add(new Symptom("Cold sweats",139));
        symptomList.add(new Symptom("Cough",15));
        symptomList.add(new Symptom("Cough with sputum",228));
        symptomList.add(new Symptom("Cramps",94));
        symptomList.add(new Symptom("Cravings",49));
        symptomList.add(new Symptom("Crusting",134));
        symptomList.add(new Symptom("Curvature of the spine",260));
        symptomList.add(new Symptom("Dark urine",108));
        symptomList.add(new Symptom("Decreased urine stream",163));
        symptomList.add(new Symptom("Delayed start to urination",165));
        symptomList.add(new Symptom("Diarrhea",50));
        symptomList.add(new Symptom("Difficult defecation",79));
        symptomList.add(new Symptom("Difficulty in finding words",126));
        symptomList.add(new Symptom("Difficulty in speaking",98));
        symptomList.add(new Symptom("Difficulty in swallowing",93));
        symptomList.add(new Symptom("Difficulty to concentrate",53));
        symptomList.add(new Symptom("Difficulty to learn",1007));
        symptomList.add(new Symptom("Difficulty with gait",1005));
        symptomList.add(new Symptom("Discoloration of nails",216));
        symptomList.add(new Symptom("Disorientation regarding time or place",128));
        symptomList.add(new Symptom("Distended abdomen",989));
        symptomList.add(new Symptom("Dizziness",207));
        symptomList.add(new Symptom("Double vision",71));
        symptomList.add(new Symptom("Double vision, acute-onset",270));
        symptomList.add(new Symptom("Dribbling after urination",162));
        symptomList.add(new Symptom("Drooping eyelid",244));
        symptomList.add(new Symptom("Drowsiness",43));
        symptomList.add(new Symptom("Dry eyes",273));
        symptomList.add(new Symptom("Dry mouth",272));
        symptomList.add(new Symptom("Dry skin",151));
        symptomList.add(new Symptom("Earache",87));
        symptomList.add(new Symptom("Early satiety",92));
        symptomList.add(new Symptom("Elbow pain",1011));
        symptomList.add(new Symptom("Enlarged calf",1006));
        symptomList.add(new Symptom("Eye blinking",242));
        symptomList.add(new Symptom("Eye pain",287));
        symptomList.add(new Symptom("Eye redness",33));
        symptomList.add(new Symptom("Eyelid swelling",208));
        symptomList.add(new Symptom("Eyelids sticking together",209));
        symptomList.add(new Symptom("Face pain",219));
        symptomList.add(new Symptom("Facial paralysis",246));
        symptomList.add(new Symptom("Facial swelling",970));
        symptomList.add(new Symptom("Fast, deepened breathing",153));
        symptomList.add(new Symptom("Fatty defecation",83));
        symptomList.add(new Symptom("Feeling faint",982));
        symptomList.add(new Symptom("Feeling ill",1014));
        symptomList.add(new Symptom("Feeling of foreign body in the eye",76));
        symptomList.add(new Symptom("Feeling of pressure in the ear",86));
        symptomList.add(new Symptom("Feeling of residual urine",164));
        symptomList.add(new Symptom("Feeling of tension in the legs",145));
        symptomList.add(new Symptom("Fever",11));
        symptomList.add(new Symptom("Finger deformity",995));
        symptomList.add(new Symptom("Finger pain",1013));
        symptomList.add(new Symptom("Finger swelling",1012));
        symptomList.add(new Symptom("Flaking skin",214));
        symptomList.add(new Symptom("Flaking skin on the head",245));
        symptomList.add(new Symptom("Flatulence",154));
        symptomList.add(new Symptom("Foot pain",255));
        symptomList.add(new Symptom("Foot swelling",1002));
        symptomList.add(new Symptom("Forgetfulness",125));
        symptomList.add(new Symptom("Formation of blisters on a skin area",62));
        symptomList.add(new Symptom("Foul smelling defecation",84));
        symptomList.add(new Symptom("Frequent urination",59));
        symptomList.add(new Symptom("Genital warts",110));
        symptomList.add(new Symptom("Hair loss",152));
        symptomList.add(new Symptom("Hallucination",976));
        symptomList.add(new Symptom("Halo",72));
        symptomList.add(new Symptom("Hand pain",186));
        symptomList.add(new Symptom("Hand swelling",148));
        symptomList.add(new Symptom("Hard defecation",80));
        symptomList.add(new Symptom("Hardening of the skin",184));
        symptomList.add(new Symptom("Headache",9));
        symptomList.add(new Symptom("Hearing loss",206));
        symptomList.add(new Symptom("Heart murmur",985));
        symptomList.add(new Symptom("Heartburn",45));
        symptomList.add(new Symptom("Hiccups",122));
        symptomList.add(new Symptom("Hip deformity",993));
        symptomList.add(new Symptom("Hip pain",196));
        symptomList.add(new Symptom("Hoarseness",121));
        symptomList.add(new Symptom("Hot flushes",149));
        symptomList.add(new Symptom("Immobilization",197));
        symptomList.add(new Symptom("Impaired balance",120));
        symptomList.add(new Symptom("Impaired hearing",90));
        symptomList.add(new Symptom("Impaired light-dark adaptation",70));
        symptomList.add(new Symptom("Impairment of male potency",113));
        symptomList.add(new Symptom("Incomplete defecation",81));
        symptomList.add(new Symptom("Increased appetite",131));
        symptomList.add(new Symptom("Increased drive",262));
        symptomList.add(new Symptom("Increased salivation",204));
        symptomList.add(new Symptom("Increased thirst",40));
        symptomList.add(new Symptom("Increased touch sensitivity",220));
        symptomList.add(new Symptom("Increased urine quantity",39));
        symptomList.add(new Symptom("Involuntary movements",257));
        symptomList.add(new Symptom("Irregular heartbeat",986));
        symptomList.add(new Symptom("Irregular mole",65));
        symptomList.add(new Symptom("Itching eyes",73));
        symptomList.add(new Symptom("Itching in the ear",88));
        symptomList.add(new Symptom("Itching in the mouth or throat",973));
        symptomList.add(new Symptom("Itching in the nose",96));
        symptomList.add(new Symptom("Itching of skin",21));
        symptomList.add(new Symptom("Itching of the anus",999));
        symptomList.add(new Symptom("Itching on head",247));
        symptomList.add(new Symptom("Itching or burning in the genital area",268));
        symptomList.add(new Symptom("Joint effusion",194));
        symptomList.add(new Symptom("Joint instability",198));
        symptomList.add(new Symptom("Joint pain",27));
        symptomList.add(new Symptom("Joint redness",230));
        symptomList.add(new Symptom("Joint swelling",193));
        symptomList.add(new Symptom("Joylessness",47));
        symptomList.add(new Symptom("Knee deformity",994));
        symptomList.add(new Symptom("Knee pain",256));
        symptomList.add(new Symptom("Leg cramps",146));
        symptomList.add(new Symptom("Leg pain",1010));
        symptomList.add(new Symptom("Leg swelling",231));
        symptomList.add(new Symptom("Leg ulcer",143));
        symptomList.add(new Symptom("Less than 3 defecations per week",82));
        symptomList.add(new Symptom("Limited mobility of the ankle",992));
        symptomList.add(new Symptom("Limited mobility of the back",167));
        symptomList.add(new Symptom("Limited mobility of the fingers",178));
        symptomList.add(new Symptom("Limited mobility of the hip",1000));
        symptomList.add(new Symptom("Limited mobility of the leg",195));
        symptomList.add(new Symptom("Lip swelling",35));
        symptomList.add(new Symptom("Lockjaw",205));
        symptomList.add(new Symptom("Loss of eye lashes",210));
        symptomList.add(new Symptom("Lower abdominal pain",174));
        symptomList.add(new Symptom("Lower-back pain",263));
        symptomList.add(new Symptom("Lump in the breast",261));
        symptomList.add(new Symptom("Malposition of the testicles",266));
        symptomList.add(new Symptom("Marked veins",232));
        symptomList.add(new Symptom("Memory gap",235));
        symptomList.add(new Symptom("Menstruation disorder",112));
        symptomList.add(new Symptom("Missed period",123));
        symptomList.add(new Symptom("Moist and softened skin",215));
        symptomList.add(new Symptom("Mood swings",85));
        symptomList.add(new Symptom("Morning stiffness",983));
        symptomList.add(new Symptom("Mouth pain",135));
        symptomList.add(new Symptom("Mouth ulcers",97));
        symptomList.add(new Symptom("Muscle pain",177));
        symptomList.add(new Symptom("Muscle stiffness",119));
        symptomList.add(new Symptom("Muscle weakness",987));
        symptomList.add(new Symptom("Muscular atrophy in the leg",252));
        symptomList.add(new Symptom("Muscular atrophy of the arm",202));
        symptomList.add(new Symptom("Muscular weakness in the arm",168));
        symptomList.add(new Symptom("Muscular weakness in the leg",253));
        symptomList.add(new Symptom("Nausea",44));
        symptomList.add(new Symptom("Neck pain",136));
        symptomList.add(new Symptom("Neck stiffness",234));
        symptomList.add(new Symptom("Nervousness",114));
        symptomList.add(new Symptom("Night cough",133));
        symptomList.add(new Symptom("Night sweats",1004));
        symptomList.add(new Symptom("Non-healing skin wound",63));
        symptomList.add(new Symptom("Nosebleed",38));
        symptomList.add(new Symptom("Numbness in the arm",221));
        symptomList.add(new Symptom("Numbness in the leg",254));
        symptomList.add(new Symptom("Numbness of the hands",200));
        symptomList.add(new Symptom("Oversensitivity to light",137));
        symptomList.add(new Symptom("Overweight",157));
        symptomList.add(new Symptom("Pain in the bones",155));
        symptomList.add(new Symptom("Pain in the calves",142));
        symptomList.add(new Symptom("Pain in the limbs",12));
        symptomList.add(new Symptom("Pain of the anus",990));
        symptomList.add(new Symptom("Pain on swallowing",203));
        symptomList.add(new Symptom("Pain radiating to the arm",251));
        symptomList.add(new Symptom("Pain radiating to the leg",103));
        symptomList.add(new Symptom("Pain when chewing",286));
        symptomList.add(new Symptom("Painful defecation",189));
        symptomList.add(new Symptom("Painful urination",109));
        symptomList.add(new Symptom("Pallor",150));
        symptomList.add(new Symptom("Palpitations",37));
        symptomList.add(new Symptom("Paralysis",140));
        symptomList.add(new Symptom("Physical inactivity",118));
        symptomList.add(new Symptom("Problems with the sense of touch in the face",129));
        symptomList.add(new Symptom("Problems with the sense of touch in the feet",130));
        symptomList.add(new Symptom("Protrusion of the eyes",258));
        symptomList.add(new Symptom("Purulent discharge from the urethra",172));
        symptomList.add(new Symptom("Purulent discharge from the vagina",173));
        symptomList.add(new Symptom("Rebound tenderness",191));
        symptomList.add(new Symptom("Reduced appetite",54));
        symptomList.add(new Symptom("Ringing in the ear",78));
        symptomList.add(new Symptom("Runny nose",14));
        symptomList.add(new Symptom("Sadness",975));
        symptomList.add(new Symptom("Scalp redness",269));
        symptomList.add(new Symptom("Scar",1001));
        symptomList.add(new Symptom("Sensitivity to cold",60));
        symptomList.add(new Symptom("Sensitivity to glare",69));
        symptomList.add(new Symptom("Sensitivity to noise",102));
        symptomList.add(new Symptom("Shiny red tongue",264));
        symptomList.add(new Symptom("Shortness of breath",29));
        symptomList.add(new Symptom("Side pain",183));
        symptomList.add(new Symptom("Skin lesion",26));
        symptomList.add(new Symptom("Skin nodules",25));
        symptomList.add(new Symptom("Skin rash",124));
        symptomList.add(new Symptom("Skin redness",61));
        symptomList.add(new Symptom("Skin thickening",217));
        symptomList.add(new Symptom("Skin wheal",34));
        symptomList.add(new Symptom("Sleepiness with spontaneous falling asleep",241));
        symptomList.add(new Symptom("Sleeplessness",52));
        symptomList.add(new Symptom("Sneezing",95));
        symptomList.add(new Symptom("Sore throat",13));
        symptomList.add(new Symptom("Sputum",64));
        symptomList.add(new Symptom("Stomach burning",179));
        symptomList.add(new Symptom("Stress-related leg pain",185));
        symptomList.add(new Symptom("Stuffy nose",28));
        symptomList.add(new Symptom("Sweating",138));
        symptomList.add(new Symptom("Swelling in the genital area",236));
        symptomList.add(new Symptom("Swelling of the testicles",267));
        symptomList.add(new Symptom("Swollen glands in the armpit",248));
        symptomList.add(new Symptom("Swollen glands in the groin",249));
        symptomList.add(new Symptom("Swollen glands in the neck",169));
        symptomList.add(new Symptom("Tears",211));
        symptomList.add(new Symptom("Testicular pain",222));
        symptomList.add(new Symptom("Tic",243));
        symptomList.add(new Symptom("Tingling",201));
        symptomList.add(new Symptom("Tiredness",16));
        symptomList.add(new Symptom("Toe deformity",997));
        symptomList.add(new Symptom("Toe swelling",1003));
        symptomList.add(new Symptom("Tongue burning",980));
        symptomList.add(new Symptom("Tongue swelling",977));
        symptomList.add(new Symptom("Toothache",1008));
        symptomList.add(new Symptom("Tremor at rest",115));
        symptomList.add(new Symptom("Tremor on movement",132));
        symptomList.add(new Symptom("Trouble understanding speech",988));
        symptomList.add(new Symptom("Unconsciousness, short",144));
        symptomList.add(new Symptom("Uncontrolled defecation",265));
        symptomList.add(new Symptom("Underweight",116));
        symptomList.add(new Symptom("Urge to urinate",160));
        symptomList.add(new Symptom("Urination during the night",161));
        symptomList.add(new Symptom("Vision impairment",68));
        symptomList.add(new Symptom("Vision impairment for far objects",213));
        symptomList.add(new Symptom("Vision impairment for near objects",166));
        symptomList.add(new Symptom("Visual field loss",66));
        symptomList.add(new Symptom("Vomiting",101));
        symptomList.add(new Symptom("Vomiting blood",181));
        symptomList.add(new Symptom("Weakness or numbness on right or left side of body",972));
        symptomList.add(new Symptom("Weight gain",23));
        symptomList.add(new Symptom("Weight loss",22));
        symptomList.add(new Symptom("Wheezing",30));
        symptomList.add(new Symptom("Wound",187));
        symptomList.add(new Symptom("Yellow colored skin",105));
        symptomList.add(new Symptom("Yellowish discoloration of the white part of the eye",106));
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchData extends AsyncTask<Void, Void, String> {
        boolean flag = true;
        StringBuffer response = new StringBuffer();
        String result="";
        private ProgressDialog dialog;

        public FetchData(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Acquiring Token from Server");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONObject object1;
            try {
                URL url;
                url=new URL("https://authservice.priaid.ch/login");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty ("Authorization", "Bearer "+"w2A5E_GMAIL_COM_AUT:QjTcb4ukdxjyblwlvI17Bw==");
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
                    object1= new JSONObject(response.toString());
                    result= object1.getString("Token");
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.content), e.toString(), Snackbar.LENGTH_LONG);
                snackbar.show();
                flag = false;
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (flag) {
                token=result;
                Snackbar snackbar = Snackbar.make(findViewById(R.id.content), "Token Successfully Acquired", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
            }
        }

    }
}
