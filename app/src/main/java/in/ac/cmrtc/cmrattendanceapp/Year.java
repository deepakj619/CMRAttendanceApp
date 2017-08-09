package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Year extends AppCompatActivity {

    private static int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.getBranch() + " - YEAR");
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button first = (Button) findViewById(R.id.cse);
        Button second= (Button) findViewById(R.id.ece);
        Button third = (Button) findViewById(R.id.mech);
        Button fourth = (Button) findViewById(R.id.civil);

        first.setText("I");
        first.setVisibility(View.GONE);
        second.setText("II");
        third.setText("III");
        fourth.setText("IV");

        //Assigning the action listeners to each button and year to variable
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Year.this,Semester.class);
                year = 1;
                startActivity(open);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Year.this,Semester.class);
                year = 2;
                startActivity(open);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Year.this,Semester.class);
                year = 3;
                startActivity(open);
            }
        });

        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Year.this,Semester.class);
                year = 4;
                startActivity(open);
            }
        });

    }
    public static int getYear(){

        return year;
    }

}
