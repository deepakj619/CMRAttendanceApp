package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    private static int id;     //to store id of each branch
    private Button cse;
    private Button ece;
    private Button mech;
    private Button civil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Select Branch");
        setContentView(R.layout.activity_main);

        cse=(Button)findViewById(R.id.cse);
        ece=(Button)findViewById(R.id.ece);
        mech=(Button)findViewById(R.id.mech);
        civil=(Button)findViewById(R.id.civil);

        ece.setVisibility(View.GONE);
        mech.setVisibility(View.GONE);
        civil.setVisibility(View.GONE);

        cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MainActivity.this,Year.class);
                id = 5;
                startActivity(open);
            }
        });
        ece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MainActivity.this,Year.class);
                id = 4;
                startActivity(open);
            }
        });
        mech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MainActivity.this,Year.class);
                id = 3;
                startActivity(open);
            }
        });
        civil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MainActivity.this,Year.class);
                id = 1;
                startActivity(open);
            }
        });

    }
    public static String getBranch(){
        switch (getId()){
            case 1:
                return "CIVIL";
            case 3:
                return "MECH";
            case 4:
                return "ECE";
            case 5:
                return "CSE";
        }
        return null;
    }

    public static int getId(){
        return id;
    }


}