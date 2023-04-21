package org.me.gcu.armstrong_callum_s2220306;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//==================================================================================================
//Details : My Details
//
// Name                 Callum Armstrong
// Student ID           2220306
// Programme of Study   Computing
//
//==============================================================================================

public class extraInfoActivity extends AppCompatActivity {

    //Graphical componant Varibals
    private TableLayout displayTable;
    private TableLayout bearingTable;
    private TextView titles;

    //Varibals related to the hightest and lowest Magnitudes and Depths
    double mHigh;
    double mLow;
    int dHigh;
    int dLow;

    //Creating of multipul ArrayList used to store certain earthQuakes
    ArrayList<earthQuake> quakes = new ArrayList<>();
    ArrayList<earthQuake> unsortedDisplayedQuakes = new ArrayList<>();
    ArrayList<earthQuake> sortedDisplayedQuakes = new ArrayList<>();
    ArrayList<earthQuake> nearestQuakes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //finds graphical componadnts and makes them visiable
        displayTable = (TableLayout) findViewById(R.id.dataTable); displayTable.setVisibility(View.VISIBLE);
        bearingTable = (TableLayout) findViewById(R.id.bearingTable); bearingTable.setVisibility(View.VISIBLE);
        titles = (TextView) findViewById(R.id.titles); titles.setText("Click Each to see more Details!");

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            //if back button is pressed it sends the user back to the previouse activity
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent i = getIntent();//creates intent varibel

        quakes = (ArrayList<earthQuake>) i.getSerializableExtra("earthQuakes");
        //sets quakes array to the incoming arraylist of earthQuakes

        quakeBoundarys();//runs the quakeBoundarys method
        displaySorter();//runns the displaySorter method
        displayData();// runs the displpayData method
        bearingCalcDisplay();//runs the bearingcalcDisplay method
    }

    void quakeBoundarys() {

        //sets all varibals to what there value is on the first earthquake so they are not null
        //also sets all of the F = "found" booleans to false
        mHigh = Double.valueOf(quakes.get(0).getMagnitude());
        boolean mHighF = false;
        mLow = Double.valueOf(quakes.get(0).getMagnitude());
        boolean mLowF = false;

        dHigh = Integer.valueOf(quakes.get(0).getDepth());
        boolean dHighF = false;
        dLow = Integer.valueOf(quakes.get(0).getDepth());
        boolean dLowF = false;


        for (earthQuake eq : quakes) {// for earthQuakes in quakes

            //though all the loops the highest and lowest magnitude is found and set
            if (Double.valueOf(eq.getMagnitude()) > mHigh) {
                mHigh = Double.valueOf(eq.getMagnitude());
            } else if (Double.valueOf(eq.getMagnitude()) < mLow) {
                mLow = Double.valueOf(eq.getMagnitude());
            }

            //though all the loops the highest and lowest depth is found and set
            if (Integer.valueOf(eq.getDepth()) > dHigh) {
                dHigh = Integer.valueOf(eq.getDepth());
            } else if (Integer.valueOf(eq.getDepth()) < dLow) {
                dLow = Integer.valueOf(eq.getDepth());
            }

        }

        for (earthQuake eq : quakes) { // for earthQuakes in quakes

            //compares all the highest lowest values to the ones on the earthQuake.
            //if it matchs the found varibale is set to true and they are added to the
            //unsortedDisplayQuakes
            if (Double.valueOf(eq.getMagnitude()) == mHigh && !mHighF) {
                mHighF = true;

                unsortedDisplayedQuakes.add(eq);
            }
            if (Double.valueOf(eq.getMagnitude()) == mLow && !mLowF) {
                mLowF = true;

                unsortedDisplayedQuakes.add(eq);

            }
            if (Integer.valueOf(eq.getDepth()) == dHigh && !dHighF) {
                dHighF = true;

                unsortedDisplayedQuakes.add(eq);
            }
            if (Integer.valueOf(eq.getDepth()) == dLow && !dLowF) {
                dLowF = true;

                unsortedDisplayedQuakes.add(eq);
            }
        }
    }

    void displaySorter() {

        //all these for loops are to ensure that the order in the SortedDisplayQuakes
        //are High Magnitude, Low Magnitude, High Depth, Low Depth. for display purposes

        for (earthQuake eq : unsortedDisplayedQuakes) {

            if (Double.valueOf(eq.getMagnitude()) == mHigh) { sortedDisplayedQuakes.add(eq);}
        }

        for (earthQuake eq : unsortedDisplayedQuakes) {

            if (Double.valueOf(eq.getMagnitude()) == mLow)  { sortedDisplayedQuakes.add(eq);}
        }

        for (earthQuake eq : unsortedDisplayedQuakes) {

            if (Integer.valueOf(eq.getDepth()) == dHigh)  { sortedDisplayedQuakes.add(eq);}
        }

        for (earthQuake eq : unsortedDisplayedQuakes) {
            if (Integer.valueOf(eq.getDepth()) == dLow)  { sortedDisplayedQuakes.add(eq);}
        }

    }

    void displayData()
    {
        displayTable.removeAllViews();//removes all previous rows from table

        int pos = 0;

            for (earthQuake dq : sortedDisplayedQuakes) {//for each earthQuake in the sorted DisplayQuakes Array

                //====================================================
                //Setting Up the table rows
                TableRow row = new TableRow(getApplicationContext());

                //set row id to the pos in the array
                row.setId(pos);

                //creates textViews
                TextView titleText = new TextView(getApplicationContext());
                TextView dataText = new TextView(getApplicationContext());

                //formating for the TextView
                titleText.setTextSize(18);
                titleText.setGravity(0);
                titleText.setPadding(10, 20, 20, 10);
                dataText.setTextSize(18);
                dataText.setGravity(0);
                dataText.setPadding(10, 20, 20, 10);


                //====================================================
                //if the earthQuake Magnitude Value or Depth Value line up their
                //values are put in the textViews and are then added to the row and displayed on the table

                if (Double.valueOf(dq.getMagnitude()) == mHigh) {

                    titleText.setText("Highest Magnitude:");
                    dataText.setText(dq.getMagnitude());

                    row.addView(titleText);
                    row.addView(dataText);

                    displayTable.addView(row);

                }
                if (Double.valueOf(dq.getMagnitude()) == mLow) {

                    titleText.setText("Low Magnitude:");
                    dataText.setText(dq.getMagnitude());

                    row.addView(titleText);
                    row.addView(dataText);

                    displayTable.addView(row);
                }
                if (Integer.valueOf(dq.getDepth()) == dHigh) {

                    titleText.setText("Lowest Depth:");
                    dataText.setText(dq.getDepth() + "Km Underground");

                    row.addView(titleText);
                    row.addView(dataText);

                    displayTable.addView(row);

                }
                if (Integer.valueOf(dq.getDepth()) == dLow) {

                    titleText.setText("Shallowest Depth:");
                    dataText.setText(dq.getDepth() + "Km Underground");

                    row.addView(titleText);
                    row.addView(dataText);

                    displayTable.addView(row);

                }

            int finalPos = pos;
            row.setOnClickListener(new View.OnClickListener() {//if the row is clicked
                @Override
                public void onClick(View view) {

                    //creates and setsup the intent
                    Intent i = new Intent(extraInfoActivity.this, detailsActivity.class);
                    //adds in all the nessary data into the intent
                    i.putExtra("l", MainActivity.locationFormat(dq.location));
                    i.putExtra("dt", dq.getDateTime());
                    i.putExtra("m", dq.getMagnitude());
                    i.putExtra("d", dq.getDepth());
                    i.putExtra("lo", dq.getLongitude());
                    i.putExtra("la", dq.getLatitude());

                    startActivity(i);//Start of Activity

                }
            });

            pos++; //pos + 1

            }



            //formatting the table and adding another row with Text
        TableRow glsTitleRow = new TableRow(getApplicationContext());

        TextView titleText = new TextView(getApplicationContext());

        titleText.setTextSize(18);
        titleText.setGravity(0);
        titleText.setPadding(10, 20, 20, 10);
        titleText.setText("\nFrom Glasgow:");

        glsTitleRow.addView(titleText);

        displayTable.addView(glsTitleRow);

    }


    void bearingCalcDisplay()
    {
        //removes are rows from table
        bearingTable.removeAllViews();

        //Glasgow Location Setup
        Location glasgow = new Location("");
        glasgow.setLatitude(55.8642);
        glasgow.setLongitude(-4.2518);

        //Declearation of new locations for the closest NESW locations
        //there Lat and Long are set to New Zealand so they are not Null
        earthQuake nearN = quakes.get(0);
        Location qN = new Location("");
        qN.setLatitude(-40);
        qN.setLongitude(-174);
        earthQuake nearE = quakes.get(0);
        Location qE = new Location("");
        qE.equals(qN);
        earthQuake nearS = quakes.get(0);
        Location qS = new Location("");
        qS.equals(qN);
        earthQuake nearW = quakes.get(0);
        Location qW = new Location("");
        qW.equals(qN);


        for( earthQuake eq : quakes)//for each earthQuake in quakes
        {
            //Dearation of new Location
            Location quake = new Location("");
            quake.setLatitude(Double.valueOf(eq.getLatitude()));//new Location Lat set to earthQuake Lat
            quake.setLongitude(Double.valueOf(eq.getLongitude()));//new Location Long set to earthQuake Long

            int bearing = (int) glasgow.bearingTo(quake);//bearing from Glasgow is calculated

            if (bearing <= 0)//if the bearing is a negative value + 360 so it becomes a positive value
            {
                bearing = bearing + 360;
            }

            char direction = 'a';

            //finds the closest cardinal direction to the bearing
            if (bearing > 315 || bearing <= 45) {direction = 'N';}
            else if ((bearing > 45  && bearing <= 135)) {direction = 'E';}
            else if (bearing > 135 && bearing <= 225) {direction = 'S';}
            else if (bearing > 225 && bearing <= 315) {direction = 'W';}

            float tempDistance = glasgow.distanceTo(quake);//creates and sets tempDistance to the distance between glasgow and the quake

            switch(direction)//switch based of direction
            {
                case 'N' ://if north
                {
                    if(tempDistance <= glasgow.distanceTo(qN))
                       //if the tempDIstance is lower than the distance from the saves shortest distance
                        //the quake becomes the new saved shorted distance
                    {
                        qN.setLongitude(quake.getLongitude());
                        qN.setLatitude(quake.getLatitude());
                        qN.setBearing(bearing);
                        nearN = eq;
                    }

                    break;
                }
                case 'E' ://if east
                {
                    if(tempDistance <= glasgow.distanceTo(qE))
                    //if the tempDIstance is lower than the distance from the saves shortest distance
                    //the quake becomes the new saved shorted distance
                    {
                        qE.setLongitude(quake.getLongitude());
                        qE.setLatitude(quake.getLatitude());
                        qE.setBearing(bearing);
                        nearE = eq;
                    }

                    break;//if south
                }
                case 'S' :
                {
                    if(tempDistance <= glasgow.distanceTo(qS))
                    //if the tempDIstance is lower than the distance from the saves shortest distance
                    //the quake becomes the new saved shorted distance
                    {
                        qS.setLongitude(quake.getLongitude());
                        qS.setLatitude(quake.getLatitude());
                        qS.setBearing(bearing);
                        nearS = eq;
                    }

                    break;
                }
                case 'W' ://if west
                {
                    if(tempDistance <= glasgow.distanceTo(qW))
                    //if the tempDIstance is lower than the distance from the saves shortest distance
                    //the quake becomes the new saved shorted distance
                    {
                        qW.setLongitude(quake.getLongitude());
                        qW.setLatitude(quake.getLatitude());
                        qW.setBearing(bearing);
                        nearW = eq;
                    }

                    break;
                }
            }

        }
        //each of the quakes of shortest distance is added to an array
        nearestQuakes.add(nearN);
        nearestQuakes.add(nearE);
        nearestQuakes.add(nearS);
        nearestQuakes.add(nearW);

        int count = 0; // count = 0

        for (earthQuake eq: nearestQuakes)//for the amount of quakes in the array
        {
            int pos = 0;

            TableRow row = new TableRow(getApplicationContext());
            //creates new row


            //sets the rowId to the position in the array
            row.setId(pos);

            //creating the textViews
            TextView titleText1 = new TextView(getApplicationContext());
            TextView dataText1 = new TextView(getApplicationContext());

            //formating the textViews
            titleText1.setTextSize(18);
            titleText1.setGravity(0);
            titleText1.setPadding(10, 20, 20, 10);
            dataText1.setTextSize(18);
            dataText1.setGravity(0);
            dataText1.setPadding(10, 20, 20, 10);



            switch(count)
            {
                case 0 :
                {
                    //Displaying North Data
                    titleText1.setText("Nearest North Bearing:\nAt A Distance of:");
                    dataText1.setText(qN.getBearing() + "\n" + glasgow.distanceTo(qN)/1000 + "Km");
                    row.addView(titleText1);
                    row.addView(dataText1);

                    count++;
                    break;
                }
                case 1 :
                {
                    //Displaing East Data
                    titleText1.setText("Nearest East Bearing:\nAt A Distance of:");
                    dataText1.setText(qE.getBearing() + "\n" + glasgow.distanceTo(qE)/1000 + "Km");
                    row.addView(titleText1);
                    row.addView(dataText1);

                    count++;
                    break;
                }
                case 2 :
                {
                    //Displaying South Data
                    titleText1.setText("Nearest South Bearing:\nAt A Distance of:");
                    dataText1.setText(qS.getBearing() + "\n" + glasgow.distanceTo(qS)/1000 + "Km");
                    row.addView(titleText1);
                    row.addView(dataText1);

                    count++;
                    break;
                }
                case 3 :
                {
                    //Diplsaying South Data
                    titleText1.setText("Nearest West Bearing:\nAt A Distance of:");
                    dataText1.setText(qW.getBearing() + "\n" + glasgow.distanceTo(qW)/1000 + "Km");
                    row.addView(titleText1);
                    row.addView(dataText1);

                    count++;
                    break;
                }
                default:

                    titleText1.setText("Something went wrong!");
                    row.addView(titleText1);


            }

            bearingTable.addView(row);//adds new rows to the table


            int finalPos = pos;
            row.setOnClickListener(new View.OnClickListener() {//if row is clicked
                @Override
                public void onClick(View view) {

                    //sets up the intent
                    Intent i = new Intent(extraInfoActivity.this, detailsActivity.class);

                    //puts the nessary data in the Extra of the intent
                    i.putExtra("l", MainActivity.locationFormat(eq.location));
                    i.putExtra("dt", eq.getDateTime());
                    i.putExtra("m", eq.getMagnitude());
                    i.putExtra("d", eq.getDepth());
                    i.putExtra("lo", eq.getLongitude());
                    i.putExtra("la", eq.getLatitude());

                    startActivity(i);//starts new activity

                }
            });


            pos++;//pos + 1

        }
    }


}