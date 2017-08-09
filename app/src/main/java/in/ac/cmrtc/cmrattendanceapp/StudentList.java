package in.ac.cmrtc.cmrattendanceapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class StudentList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    static ArrayList<String> rollNo = new ArrayList<>();
    private static String list;
    private ArrayList<String> checkBox = new ArrayList<>();
    private ArrayList<String> Pcheckbox = new ArrayList<>();
    private ArrayList<String> Acheckbox = new ArrayList<>();
    private Spinner subjectspinner;
    private ArrayAdapter<CharSequence> subjectadapter;
    private int noOfColumns;
    private int noofRows;
    private int noOfElements;
    private Firebase firebase;
    private static String info;
    private RadioButton presentbutton,absentbutton;
    private int checkBoxNo;
    private Button database;
    private  Boolean check=true;
    public String dbdate = "";
    private String subject;
    public ArrayList<String> periods = new ArrayList<String>();
    public  String tempdate = "";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Attendance List - " + MainActivity.getBranch() + " - " + Year.getYear()
                + " - " + Section.getSecLetter());

        setContentView(R.layout.activity_student_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.getBranch());
        rollNo.clear();
        ListGenerator generate = new ListGenerator(MainActivity.getId(),
                Year.getYear(), Section.getSec());
        rollNo = generate.getList();
        noOfElements = rollNo.size();
        createTable();

        subjectspinner=(Spinner)findViewById(R.id.subjectSpinner);
        if(Semester.getSem()==1 && Year.getYear()==2) {
            subjectadapter = ArrayAdapter.createFromResource(this, R.array.Second_cse_1_subjects,android.R.layout.simple_spinner_item);
            subjectadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectspinner.setAdapter(subjectadapter);
            subjectspinner.setOnItemSelectedListener(this);
        }
        else if(Semester.getSem()==2 && Year.getYear()==2){

            subjectadapter = ArrayAdapter.createFromResource(this, R.array.Second_cse_2_subjects, android.R.layout.simple_spinner_item);
            subjectadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subjectspinner.setAdapter(subjectadapter);
            subjectspinner.setOnItemSelectedListener(this);

        }

        Button button = (Button) findViewById(R.id.email);
        button.setText("E-mail");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting absent's list from a function call
                list = checkBoxStatus();
                //If list is not null then the E-mail action will be performed
                if (list != null) {
                    //This Intent is used to call an E-Mail app to perform the action
                    Intent displayList = new Intent(Intent.ACTION_SENDTO);
                    displayList.setData(Uri.parse("mailto:"));
                    //Setting subject for the email
                    displayList.putExtra(Intent.EXTRA_SUBJECT, "Absent List");
                    //Setting the absent's list as body of E-Mail
                    displayList.putExtra(Intent.EXTRA_TEXT, list);
                    //Starting the E-mail activity
                    //the if blocks handles any errors generated while passing intent and
                    //prevents the app from crashing
                    if (displayList.resolveActivity(getPackageManager()) != null) {
                        startActivity(displayList);
                    }
                }
            }

        });
        Button text = (Button) findViewById(R.id.sms);
        //setting text for button
        text.setText("SMS");
        //adding a listener to the button to set it's action when it is clicked
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting absent's list from a function call
                list = checkBoxStatus();
                //If list is not null then the SMS action will be performed
                if (list != null) {
                    //This Intent is used to call an SMS app to perform the action
                    Intent text = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
                    //Adding body of SMS
                    text.putExtra("sms_body", list);
                    //Starting the SMS activity
                    //the if blocks handles any errors generated while passing intent and
                    //prevents the app from crashing
                    if (text.resolveActivity(getPackageManager()) != null) {
                        startActivity(text);
                    }
                }
            }
        });
        Button button1 = (Button) findViewById(R.id.show);
        //setting text for button
        button1.setText("Show");
        //adding a listener to the button to set it's action when it is clicked
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting absent's list from a function call
                list = checkBoxStatus();
                //If list is not null then the list is displayed
                if (list != null) {
                    //This Intent is used to display list in same app by calling display activity
                    Intent open = new Intent(StudentList.this, in.ac.cmrtc.cmrattendanceapp.Display.class);
                    //Starting the activity
                    startActivity(open);
                }
            }
        });
        Button copy = (Button) findViewById(R.id.copy);
        copy.setText("copy");
        copy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                list = checkBoxStatus();
                //If list is not null then the list is copied to clip board
                if (list != null) {
                    //getting clipboard manager of the device
                    ClipboardManager clipboard = (ClipboardManager)
                            getSystemService(Context.CLIPBOARD_SERVICE);
                    //Setting data to be copied to clipboard
                    ClipData clip = ClipData.newPlainText("simple text", list);

                    //Setting the list as primary item(first) in clipboard
                    clipboard.setPrimaryClip(clip);

                    //Displaying a message to let the user know the list is
                    //copied to the clip board
                    Toast.makeText(StudentList.this, "Copied to clipboard",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        String url="https://cmr-attendance.firebaseio.com/"+MainActivity.getBranch()+"/"+Year.getYear()+"/"+Section.getSecLetter()+"/"+String.valueOf(Semester.getSem());
        Firebase.setAndroidContext(getApplicationContext());
        final Firebase ref=new Firebase(url);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    tempdate = snapshot.getKey();
                    try {
                        Date date;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
                        date = simpleDateFormat.parse(tempdate);
                        dbdate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                        String todaysdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                        if(todaysdate.equals(dbdate)){

                            periods = (ArrayList<String>) snapshot.child("validperiods").getValue();
                            check=true;
                            break;
                        }
                        else{

                            continue;
                        }


                    } catch (ParseException e) {

                        e.printStackTrace();
                    }

                }
                ref.removeEventListener(this);


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });
        database = (Button) findViewById(R.id.db);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase.setAndroidContext(getApplicationContext());
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                @SuppressWarnings("ConstantConditions") DatabaseReference reference = db.getReference()
                        .child(MainActivity.getBranch())
                        .child(String.valueOf(Year.getYear()))
                        .child(String.valueOf(Section.getSecLetter()))
                        .child(String.valueOf(Semester.getSem()));
                String todaysdate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                AttendanceData attendanceData=new AttendanceData();

                try {
                    attendanceData = checkBoxStatus(true);
                    attendanceData.subject=subject;
                    if(attendanceData==null || attendanceData.absentlist.size()==0 || attendanceData.presentlist.size()==0){

                        Toast.makeText(StudentList.this, "Invalid Attendance", Toast.LENGTH_SHORT).show();
                        return;

                    }

                }catch (NullPointerException e){


                        Toast.makeText(StudentList.this, "Please Select Atleast one period.", Toast.LENGTH_SHORT).show();
                        return;

                }
                if(todaysdate.equals(dbdate) && check==true && attendanceData!=null){

                    int count=0;
                    for(int i=0;i<periods.size();i++){

                        if(periods.get(i).equals("DONE")){


                            count++;
                        }

                    }
                    if(count==6){

                        Toast.makeText(StudentList.this, "Today's Attendance has been done.", Toast.LENGTH_SHORT).show();

                    }
                    attendanceData=checkdb(attendanceData);
                    try {
                        if (attendanceData == null) {

                            Toast.makeText(StudentList.this, "Attendance is not valid", Toast.LENGTH_SHORT).show();
                            destroyTable();
                            createTable();
                        }
                        else {
                            String id = reference.push().getKey();
                            DatabaseReference d = reference.child(tempdate);
                            d.child("validperiods").setValue(periods);
                            d.child(id).setValue(attendanceData);
                            Toast.makeText(StudentList.this, "Attendance Uploaded to server.", Toast.LENGTH_SHORT).show();
                            destroyTable();
                            createTable();
                        }

                    }catch (NullPointerException e){

                        Toast.makeText(StudentList.this, "Attendance is not valid", Toast.LENGTH_SHORT).show();
                        destroyTable();
                        createTable();
                    }

                    }

                else {
                    String id = reference.push().getKey();
                    ArrayList<String> allowedperiods = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6"));
                    try {
                        for (int i = 0; i < allowedperiods.size(); i++) {

                            for (int j = 0; i < attendanceData.periodlist.size(); j++) {

                                if (attendanceData.periodlist.get(j).equals(allowedperiods.get(i))) {

                                    allowedperiods.set(i, "DONE");
                                    if(j==0){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_1);
                                        checkBox.setEnabled(false);
                                    }
                                    if(j==1){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_2);
                                        checkBox.setEnabled(false);
                                    }
                                    if(j==2){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_3);
                                        checkBox.setEnabled(false);
                                    }
                                    if(j==3){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_4);
                                        checkBox.setEnabled(false);
                                    }
                                    if(j==4){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_5);
                                        checkBox.setEnabled(false);
                                    }
                                    if(j==5){
                                        CheckBox checkBox=(CheckBox)findViewById(R.id.period_6);
                                        checkBox.setEnabled(false);
                                    }
                                    break;
                                }
                            }

                        }
                    }catch (NullPointerException e){



                            Toast.makeText(StudentList.this, "Please Select Atleast one period.", Toast.LENGTH_SHORT).show();


                    }
                    periods=allowedperiods;
                    tempdate=new Date().toString();
                    DatabaseReference d = reference.child(tempdate);
                    d.child("validperiods").setValue(allowedperiods);
                    d.child(id).setValue(attendanceData);
                    Toast.makeText(StudentList.this, "Attendance Uploaded to server.", Toast.LENGTH_SHORT).show();
                    dbdate=todaysdate;
                    destroyTable();
                    createTable();
                }
        }
        });

    }

    private void createTable() {

        final float scale = getResources().getDisplayMetrics().scaledDensity;
        float mTextSizeP = getResources().getDimensionPixelSize(R.dimen.List_text_size) / scale;

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        //Getting the size of display to calculate no. of Columns in each row
        final int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        float xDpi = dm.xdpi;
        float actwidth = width / xDpi;
        // setting no of rows and columns in table
        if (actwidth < 3) {
            noOfColumns = 3;
        } else {
            noOfColumns = (int) ((3 * actwidth) / 2.677167f);
        }
        noofRows = rollNo.size() / noOfColumns;

        //adding the extra row which may be missed in above step
        if (((rollNo.size() / (noOfColumns * 1.0)) % 1) != 0) {
            noofRows++;
        }
        //Calculating total blocks that are obtained from row and column count
        int totalBlocks = noOfColumns * noofRows;
        //Adding a blank entry's to make the list generated to look even
        while (rollNo.size() < totalBlocks) {
            rollNo.add("");
        }
        //Used to count no of roll no.'s remaining to generate view in layout
        int totalCount = 0;
        //initial count of checkbox is zero
        checkBoxNo = 0;
        for (int row = 0; row < noofRows; row++) {//used to generate Rows in table
            //create's new row in table
            TableRow rowView = new TableRow(this);
            //setting the row width and height to match_parent and wrap_content
            TableRow.LayoutParams lp = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            rowView.setLayoutParams(lp);

            //Adding weight to params to use on it's children views
            lp.weight = 1;

            // setting column count to '0'
            int column = 0;
            while ((column < noOfColumns) && (totalCount < rollNo.size())) {
                //check box is added only for valid data
                if (!rollNo.get(totalCount).equals("")) {
                    //For each row three columns are generated
                    //Check box view is created dynamically
                    CheckBox check = new CheckBox(this);
                    //setting the text to checkbox
                    check.setText(rollNo.get(totalCount));
                    //setting text size of text
                    check.setTextSize(mTextSizeP);
                    //Giving an is to the newly generated checkbox
                    check.setId(totalCount);
                    //Adding a rectangle border to checkbox
                    check.setBackgroundResource(R.drawable.cell_shape);
                    //setting layout parameters to the checkbox
                    check.setLayoutParams(lp);
                    //Adding view to row
                    rowView.addView(check);
                    checkBoxNo++;
                }
                //if the data is not valid a blank text view is added in place of checkbox
                else {
                    //Creating new Text variable
                    TextView bl = new TextView(this);
                    //setting the size of text to match the size of checkbox
                    bl.setTextSize(mTextSizeP + 1.15f);
                    //Adding a rectangle border to text view to maintain look of table
                    bl.setBackgroundResource(R.drawable.cell_shape);
                    //setting layout parameters that are already declared
                    bl.setLayoutParams(lp);
                    //adding the blank text view to row
                    rowView.addView(bl);
                }
                //incrementing the total count by 1
                totalCount++;
                //incrementing the column by 1
                column++;
            }
            //adding row to table
            tableLayout.addView(rowView, row);
        }
    }

    private void destroyTable() {
        //getting the id of table
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        //Deleting all the sub views added to the table in create() method
        tableLayout.removeAllViews();
    }

    private void saveCheckBox() {
        //clearing the previous memory checkbox states (if there is any)
        if (checkBox.size() > 0) {
            checkBox.clear();
        }
        //Scanning all the list(check boxes) to save their state and retrieve for later
        for (int i = 0; i < checkBoxNo; i++) {
            //Getting the id of the check box and storing it
            CheckBox check = (CheckBox) findViewById(i);
            //if the box is checked then it is stored in an array list
            if (check.isChecked()) {
                //adding the element to checkbox array List
                checkBox.add(rollNo.get(i));
            }
        }
    }

    private String checkBoxStatus() {

        //Getting date in DD/MM/YYYY format
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        //Variable list is used to sore the generated list
        //Getting branch - year - section and date and storing it in list
        String list = MainActivity.getBranch() + " - " + Year.getYear()
                + " - " + Section.getSecLetter() + "         " + date + "        ";

        //Getting period input, period variable is used to store period
        String period = null;
        //Getting the id's of periods from xml file
        CheckBox period1 = (CheckBox) findViewById(R.id.period_1);
        CheckBox period2 = (CheckBox) findViewById(R.id.period_2);
        CheckBox period3 = (CheckBox) findViewById(R.id.period_3);
        CheckBox period4 = (CheckBox) findViewById(R.id.period_4);
        CheckBox period5 = (CheckBox) findViewById(R.id.period_5);
        CheckBox period6 = (CheckBox) findViewById(R.id.period_6);

        boolean present=false,absent=false;
        presentbutton=(RadioButton)findViewById(R.id.present);
        absentbutton=(RadioButton)findViewById(R.id.absent);

        if(presentbutton.isChecked()){

            present=true;
        }
        else if(absentbutton.isChecked()){

            absent=true;
        }
        if ((!period1.isChecked() && !period2.isChecked() && !period3.isChecked() &&
                !period4.isChecked() && !period5.isChecked() && !period6.isChecked()) && (!present || !absent)) {
            Toast.makeText(this, "Select any one period and Present or Absent button", Toast.LENGTH_SHORT).show();
            return null;
        }

        //If no period is selected the system toasts a message to select any one period
        else if (!period1.isChecked() && !period2.isChecked() && !period3.isChecked() &&
                !period4.isChecked() && !period5.isChecked() && !period6.isChecked()) {
            Toast.makeText(this, "Select any one period", Toast.LENGTH_SHORT).show();
            //null is returned so user doesn't go into next page without selecting period
            return null;
        } else { //if the user has selected a period
            if (period1.isChecked()) //if 1st period is checked
                period = "1";
            if (period2.isChecked()) { // if 2nd period is checked
                if (period == null) // if no previous periods are selected
                    period = "2";
                else               //if previous period is already selected
                    period = period + ", 2";
            }
            if (period3.isChecked()) { // if 3rd period is checked
                if (period == null)// if no previous periods are selected
                    period = "3";
                else               //if previous period is already selected
                    period = period + ", 3";
            }
            if (period4.isChecked()) { // if 4th period is checked
                if (period == null)// if no previous periods are selected
                    period = "4";
                else               //if previous period is already selected
                    period = period + ", 4";
            }
            if (period5.isChecked()) { // if 5th period is checked
                if (period == null)// if no previous periods are selected
                    period = "5";
                else               //if previous period is already selected
                    period = period + ", 5";
            }
            if (period6.isChecked()) {// if 6th period is checked
                if (period == null)// if no previous periods are selected
                    period = "6";
                else               //if previous period is already selected
                    period = period + ", 6";
            }
        }
        //Adding the period to the list
        list = list + "Period : " + period;

        //getting date and time when the list is generated
        String infdate = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)) +
                Integer.toString(Calendar.getInstance().get(Calendar.MONTH)) +
                Integer.toString(Calendar.getInstance().get(Calendar.DATE)) + "_" +
                Integer.toString(Calendar.getInstance().get(Calendar.HOUR)) +
                Integer.toString(Calendar.getInstance().get(Calendar.MINUTE)) +
                Integer.toString(Calendar.getInstance().get(Calendar.SECOND));
        //storing into variable to use it in naming the file while saving
        info = MainActivity.getBranch() + "_" + Year.getYear()
                + "_" + Section.getSecLetter() + "_" + infdate;



        //Getting id's of absent and present List Generation check boxes
        CheckBox presentList = (CheckBox) findViewById(R.id.present_list);
        CheckBox absentList = (CheckBox) findViewById(R.id.absent_list);

        //Conditions for various combinations of present and absent List generation check box's
        //If both the List Generation check boxes are checked
        if (presentList.isChecked() && absentList.isChecked()) {
            //Warns the user to select any one box only
            Toast.makeText(this, "Select any one not both(Absent List or Present List) to generate",
                    Toast.LENGTH_SHORT).show();
        } else if (presentList.isChecked()) { //If present List Generation is checked
            //Conditions for various combinations of present and absent check box's
            //If both the check boxes are checked
            if (present && absent) {
                //Warns the user to select any one box only
                Toast.makeText(this, "Select any one not both(absent or present)",
                        Toast.LENGTH_SHORT).show();
                //The function will return null and the task is not be performed
                return null;
            } else if (present) {//If present is checked
                list = list + "\nPresent : \n";
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box is checked the list no is added to the list
                    if (check.isChecked()) {
                        list = list + rollNo.get(i) + ", ";
                    }
                }
                //this function will return the list of present students
                return list;
            } else if (absent) { //If absent is checked
                list = list + "\nPresent : \n";
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box not checked then it is added to the list
                    if (!check.isChecked()) {
                        list = list + rollNo.get(i) + ", ";
                    }
                }
                //this function will return the list of present students
                return list;
            } else {
                //If none of the box(absent or present) is checked
                //The user is warned
                Toast.makeText(this, "Select absent or present", Toast.LENGTH_SHORT).show();
            }
        } else if (absentList.isChecked()) { //If absent List Generation is checked
            //Conditions for various combinations of present and absent check box's
            //If both the check boxes are checked
            if (present && absent) {
                //Warns the user to select any one box only
                Toast.makeText(this, "Select any one not both(absent or present)",
                        Toast.LENGTH_SHORT).show();
                //The function will return null and the task is not be performed
                return null;
            } else if (present) {//If present is checked
                list = list + "\nAbsent : \n";
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box not checked the list no is added to the list
                    if (!check.isChecked()) {
                        list = list + rollNo.get(i) + ", ";
                    }
                }
                //this function will return the list of absent students
                return list;
            } else if (absent) { //If absent is checked
                list = list + "\nAbsent : \n";
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box is checked then it is added to the list
                    if (check.isChecked()) {
                        list = list + rollNo.get(i) + ", ";
                    }
                }
                //this function will return the list of absent students
                System.out.println(list);
                return list;
            } else {
                //If none of the box(absent or present) is checked
                //The user is warned
                Toast.makeText(this, "Select absent or present", Toast.LENGTH_SHORT).show();
            }
        } else {
            //If none of the box(absent or present List generation) is checked
            //The user is warned
            Toast.makeText(this, "Select to generate a list", Toast.LENGTH_SHORT).show();

        }
        //The function will return null and the task is not be performed as the user
        // as not satisfied the conditions to generate thr List
        return null;
    }

    public AttendanceData checkBoxStatus(Boolean db) {



        AttendanceData attendanceData = new AttendanceData();
        CheckBox period1 = (CheckBox) findViewById(R.id.period_1);
        CheckBox period2 = (CheckBox) findViewById(R.id.period_2);
        CheckBox period3 = (CheckBox) findViewById(R.id.period_3);
        CheckBox period4 = (CheckBox) findViewById(R.id.period_4);
        CheckBox period5 = (CheckBox) findViewById(R.id.period_5);
        CheckBox period6 = (CheckBox) findViewById(R.id.period_6);

        boolean present=false,absent=false;
        presentbutton=(RadioButton)findViewById(R.id.present);
        absentbutton=(RadioButton)findViewById(R.id.absent);

        if(presentbutton.isChecked()){

            present=true;
        }
        else if(absentbutton.isChecked()){

            absent=true;
        }

        if ((!period1.isChecked() && !period2.isChecked() && !period3.isChecked() &&
                !period4.isChecked() && !period5.isChecked() && !period6.isChecked()) && (!present || !absent)) {
            Toast.makeText(this, "Select any one period and Present or Absent button", Toast.LENGTH_SHORT).show();
            return null;
        }
        //If no period is selected the system toasts a message to select any one period
        else if (!period1.isChecked() && !period2.isChecked() && !period3.isChecked() &&
                !period4.isChecked() && !period5.isChecked() && !period6.isChecked()) {
            Toast.makeText(this, "Select any one period", Toast.LENGTH_SHORT).show();
            return null;
        } else { //if the user has selected a period
            if (period1.isChecked()) //if 1st period is checked
                attendanceData.periodlist.add("1");
            if (period2.isChecked()) { // if 2nd period is checked
                if (attendanceData.periodlist.size() == 0) // if no previous periods are selected
                    attendanceData.periodlist.add("2");
                else               //if previous period is already selected
                    attendanceData.periodlist.add("2");
            }
            if (period3.isChecked()) { // if 3rd period is checked
                if (attendanceData.periodlist.size() == 0)// if no previous periods are selected
                    attendanceData.periodlist.add("3");
                else               //if previous period is already selected
                    attendanceData.periodlist.add("3");
            }
            if (period4.isChecked()) { // if 4th period is checked
                if (attendanceData.periodlist.size() == 0)// if no previous periods are selected
                    attendanceData.periodlist.add("4");
                else               //if previous period is already selected
                    attendanceData.periodlist.add("4");
            }
            if (period5.isChecked()) { // if 5th period is checked
                if (attendanceData.periodlist.size() == 0)// if no previous periods are selected
                    attendanceData.periodlist.add("5");
                else               //if previous period is already selected
                    attendanceData.periodlist.add("5");
            }
            if (period6.isChecked()) {// if 6th period is checked
                if (attendanceData.periodlist.size() == 0)// if no previous periods are selected
                    attendanceData.periodlist.add("6");
                else               //if previous period is already selected
                    attendanceData.periodlist.add("6");
            }
        }


        //Getting id's of absent and present List Generation check boxes
        CheckBox presentList = (CheckBox) findViewById(R.id.present_list);
        CheckBox absentList = (CheckBox) findViewById(R.id.absent_list);

        //Conditions for various combinations of present and absent List generation check box's
        //If both the List Generation check boxes are checked
        if (presentList.isChecked() && absentList.isChecked()) {
            //Warns the user to select any one box only
            Toast.makeText(this, "Select any one not both(Absent List or Present List) to generate",
                    Toast.LENGTH_SHORT).show();
        } else if (presentList.isChecked()) { //If present List Generation is checked
            //Conditions for various combinations of present and absent check box's
            //If both the check boxes are checked
            if (present && absent) {
                //Warns the user to select any one box only
                Toast.makeText(this, "Select any one not both(absent or present)",
                        Toast.LENGTH_SHORT).show();
                //The function will return null and the task is not be performed
                return null;
            } else if (present) {//If present is checked
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box is checked the list no is added to the list
                    if (check.isChecked()) {
                        attendanceData.presentlist.add(rollNo.get(i));
                    } else {

                        attendanceData.absentlist.add(rollNo.get(i));

                    }
                }
                    /*for(int i=0;i<attendanceData.periodlist.size();i++) {

                        presentmap.put(attendanceData.periodlist.get(i),attendanceData.presentlist);
                    }
                for(int i=0;i<attendanceData.periodlist.size();i++) {

                    absentmap.put(attendanceData.periodlist.get(i),attendanceData.absentlist);
                }
                list.add(presentmap);
                list.add(absentmap);*/
                return attendanceData;

                //this function will return the list of present students

            } else if (absent) { //If absent is checked
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box not checked then it is added to the list
                    if (!check.isChecked()) {
                        attendanceData.presentlist.add(rollNo.get(i));
                    } else {
                        attendanceData.absentlist.add(rollNo.get(i));

                    }
                }
                //this function will return the list of present students
                /*for(int i=0;i<attendanceData.periodlist.size();i++) {

                    presentmap.put(attendanceData.periodlist.get(i),attendanceData.presentlist);
                }
                for(int i=0;i<attendanceData.periodlist.size();i++) {

                    absentmap.put(attendanceData.periodlist.get(i),attendanceData.absentlist);
                }
                list.add(presentmap);
                list.add(absentmap);*/
                return attendanceData;
                //this function will return the list of present students
            } else {
                //If none of the box(absent or present) is checked
                //The user is warned
                Toast.makeText(this, "Select absent or present", Toast.LENGTH_SHORT).show();
            }
        } else if (absentList.isChecked()) { //If absent List Generation is checked
            //Conditions for various combinations of present and absent check box's
            //If both the check boxes are checked
            if (present && absent) {
                //Warns the user to select any one box only
                Toast.makeText(this, "Select any one not both(absent or present)",
                        Toast.LENGTH_SHORT).show();
                //The function will return null and the task is not be performed
                return null;
            } else if (present) {//If present is checked
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box not checked the list no is added to the list
                    if (!check.isChecked()) {
                        attendanceData.absentlist.add(rollNo.get(i));
                    } else {

                        attendanceData.presentlist.add(rollNo.get(i));
                    }
                }
                //this function will return the list of absent students
                /*for(int i=0;i<attendanceData.periodlist.size();i++) {

                    presentmap.put(attendanceData.periodlist.get(i),attendanceData.presentlist);
                }
                for(int i=0;i<attendanceData.periodlist.size();i++) {

                    absentmap.put(attendanceData.periodlist.get(i),attendanceData.absentlist);
                }
                list.add(presentmap);
                list.add(absentmap);*/
                return attendanceData;
                //this function will return the list of present students

            } else if (absent) { //If absent is checked
                for (int i = 0; i < checkBoxNo; i++) {
                    //Now Checking all the checkboxes of roll no's
                    //Getting id's of check box's of students
                    CheckBox check = (CheckBox) findViewById(i);
                    //If check box is checked then it is added to the list
                    if (check.isChecked()) {
                        attendanceData.absentlist.add(rollNo.get(i));
                        //System.out.print("Absent: "+Acheckbox);
                    } else {

                        attendanceData.presentlist.add(rollNo.get(i));
                    }
                }
                //this function will return the list of absent students
                /*for(int i=0;i<attendanceData.periodlist.size();i++) {

                    presentmap.put(attendanceData.periodlist.get(i),attendanceData.presentlist);
                }
                for(int i=0;i<attendanceData.periodlist.size();i++) {

                    absentmap.put(attendanceData.periodlist.get(i),attendanceData.absentlist);
                }
                list.add(presentmap);
                list.add(absentmap);*/
                return attendanceData;
                //this function will return the list of present students
            } else {
                //If none of the box(absent or present) is checked
                //The user is warned
                Toast.makeText(this, "Select absent or present", Toast.LENGTH_SHORT).show();
            }
        } else {
            //If none of the box(absent or present List generation) is checked
            //The user is warned
            Toast.makeText(this, "Select to generate a list", Toast.LENGTH_SHORT).show();

        }
        //The function will return null and the task is not be performed as the user
        // as not satisfied the conditions to generate thr List
        return null;
    }

    public static String getText() {
        return list;
    }

    //This function  is used generate file name to save screen short
    public static String getInfo() {
        return info;
    }


    private AttendanceData checkdb(AttendanceData attendanceData){

        Boolean check=false;
        int size = attendanceData.periodlist.size();
        String givenperiods = "";
        for (int i = 0; i < periods.size(); i++) {

                if(size>0) {
                    if (periods.get(i).equals("DONE") && i == 0) {

                        givenperiods = givenperiods + "1,";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_1);
                        period.setEnabled(false);
                        if(attendanceData.periodlist.get(i).equals("1")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        size--;

                    } else if (attendanceData.periodlist.get(i).equals("1")) {

                        periods.set(0, "DONE");
                        check=false;
                        size--;

                    }
                    if (periods.get(i).equals("DONE") && i == 1) {

                        givenperiods = givenperiods + "2,";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_2);
                        period.setEnabled(false);
                        if(attendanceData.periodlist.get(i).equals("2")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        size--;


                    } else if (attendanceData.periodlist.get(i).equals("2")) {

                        periods.set(1,"DONE");
                        check=false;
                        size--;

                    }
                    if (periods.get(i).equals("DONE") && i == 2) {

                        givenperiods = givenperiods + "3,";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_3);
                        period.setEnabled(false);
                        if(attendanceData.periodlist.get(i).equals("3")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        size--;

                    } else if (attendanceData.periodlist.get(i).equals("3")) {

                        periods.set(2,"DONE");
                        check=false;
                        size--;

                    }
                    if (periods.get(i).equals("DONE") && i == 3) {

                        givenperiods = givenperiods + "4,";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_4);
                        if(attendanceData.periodlist.get(i).equals("4")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        period.setEnabled(false);
                        size--;

                    } else if (attendanceData.periodlist.get(i).equals("4")) {

                        periods.set(3,"DONE");
                        check=false;
                        size--;

                    }

                    if (periods.get(i).equals("DONE") && i == 4) {

                        givenperiods = givenperiods + "5,";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_5);
                        period.setEnabled(false);
                        if(attendanceData.periodlist.get(i).equals("5")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        size--;

                    } else if (attendanceData.periodlist.get(i).equals("5")) {

                        periods.set(4, "DONE");
                        check=false;
                        size--;

                    }

                    if (periods.get(i).equals("DONE") && i == 5) {

                        givenperiods = givenperiods + "6";
                        check=true;
                        CheckBox period = (CheckBox) findViewById(R.id.period_6);
                        period.setEnabled(false);
                        if(attendanceData.periodlist.get(i).equals("6")){

                            attendanceData.periodlist.set(i, "NULL");

                        }
                        size--;

                    } else if (attendanceData.periodlist.get(i).equals("6")) {

                        periods.set(5, "DONE");
                        check=false;
                        size--;

                    }
                }else{
                    break;
                }
        }
        if(check) {
            Toast.makeText(StudentList.this, "Attendance has been already given for Period: " + givenperiods, Toast.LENGTH_SHORT).show();
            return null;
        }
        else{
            return attendanceData;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        subject=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "You Selected "+subject, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
