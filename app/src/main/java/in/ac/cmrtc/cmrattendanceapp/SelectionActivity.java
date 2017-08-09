package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {


    private Button get;
    private Button put;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        get=(Button)findViewById(R.id.getdbAttendance);
        put=(Button)findViewById(R.id.puttoAttendance);
        get.setOnClickListener(SelectionActivity.this);
        put.setOnClickListener(SelectionActivity.this);

    }

    @Override
    public void onClick(View v) {

        if(v==get){

            Intent intent=new Intent(SelectionActivity.this,GetAttendance.class);
            startActivity(intent);

        }
        else if(v==put){

            Intent intent=new Intent(SelectionActivity.this,StudentList.class);
            startActivity(intent);
        }

    }
}
