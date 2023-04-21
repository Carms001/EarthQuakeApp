package org.me.gcu.armstrong_callum_s2220306;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

//==================================================================================================
//Details : My Details
//
// Name                 Callum Armstrong
// Student ID           2220306
// Programme of Study   Computing
//
//==============================================================================================

public class searchDateActivity extends AppCompatActivity {

    //creates varibels for the graphical components
    private TableLayout searchResults;
    private TextView searchItem;
    private TextView locationDateSwapper;

    private Button backBtn;


    ArrayList<earthQuake> datedEarthQuakes = new ArrayList<>();//creates a new array called datedEarthQuakes

    String date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchResults = (TableLayout) findViewById(R.id.searchResults);//finds the graphic compondsa
        backBtn = (Button) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            //if the back button is clicked it will send the user to the previouse activty
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();//creates intent varible

        datedEarthQuakes = (ArrayList<earthQuake>) i.getSerializableExtra("searchResults");
        //sets datedQuakes to the incomeing arrrayList

        searchItem = (TextView) findViewById(R.id.itemSearched);//finds the TextView
        locationDateSwapper = (TextView) findViewById(R.id.locationDateSwap); 

        searchItem.setText(i.getStringExtra("date"));//sets searchItem to the incoming date value
        locationDateSwapper.setText("Location");//sets TextView Text

        tableMethod();//Calls Table Method

    }

    void tableMethod() {
        int pos = 0;

        for (earthQuake eQ : datedEarthQuakes) {//for each earthQuake in datedEarthQuakes

            //====================================================
            //Setting Up the table rows
            TableRow row = new TableRow(getApplicationContext());

            searchResults.setDividerPadding(10);

            //set row id to the pos in the array
            row.setId(pos);

            //creates textViews
            TextView locText = new TextView(getApplicationContext());
            TextView magText = new TextView(getApplicationContext());
            TextView depthText = new TextView(getApplicationContext());


            //formating for the TextView
            locText.setTextSize(18);
            locText.setGravity(2);
            locText.setPadding(0, 10, 0, 10);
            magText.setTextSize(18);
            magText.setGravity(2);
            magText.setPadding(0, 10, 0, 10);
            depthText.setTextSize(18);
            depthText.setGravity(2);
            depthText.setPadding(0, 10, 0, 10);


            //====================================================
            //Setting Up the color of the row in accordance to the Magnitude value

            switch (eQ.getMagnitude().charAt(0)) {

                case ('6'): {
                    row.setBackgroundColor(Color.parseColor("#e06666"));
                    locText.setTextColor(Color.parseColor("#ffffff"));
                    break;
                }

                case ('5'):
                case ('4'): {
                    row.setBackgroundColor(Color.parseColor("#e69138"));
                    break;
                }

                case ('3'): {
                    row.setBackgroundColor(Color.parseColor("#f1c232"));
                    break;
                }
                case ('2'): {
                    row.setBackgroundColor(Color.parseColor("#ffe599"));
                    break;
                }

                case ('1'): {
                    row.setBackgroundColor(Color.parseColor("#93c47d"));
                    break;
                }
                case ('0'): {
                    row.setBackgroundColor(Color.parseColor("#d9ead3"));
                    break;
                }
                default: {
                    break;
                }

            }

            String depthString = eQ.getDepth().trim() + "Km";
            //sets depthString to the depth and removes any spaces on either side

            //sets the text for each textView
            locText.setText(MainActivity.locationFormat(eQ.getLocation()));
            magText.setText(eQ.getMagnitude());
            depthText.setText(depthString);

            //add the texviews to the row
            row.addView(locText);
            row.addView(magText);
            row.addView(depthText);
            //adding the row to the table
            searchResults.addView(row);


            int finalPos = pos;
            row.setOnClickListener(new View.OnClickListener() {//if the row is clicked
                @Override
                public void onClick(View view) {

                    //creates and setsup the intent
                    Intent i = new Intent(searchDateActivity.this, detailsActivity.class);
                    //adds in all the nessary data into the intent
                    i.putExtra("l", MainActivity.locationFormat(eQ.location));
                    i.putExtra("dt", eQ.getDateTime());
                    i.putExtra("m", eQ.getMagnitude());
                    i.putExtra("d", eQ.getDepth());
                    i.putExtra("lo", eQ.getLongitude());
                    i.putExtra("la", eQ.getLatitude());

                    startActivity(i);

                }
            });
            {

            }

            pos++; //pos + 1

        }
    }

}
