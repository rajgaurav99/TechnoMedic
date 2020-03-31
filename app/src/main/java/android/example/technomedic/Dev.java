package android.example.technomedic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

public class Dev extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        ImageView kaggle,linkedin,github;

    }
    public void show_kaggle(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/rajgaurav9"));
        startActivity(browserIntent);
    }
    public void show_github(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/rajgaurav99"));
        startActivity(browserIntent);
    }
    public void show_linkedin(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/rajgaurav99/"));
        startActivity(browserIntent);
    }
}
