package org.me.gcu.armstrong_callum_s2220306;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//==================================================================================================
//Details : My Details
//
// Name                 Callum Armstrong
// Student ID           2220306
// Programme of Study   Computing
//
//==============================================================================================

public class detailsActivity extends AppCompatActivity {


    //Creates Strings used in Activity
    String location;
    String dateTime;
    String magnitude;
    String depth;
    String lat;
    String log;

    //creates a textView Varibal
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        textView = (TextView) findViewById(R.id.description);//finds the textView

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
            //if the back button is pressed it will return the user to the previouse actvity
        });


        Bundle extras = getIntent().getExtras();//get any extras from the intent
        if (extras != null) {//if the extras arnt Null

            //assigns each varible to its account extra
            location = extras.getString("l");
            dateTime = extras.getString("dt");
            magnitude = extras.getString("m");
            lat = extras.getString("la");
            log = extras.getString("lo");
            depth = extras.getString("d");


        }

        //Setup of String that displays data
        String text =
                location + "\n" +
                dateTime + "\n" +
                magnitude + "\n" +
                lat + "\n" +
                log + "\n" +
                depth + "km";

        textView.setText(text);//sets the TextView to the String so it can be displayed
    }

}