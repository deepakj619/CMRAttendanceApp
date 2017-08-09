package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Semester extends AppCompatActivity {

    public static int sem;
    private Button firstsem;
    private Button secondsem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firstsem=(Button)findViewById(R.id.firstsem);
        secondsem=(Button)findViewById(R.id.secondsem);

        firstsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sem=1;
                Intent intent=new Intent(Semester.this,Section.class);
                startActivity(intent);


            }
        });

        secondsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sem=2;
                Intent intent=new Intent(Semester.this,Section.class);
                startActivity(intent);
            }
        });


    }

    public static int getSem(){


        return sem;
    }
}
