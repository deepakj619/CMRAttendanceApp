package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Section extends AppCompatActivity {

    private static int sec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.getBranch()+" - "+Year.getYear()+" - Section");
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button A = (Button) findViewById(R.id.cse);
        Button B= (Button) findViewById(R.id.ece);
        Button C = (Button) findViewById(R.id.mech);
        Button D = (Button) findViewById(R.id.civil);
        //Setting the text for the buttons
        A.setText("A");
        B.setText("B");
        C.setText("C");
        D.setText("D");
        //Adjusting sections based on the branch and year
        int branch = MainActivity.getId();
        int year = Year.getYear();
        if(branch == 1){

            C.setVisibility(View.GONE);
            D.setVisibility(View.GONE);

        } else if(branch == 3){
            D.setVisibility(View.GONE);
        }
        //Adding action listener to the buttons
        //and assigning the respective sections to the variables
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = 1;
                Intent open = new Intent(Section.this,SelectionActivity.class);
                startActivity(open);



            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = 2;
                Intent open = new Intent(Section.this,SelectionActivity.class);
                startActivity(open);


            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = 3;
                Intent open = new Intent(Section.this,SelectionActivity.class);
                startActivity(open);


            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sec = 4;
                Intent open = new Intent(Section.this,SelectionActivity.class);
                startActivity(open);

            }
        });

    }

    public static int getSec(){
        return sec;
    }

    public static char getSecLetter(){
        switch (getSec()){
            case 1:
                return 'A';
            case 2:
                return 'B';
            case 3:
                return 'C';
            case 4:
                return 'D';
        }
        return ' ';
    }


}

