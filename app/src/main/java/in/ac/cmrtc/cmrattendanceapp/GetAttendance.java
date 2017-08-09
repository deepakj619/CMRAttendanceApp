package in.ac.cmrtc.cmrattendanceapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetAttendance extends AppCompatActivity {

    private String branch;
    private String year;
    private String section;
    private String semester;
    private Button get;
    private Button bdate;
    private Firebase firebase;
    public String currentdate="";
    public String dbdate;
    public ArrayList<AttendanceData> databaseData = new ArrayList<AttendanceData>();
    private AttendanceData attendanceData;
    public String tempdate = "";
    private int year_x, month_x, day_x;
    private ProgressDialog progressDialog;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_attendance);
        branch=MainActivity.getBranch();
        section= String.valueOf(Section.getSecLetter());
        year=String.valueOf(Year.getYear());
        semester=String.valueOf(Semester.getSem());
        setTitle("Attendance for "+branch+" : "+year+" - "+semester+"-"+section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialiseViews();
        final Calendar calendar = Calendar.getInstance();
        year_x = calendar.get(Calendar.YEAR);
        month_x = calendar.get(Calendar.MONTH);
        day_x = calendar.get(Calendar.DAY_OF_MONTH);
        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DIALOG_ID);
            }
        });
        Firebase.setAndroidContext(this);
        get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please Wait.....");
                progressDialog.show();
                String url = "https://cmr-attendance.firebaseio.com/" + branch + "/" + year + "/" + section +"/"+semester;
                final Firebase ref = new Firebase(url);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if (databaseData.size() > 0) {

                                break;
                            }
                            tempdate = snapshot.getKey();
                            try {
                                Date date;
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");
                                date = simpleDateFormat.parse(tempdate);
                                dbdate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                                if (currentdate.equals(dbdate)) {

                                    for (DataSnapshot msg : snapshot.getChildren()) {

                                        if (msg.getKey() == "validperiods") {

                                            break;
                                        }
                                        attendanceData = msg.getValue(AttendanceData.class);
                                        databaseData.add(attendanceData);
                                        System.out.println("fhs");
                                    }

                                } else {

                                    continue;
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        if (databaseData.size() == 0) {

                            progressDialog.dismiss();
                            Toast.makeText(GetAttendance.this, "Attendance for this date not provided or invalid query", Toast.LENGTH_SHORT).show();
                        } else {

                            Intent intent = new Intent(GetAttendance.this, ShowAttendanceAdapater.class);
                            Bundle args = new Bundle();
                            args.putSerializable("ARRAYLIST", (Serializable) databaseData);
                            intent.putExtra("BUNDLE", args);
                            intent.putExtra("date", currentdate);
                            ref.removeEventListener(this);
                            progressDialog.dismiss();
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {


        if (id == DIALOG_ID) {

            return new DatePickerDialog(this, onDateSetListener, year_x, month_x, day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            if (month_x / 10 == 0) {

                currentdate = day_x + "/0" + month_x + "/" + year_x;
                if (day_x / 10 == 0 && month_x / 10 == 0) {

                    currentdate = "0" + day_x + "/0" + month_x + "/" + year_x;
                }
            } else if (day_x / 10 == 0) {

                currentdate = "0" + day_x + "/" + month_x + "/" + year_x;

            } else {
                currentdate = day_x + "/" + month_x + "/" + year_x;

            }
            Toast.makeText(GetAttendance.this,"You Selected "+currentdate, Toast.LENGTH_SHORT).show();

        }
    };

    public void initialiseViews() {


        get = (Button) findViewById(R.id.get);
        bdate = (Button) findViewById(R.id.date);
        progressDialog = new ProgressDialog(GetAttendance.this);

    }


}
