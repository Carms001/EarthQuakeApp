package org.me.gcu.armstrong_callum_s2220306;
//==================================================================================================
//Imports

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

//==================================================================================================
//Details : My Details
//
// Name                 Callum Armstrong
// Student ID           2220306
// Programme of Study   Computing
//
//==============================================================================================
//App Setup: Variables/Graphical Components

public class MainActivity extends AppCompatActivity implements OnClickListener {
    //========================
    //Buttons
    //Creates Buttons Veribals
    private Button startButton;
    private Button filterBtn;
    private Button magSortBtn;
    private Button dateSortBtn;
    private Button dateSearch;
    private Button searchFilterbtn;
    private Button locationSearch;
    private Button depthSortBtn;
    private Button extraInfoBtn;
    //========================
    //TextViews
    //These are for manipulatable TextViews
    private TextView filterBtnContext;
    private TextView filterBy;
    private TextView searchError;
    private TextView searchContext;
    //========================
    //EditTexts
    //Creates editable textbars useing EditText
    private EditText searchBar;
    //========================
    //Tables
    //Creates a tables using tableLayout
    private TableLayout filterTable;
    private TableLayout dataTable;
    private TableLayout searchTable;
    private TableLayout startTable;

    private Spinner locationSpinner;
    //========================
    //Boolean Checkers
    boolean magSorted = false;
    boolean dateSorted = true;
    boolean filterBtnPress = false;
    boolean searchBtnPress = false;
    boolean locationSpinnerSetUp = false;
    boolean depthSored = false;
    //========================
    //Strings
    private String result;
    private String url1 = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    //Creates the veribals that stores the URL  for the Xml Data
    //========================
    //Arrays
    ArrayList<earthQuake> earthQuakes = new ArrayList<earthQuake>();//arraylist of earthquakes

    //=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //======================================================
        //Finds all the Tables so they can manipulated
        dataTable = findViewById(R.id.dataTable);
        searchTable = findViewById(R.id.searchTable);
        filterTable = findViewById(R.id.filterTable);
        startTable = findViewById(R.id.startTable);

        // Set up the raw links to the graphical components
        //======================================================
        //Get and Show Data Button
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startProgress();//StartsProgress Method Call
                tableMethod(0);
                //Sets Elements Visabilty so those that need to aprear and those who dont Disapear.
                filterBtn.setVisibility(View.VISIBLE);
                searchFilterbtn.setVisibility(View.VISIBLE);
                startTable.setVisibility(View.GONE);
                extraInfoBtn.setVisibility((View.VISIBLE));

            }
        });
        //======================================================
        //Show ExtraInfo Button
        extraInfoBtn = (Button) findViewById(R.id.extraInfoBtn);
        extraInfoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), extraInfoActivity.class);
                //Sets the destiniation for the intent.
                i.putExtra("earthQuakes", earthQuakes);
                //Passes though earthquakes Array to ExtraInfo Activity
                startActivity(i);
                //Starts Activity


            }
        });

        //======================================================
        //Show Search Button
        searchFilterbtn = (Button) findViewById(R.id.searchFilterBtn);
        searchFilterbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContext = (TextView) findViewById(R.id.searchContext);
                searchBar = (EditText) findViewById(R.id.searchBar);

                if (!searchBtnPress) {
                    //if the Search Filter button is pressed elements become visiable
                    searchTable.setVisibility(View.VISIBLE);
                    searchBtnPress = true;//Sets the searchBtnPress boolen to be false
                    dateSearch.setVisibility(View.VISIBLE);
                    locationSearch.setVisibility(View.VISIBLE);
                    searchFilterbtn.setText("Hide Search"); //Changes the text on the button

                    if (!locationSpinnerSetUp)//if Location Spinner is not setup
                    {
                        lSpinnerSetUp();//sets up the Location Spinner
                    }

                    locationSpinnerSetUp = true;//sets the Spiiner Setup Boolean to be true


                } else {
                    //if the search filter button has already be pressed the element will Disapear from screen
                    searchTable.setVisibility(View.GONE);
                    searchBtnPress = false;
                    dateSearch.setVisibility(View.GONE);
                    locationSearch.setVisibility(View.GONE);
                    searchFilterbtn.setText("Search Options");
                }
            }
        });
        //======================================================
        //Show Filters Button
        filterBtn = (Button) findViewById(R.id.filterBtn);//Finds the Filter buttons
        filterBy = (TextView) findViewById(R.id.filteredBy);
        filterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!filterBtnPress) {//if not already pressed
                    filterShow();
                } else {//if already Pressed
                    filterHide();
                }
            }
        });
        //======================================================
        //Magnitude Filter Button
        magSortBtn = (Button) findViewById(R.id.magSortBtn);
        magSortBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tableMethod(1);
            }//starts tableMethod with 1 as sort
        });
        //======================================================
        //Date Filter Button
        dateSortBtn = (Button) findViewById(R.id.dateSortBtn);
        dateSortBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tableMethod(2);
            }//starts tableMethod with 2 as sort
        });
        //======================================================
        //Depth Depth Button
        depthSortBtn = (Button) findViewById(R.id.depthSortBtn);
        depthSortBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tableMethod(3);
            }//starts tableMethod with 3 as sort
        });
        //======================================================
        //Date Search Button
        dateSearch = (Button) findViewById(R.id.dateSearch);
        dateSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                searchMethod(searchBar.getText().toString());//starts searchMethod with
                //the inputted Date

            }
        });
        //======================================================
        //Location Search Button
        locationSearch = (Button) findViewById(R.id.locationSearch);
        locationSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            spinnerSearch(locationSpinner.getSelectedItem().toString());//starts spinnerSearch with
                //the input being the selected location from the spinner
            }
        });

    }
    void filterShow() {
        //Will make all the filters elements aprear
        filterBtnContext = (TextView) findViewById(R.id.filterBtnContext);//finding the filter Table
        filterTable.setVisibility(View.VISIBLE);
        magSortBtn.setVisibility(View.VISIBLE);
        dateSortBtn.setVisibility(View.VISIBLE);
        depthSortBtn.setVisibility(View.VISIBLE);
        filterBtn.setText("Hide Filter");//changing button text

        filterBtnPress = true;//sets boolean to true

    }

    void filterHide() {
        //hides all the filter elements
        filterTable.setVisibility(View.GONE);
        magSortBtn.setVisibility(View.GONE);
        dateSortBtn.setVisibility(View.GONE);
        depthSortBtn.setVisibility(View.GONE);
        filterBtn.setText("Filter Options");//changes button text

        filterBtnPress = false;//sets boolean to false
    }

    //==============================================================================================
    //Parsing Data from XML into the Quake Class

    void parserMethod() {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//creating pull parser
            factory.setNamespaceAware(true);
            XmlPullParser xmlpp = factory.newPullParser(); //creates a new PullParser names xmlpp
            xmlpp.setInput(new StringReader(result)); //reads in result which is what is storing the XML data

            int eventType = xmlpp.getEventType(); //this is saying that this is the start of the xml type
            earthQuake q = new earthQuake();
            String tag = "";

            //Setup: Separating String
            String inString = ""; //to store the whole string temporarily
            String outString = ""; // the Eventual New String after separation
            int markerCount = 0; // used for operating the string using ':'
            int charCount = 0; //used for storing character place
            char currentChar = 0; //used to keep track of the current Char number.
            int desCount = 0; //counts Description so that the first this skipped

            while (eventType != XmlPullParser.END_DOCUMENT)// Keep Looping until we have reached the end of the document
            {
                if (eventType == XmlPullParser.START_TAG)// If the XML Start Tag is detected.
                {
                    tag = xmlpp.getName(); //sets Tag to the Current Tag
                    charCount = 0;


                    switch (tag) {//Switch Statement
                        case ("item")://if tag = item

                            q = new earthQuake();//Creates New Quake


                            break;

                        case ("description"):// if tag = description

                            if (desCount == 0) { // if descriptionCount = 0
                                desCount++; // adds one to the decCount

                                /* this is used to remove the first 2 items in the XML file as they,
                                are not needed as they do not have any relevant data to the application
                                this is done by checking how many descriptions have gone by as only one
                                of the non needed items has a description.
                                this if statement means that it is skipped. */

                                break;

                            } else {
                                xmlpp.next();//Moves to Text
                                //===========================================
                                //Separating String
                                //Action: dateTime

                                    /*
                                    This works as the text inside each XML element is the same
                                    this is why i am using it to separate the strings out to there
                                    component data eg, Location, Date/Time ect
                                     */
                                inString = xmlpp.getText();// sets inString to the data inside the XML Element
                                currentChar = inString.charAt(charCount);

                                while (currentChar != ':')//Loops until it finds that character

                                {
                                    currentChar = inString.charAt(charCount++);
                                    //sets the character to be compared to the next character in the string
                                }

                                charCount++; //chatCount + 1

                                while (inString.charAt(charCount + 1) != ';') {
                                    //While: the character at charCount != ;
                                    outString = outString + inString.charAt(charCount);
                                    currentChar = inString.charAt(++charCount);
                                }

                                outString.trim();//Removes any would be spaces from the new |string

                                q.setDateTime(outString); //sets the the Quakes DateTime to the new string

                                outString = ""; //Clears outString for the next Use

                                //===========================================
                                //Action: Location

                                while (currentChar != ':')//Loops until it finds that character

                                {
                                    currentChar = inString.charAt(charCount++);
                                    //sets the character to be compared to the next character in the string
                                }

                                charCount++;//charCount + 1

                                while (inString.charAt(charCount + 1) != ';') {
                                    //While: the character at charCount != ;
                                    outString = outString + inString.charAt(charCount);
                                    currentChar = inString.charAt(charCount++);
                                }

                                outString.trim();// removes any would be Space at the start and end of the string

                                q.setLocation(outString);//sets quakes location to outstring

                                outString = ""; //clears outstring to prepare for next use

                                //===========================================
                                //Action: Depth

                                while (currentChar != ':' || markerCount != 1)
                                //Loops until it finds that character or markerCount = 1

                                {
                                    if (currentChar == ':') {//if the character : is detected
                                        markerCount++;//markerCount + 1
                                    }
                                    currentChar = inString.charAt(charCount++);
                                    //sets the character to be compared to the next character in the string
                                }

                                markerCount = 0; //sets markerCount = 0
                                charCount++;//charCount + 1

                                while (inString.charAt(charCount) != 'k') {
                                    //While: the character at charCount != ;
                                    outString = outString + inString.charAt(charCount);
                                    currentChar = inString.charAt(charCount++);
                                }

                                outString.trim(); //removes any would be Space at the start and end of the string

                                q.setDepth(outString);//setsDepth to outString

                                outString = "";

                                //===========================================
                                //Action: Magnitude

                                while (currentChar != ':')//Loops until it finds that character

                                {
                                    currentChar = inString.charAt(charCount++);

                                    //sets the character to be compared to the next character in the string
                                }

                                outString = ""; //Clears OutString

                                charCount++;//charCOunt + 2
                                charCount++;

                                while (charCount != inString.length()) {
                                    //Loops until the end of the String
                                    outString = outString + inString.charAt(charCount);
                                    currentChar = inString.charAt(charCount++);
                                }

                                outString.trim(); //Removes any Spaces from the start and end of the string

                                q.setMagnitude(outString);

                                outString = "";


                                break;
                            }

                        case "lat":

                            xmlpp.next();//Moves to Text
                            //===========================================
                            //Setup: Separating String
                            inString = xmlpp.getText(); //to store the whole string temporarily

                            q.setLatitude(inString);

                            break;

                        case "long":

                            xmlpp.next();//Moves to Text
                            //===========================================
                            //Setup: Separating String
                            inString = xmlpp.getText(); //to store the whole string temporarily

                            q.setLongitude(inString);

                            earthQuakes.add(q);
                            System.out.println();


                            break;

                        default:
                            xmlpp.next();//Goes to the next element

                    }

                }

                if (eventType == XmlPullParser.END_TAG)//If an End Tag is Detected
                {

                }

                eventType = xmlpp.next();//moves to next
            }

        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("XML Parsing Successful");

    }

    @Override
    public void onClick(View view) {

    }

    //==============================================================================================
    //Magnitude Filter Classes
    class magCompareASE implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            magSorted = true;//Sets Mag Sorted to True

            if (Double.valueOf(q1.getMagnitude()) == Double.valueOf(q2.getMagnitude()))
                //If the 2 Values are the same return 0
                return 0;
            else if (Double.valueOf(q1.getMagnitude()) > Double.valueOf(q2.getMagnitude()))
                //if value 1 is greater than value to return 1 else return -1
                return 1;
            else
                return -1;

        }
    }

    class magCompareDES implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            magSorted = false;//Sets Mag Sorted to False


            if (Double.valueOf(q1.getMagnitude()) == Double.valueOf(q2.getMagnitude()))
                //If the 2 Values are the same return 0
                return 0;
            else if (Double.valueOf(q1.getMagnitude()) < Double.valueOf(q2.getMagnitude()))
                //if value 1 is greater than value to return 1 else return -1

                return 1;
            else
                return -1;

        }
    }


    class dateCompareASE implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            dateSorted = true;//Sets Date Sorted to True


            if (dateFormat(q1.getDateTime()) == dateFormat(q2.getDateTime()))
                //If the 2 Values are the same return 0
                return 0;
            else if (dateFormat(q1.getDateTime()).compareTo(dateFormat(q2.getDateTime())) > 0)
                //if value 1 is greater than value to return 1 else return -1

                return 1;
            else
                return -1;

        }
    }

    class dateCompareDES implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            dateSorted = false;//Sets Date Sorted to False



            if (dateFormat(q1.getDateTime()) == dateFormat(q2.getDateTime()))
                //If the 2 Values are the same return 0
                return 0;
            else if (dateFormat(q1.getDateTime()).compareTo(dateFormat(q2.getDateTime())) < 0)
                //if value 1 is greater than value to return 1 else return -1
                return 1;
            else
                return -1;

        }
    }

    class depthCompareASE implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            depthSored = true;//Sets Depth Sorted to True

            if (Double.valueOf(q1.getDepth()) == Double.valueOf(q2.getDepth()))
                //If the 2 Values are the same return 0
                return 0;
            else if (Double.valueOf(q1.getDepth()) > Double.valueOf(q2.getDepth()))
                //if value 1 is greater than value to return 1 else return -1
                return 1;
            else
                return -1;

        }
    }

    class depthCompareDES implements Comparator<earthQuake> {
        public int compare(earthQuake q1, earthQuake q2) {
            depthSored = false; //Sets Depth Sorted to False


            if (Double.valueOf(q1.getDepth()) == Double.valueOf(q2.getDepth()))
                //If the 2 Values are the same return 0
                return 0;
            else if (Double.valueOf(q1.getDepth()) < Double.valueOf(q2.getDepth()))
                //if value 1 is greater than value to return 1 else return -1
                return 1;
            else
                return -1;

        }
    }
    //==============================================================================================
    //Location Spinner SetUp
    void lSpinnerSetUp()
    {
        locationSpinner = findViewById(R.id.locationSpinner);//Finding the Spinner

        ArrayList<String> locations = new ArrayList<>();//New ArrayList called Locations

        for(earthQuake eq : earthQuakes) //for each earthQuake in the earthQuakes Array
        {
            if(locations.isEmpty())//if locations array is empty
            {
                locations.add(locationFormat(eq.getLocation()));//adds the first earthQuake location to the array
            }
            else
            {

                if(!locations.contains(locationFormat(eq.getLocation()))) //if the earthQuake location is not
                    //in the array
                {
                    locations.add(locationFormat(eq.getLocation()));//adds the new location into the array
                }

            }
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,locations);
        //sets up the array adapts needed for the spinner
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//sets the layout of the spinner

        locationSpinner.setAdapter(aa);//sets the array adapter to the spinner
        locationSpinner.setVisibility(View.VISIBLE);//makes the spinner visible

    }

    void spinnerSearch(String searchLocation)//searched loction from spinner as input string
    {

        ArrayList<earthQuake> locatedEarthQuakes = new ArrayList<>();
        //creats new arraylist called locatedEarthQuake

        for (earthQuake eq : earthQuakes) {//for each earthQuake in earthQuakes

            if (searchLocation.equals(locationFormat(eq.getLocation()))) {//if the location selected
                //and the location of the earthQuake match

                locatedEarthQuakes.add(eq);//adds the earthQuake to the array

            }
        }

            Intent i = new Intent(getApplicationContext(), searchLocationActivity.class);//sets destination
            i.putExtra("searchResults", locatedEarthQuakes);//passes the arraylist to the next activity
            i.putExtra("location", searchLocation);//passes the searched location to the next activity
            startActivity(i);//starts the new activity


    }

    //==============================================================================================
    //Adding Quakes to a table

    void tableSetup(int sort)//input of an int
    {
        dataTable.removeAllViews();//removes all previous rows

        TableRow titleRow = new TableRow(getApplicationContext());//creates new row

        TextView locTitle = new TextView(getApplicationContext());//creates new TextViews
        TextView dateTitle = new TextView(getApplicationContext());
        TextView itemTitle = new TextView(getApplicationContext());

        //formatting for the TextVeiws
        locTitle.setTextSize(18);
        locTitle.setGravity(0);
        locTitle.setPadding(0, 10, 0, 10);
        dateTitle.setTextSize(18);
        dateTitle.setGravity(0);
        dateTitle.setPadding(0, 10, 0, 10);
        itemTitle.setTextSize(18);
        itemTitle.setGravity(0);
        itemTitle.setPadding(0, 10, 0, 10);

        //Sets the text of the textVeiws
        locTitle.setText("Location:");
        dateTitle.setText("Date:");

        if(sort == 3)//if sorted by Depth
        {
            itemTitle.setText("Depth:");//sets the textVeiw to Depth
        } else {
            itemTitle.setText("Magnitude:");//else sets it to Magitude
        }


        //adds the Textviews to the row
        titleRow.addView(locTitle);
        titleRow.addView(dateTitle);
        titleRow.addView(itemTitle);

        dataTable.addView(titleRow);//adds the new row to the table


    }


    void tableMethod(int sort) {

        tableSetup(sort);//calls the tableSort method

        int pos = 1;//sets Pos to 1

        switch (sort) {//Switch based of the sort variable

            case 1: {//sort by Magnitude

                if (magSorted) {//if not previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new magCompareDES());
                    filterBy.setText("Magnitude Descending Order:");
                    filterBy.setVisibility(View.VISIBLE);

                } else {//if previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new magCompareASE());
                    filterBy.setText("Magnitude Ascending Order:");
                    filterBy.setVisibility(View.VISIBLE);
                }

                break;

            }

            case 2: {//sort by Date

                if (dateSorted) {//if not previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new dateCompareDES());
                    filterBy.setText("Date Descending Order:");
                    filterBy.setVisibility(View.VISIBLE);


                } else {//if previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new dateCompareASE());
                    filterBy.setText("Date Ascending Order:");
                    filterBy.setVisibility(View.VISIBLE);

                }
                break;
            }

            case 3:{//Sort by Depth

                if (depthSored) {//if not previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new depthCompareDES());
                    filterBy.setText("Depth Descending Order:");
                    filterBy.setVisibility(View.VISIBLE);

                } else {//if previously sorted

                    //calls the comparator Class - Sets the TextView text
                    Collections.sort(earthQuakes, new depthCompareASE());
                    filterBy.setText("Depth Ascending Order:");
                    filterBy.setVisibility(View.VISIBLE);
                }

            }

            default: {

            }

        }


        for (earthQuake eQ : earthQuakes) {

            //====================================================
            //Setting Up the table rows
            TableRow row = new TableRow(getApplicationContext());//creates new row

            row.setId(pos);//sets the row ID to the position in the array

            //creates new TextViews
            TextView locText = new TextView(getApplicationContext());
            TextView dateText = new TextView(getApplicationContext());
            TextView itemText = new TextView(getApplicationContext());


            //formating for the TextViews
            locText.setTextSize(15);
            locText.setGravity(0);
            locText.setPadding(0, 10, 0, 10);
            dateText.setTextSize(15);
            dateText.setGravity(0);
            dateText.setPadding(0, 10, 0, 10);
            itemText.setTextSize(15);
            itemText.setGravity(0);
            itemText.setPadding(0, 10, 0, 10);


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

            //Sets the text of the TextViews
            locText.setText(locationFormat(eQ.getLocation()));
            dateText.setText(removeTime(eQ.getDateTime()));
            //If Sorted by Depth, TextView = Depth  else TextView = Magnitude
            if(sort == 3){itemText.setText(eQ.getDepth() + "Km");}else{itemText.setText(eQ.getMagnitude());}


            //Adds the TextViews to the row
            row.addView(locText);
            row.addView(dateText);
            row.addView(itemText);
            //Adds the row to the Table
            dataTable.addView(row);


            int finalPos = pos;
            row.setOnClickListener(new OnClickListener() {//if the row is clicked
                @Override
                public void onClick(View view) {


                    Intent i = new Intent(MainActivity.this, detailsActivity.class);
                    //sets the destination of the intent

                    //sends necessary information
                    i.putExtra("l", locationFormat(eQ.location));
                    i.putExtra("dt", eQ.getDateTime());
                    i.putExtra("m", eQ.getMagnitude());
                    i.putExtra("d", eQ.getDepth());
                    i.putExtra("lo", eQ.getLongitude());
                    i.putExtra("la", eQ.getLatitude());

                    startActivity(i);//starts the new activity

                }
            });

            pos++;//pos +1

        }
    }

    //==============================================================================================
    //Search Method

    void searchMethod(String inString) {//inString = the date the user has searched
        Date searchDate;//creates new Date varibale
        searchError = (TextView) findViewById(R.id.searchError);//finds the error textView

        try {//try
            //parses the user String into a Date format
            searchDate = new SimpleDateFormat("dd/MM/yyyy").parse(inString);

            //if no errors the textView Disapears
            searchError.setVisibility(View.GONE);

            //creates new ArrayList called datedearthQuakes
            ArrayList<earthQuake> datedEarthQuakes = new ArrayList<>();


            for (earthQuake q : earthQuakes) {//for Each earthQuake in the Array

                if (dateFormat(q.getDateTime()).compareTo(searchDate) == 0) {
                    //if the earthQuake data is the same date as the searchdate

                    datedEarthQuakes.add(q);//adds earthQuake to the new array
                    searchError.setVisibility(View.GONE);

                }

            }

            if(datedEarthQuakes.isEmpty())//if the datedEarthQuake array is empty
            {
                searchError.setVisibility(View.VISIBLE);//sets the error textview to visieble
                searchError.setText("Error: Unable to find a Quake matching that date!");//Error Messegie
            }
            else
            {


                Intent i = new Intent(getApplicationContext(), searchDateActivity.class);//creates intent
                i.putExtra("searchResults", datedEarthQuakes);//passes array to the next activity
                i.putExtra("date", removeTime(datedEarthQuakes.get(0).getDateTime())) ;//passes the date to the activity
                startActivity(i);//starts activity

            }


        } catch (ParseException e) {//if the date is not in the correct format
            searchError.setText("Error: Please enter a valid Date Format!!");//sets text for a error the error messige
            searchError.setVisibility(View.VISIBLE);//sets the textview visable
        }

    }


    //==============================================================================================
    //Remove Time Method
    public static String removeTime(String dateTime) {//Public Method to allow its use over diffrent activitys
        String date = "";//new date string
        int pos = 0;//character posistion

        while (dateTime.charAt(pos + 2) != ':') {//goes though the string until it finds ':'
            date = date + dateTime.charAt(pos);//creates the new date by added a character to the new string

            pos++;//pos +1

        }
        date.trim();//removes and spaces at the start or end of the string

        return (date);//returns the new date
    }

    //==============================================================================================
    //Location Format Method
    public static String locationFormat(String location) {
        String newString = ""; //Creates to veribal newString
        char charCase = ' '; //Creates the veribal charCase
        int pos = 1; //creates the veribal pos equaling 1

        newString = newString + location.charAt(0);

        while (pos != location.length()) {//while the string has not ended
            if (location.charAt(pos) == ',') {//if ',' is found
                newString = newString + ", ";//adds this to newString
                pos++; //pos +1
                newString = newString + location.charAt(pos);//adds character at pos to newString

            } else if (location.charAt(pos) == ' ') {//if a ' ' is found

                newString = newString + " ";//adds ' ' to newString
                pos++;// pos + 1
                newString = newString + location.charAt(pos);//adds character at pos to newString
            } else {
                charCase = location.charAt(pos);//adds character at pos to newString
                charCase = Character.toLowerCase(charCase);//coverts charachter case to lowercase
                newString = newString + charCase;//adds that new character to newString
            }

            pos++; //pos + 1

        }

        return (newString);// return newString
    }
    //==============================================================================================

    Date dateFormat(String indate) {
        Date newDate;// newDate of type Date
        String[] split;//creates an array of strings
        String newString = "";//newString of type String
        indate = removeTime(indate);//removes time from the inDate string
        //indate: Day, dd, Month, yyyy

        split = indate.split(" ");//splits the String into segments from each ' '

        newString = newString + split[1].trim() + "/";
        newString = newString + monthFormat(split[2]) + "/";
        newString = newString + split[3];

        try {
            newDate = new SimpleDateFormat("dd/MM/yyyy").parse(newString);//trys parsing the new String into a date
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newDate;//return newDate
    }

    String monthFormat(String inString) {//formats month from letters to numbers

        inString.trim();//removes any spaces from eachend of the string

        switch (inString) {

            //if the string matches any month alases it retunes the number equivalent

            case ("Jan"): {
                return "01";
            }
            case ("Feb"): {
                return "02";
            }
            case ("Mar"): {
                return "03";
            }
            case ("Apr"): {
                return "04";
            }
            case ("May"): {
                return "05";
            }
            case ("Jun"): {
                return "06";
            }
            case ("Jul"): {
                return "07";
            }
            case ("Aug"): {
                return "08";
            }
            case ("Sep"): {
                return "09";
            }
            case ("Oct"): {
                return "10";
            }
            case ("Nov"): {
                return "11";
            }
            case ("Dec"): {
                return "12";
            }
            default:
                return "";

        }

    }


    public void startProgress()//StartProgress Method
    {
        // Run network access on a separate thread;
        Thread t = new Thread(new Task(urlSource));
        t.start();
        try {
            t.join();
        } catch (Exception e) {
            Log.e("Start Thread Try", "in try " + e);
        }

    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable {
        //======================================================================================
        //Action : Getting XML data from URL
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                result = "";
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;

                }
                in.close();
            } catch (IOException ae) {

                Log.e("MyTag", "Failed");

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("Error");
                alertDialog.setMessage("Unable To Gather Necessary Data\n Check Your Internet Connection");

                alertDialog.setButton(alertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.show();

                }


            parserMethod();

        }
    }
}
//============================================================================================
