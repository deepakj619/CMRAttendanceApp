package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAttendanceAdapater extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioGroup radioGroup1;
    private RadioButton radioButton;
    private Spinner periodpspinner;
    private ListView listView;
    private TextView textView;
    private String listtype;
    private String period;
    public HashMap<String,ArrayList<String>> presenthashMap=new HashMap<String, ArrayList<String>>();
    public HashMap<String,ArrayList<String>> absenthashMap=new HashMap<String, ArrayList<String>>();
    public static final String[] periods={"1","2","3","4","5","6"};
    private Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_adapater);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView=(ListView)findViewById(R.id.attendancelistview);
        radioGroup1 = (RadioGroup) findViewById(R.id.listradio);
        periodpspinner=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(ShowAttendanceAdapater.this,android.R.layout.simple_spinner_dropdown_item,periods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodpspinner.setAdapter(adapter);
        periodpspinner.setOnItemSelectedListener(this);
        Intent intent=getIntent();
        Bundle args=intent.getBundleExtra(  "BUNDLE");
        ArrayList<AttendanceData>attendanceDatas=(ArrayList<AttendanceData>)args.getSerializable("ARRAYLIST");
        for(int i=0;i<attendanceDatas.size();i++){

            AttendanceData data=new AttendanceData();
            data=attendanceDatas.get(i);
            for(int j=0;j<data.periodlist.size();j++){

                if(data.periodlist.get(j).equals("1")){

                    presenthashMap.put("1",data.presentlist);
                    absenthashMap.put("1",data.absentlist);

                }
                if(data.periodlist.get(j).equals("2")){

                    presenthashMap.put("2",data.presentlist);
                    absenthashMap.put("2",data.absentlist);

                }
                if(data.periodlist.get(j).equals("3")){

                    presenthashMap.put("3",data.presentlist);
                    absenthashMap.put("3",data.absentlist);

                }
                if(data.periodlist.get(j).equals("4")){

                    presenthashMap.put("4",data.presentlist);
                    absenthashMap.put("4",data.absentlist);

                }
                if(data.periodlist.get(j).equals("5")){

                    presenthashMap.put("5",data.presentlist);
                    absenthashMap.put("5",data.absentlist);

                }
                if(data.periodlist.get(j).equals("6")){

                    presenthashMap.put("6",data.presentlist);
                    absenthashMap.put("6",data.absentlist);

                }
            }

        }
        String branch=MainActivity.getBranch();
        String year= String.valueOf(Year.getYear());
        String date=getIntent().getStringExtra("date");
        String section= String.valueOf(Section.getSecLetter());
        String semester= String.valueOf(Semester.getSem());
        textView=(TextView)(findViewById(R.id.attendancetv));
        show=(Button)findViewById(R.id.showAttendance);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                listtype = ((RadioButton)findViewById(radioGroup1.getCheckedRadioButtonId())).getText().toString();
                try {
                    setAttendance();
                }catch (NullPointerException e){

                    Toast.makeText(getApplicationContext(), "Attendance not given for "+period+" period", Toast.LENGTH_SHORT).show();

                }catch (IllegalStateException e){

                    Toast.makeText(getApplicationContext(), "Attendance not given for "+period+" period", Toast.LENGTH_SHORT).show();

                }

            }
        });

        textView.setText("Attendance for "+branch+"-"+year+"-"+semester+"-"+section+" for Date:"+date+"  ");


    }

    public void setAttendance(){


        if(period.equals("1")){
        if(listtype.equals("Present List")) {



                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("1"));
                listView.setAdapter(adapter);


        }
        else if(listtype.equals("Absent List")){

                  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("1"));
                listView.setAdapter(adapter);

        }
        }
        else if(period.equals("2")){
            if(listtype.equals("Present List")) {

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("2"));
                listView.setAdapter(adapter);

            }
            else if(listtype.equals("Absent List")){

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("2"));
                    listView.setAdapter(adapter);

            }
        }
        else if(period.equals("3")){
            if(listtype.equals("Present List")) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("3"));
                    listView.setAdapter(adapter);
            }
            else if(listtype.equals("Absent List")){

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("3"));
                    listView.setAdapter(adapter);
            }
        }
        else if(period.equals("4")){
            if(listtype.equals("Present List")) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("4"));
                    listView.setAdapter(adapter);

            }
            else if(listtype.equals("Absent List")){

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("4"));
                    listView.setAdapter(adapter);

            }
        }
        if(period.equals("5")){
            if(listtype.equals("Present List")) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("5"));
                    listView.setAdapter(adapter);

            }
            else if(listtype.equals("Absent List")){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("5"));
                    listView.setAdapter(adapter);                }
        }
        if(period.equals("6")){
            if(listtype.equals("Present List")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, presenthashMap.get("6"));
                    listView.setAdapter(adapter);

            }
            else if(listtype.equals("Absent List")){

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, absenthashMap.get("6"));
                    listView.setAdapter(adapter);

            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                Toast.makeText(this, "You Selected " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                period = parent.getItemAtPosition(position).toString();
                break;
            case 1:
                Toast.makeText(this,"You Selected "+parent.getItemAtPosition(position).toString()+" Period",Toast.LENGTH_LONG).show();
                period=parent.getItemAtPosition(position).toString();
                break;
            case 2:
                Toast.makeText(this,"You Selected "+parent.getItemAtPosition(position).toString()+" Period",Toast.LENGTH_LONG).show();
                period=parent.getItemAtPosition(position).toString();
                break;
            case 3:
                Toast.makeText(this,"You Selected "+parent.getItemAtPosition(position).toString()+" Period",Toast.LENGTH_LONG).show();
                period=parent.getItemAtPosition(position).toString();
                break;
            case 4:
                Toast.makeText(this,"You Selected "+parent.getItemAtPosition(position).toString()+" Period",Toast.LENGTH_LONG).show();
                period=parent.getItemAtPosition(position).toString();
                break;
            case 5:
                Toast.makeText(this,"You Selected "+parent.getItemAtPosition(position).toString()+" Period",Toast.LENGTH_LONG).show();
                period=parent.getItemAtPosition(position).toString();
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
