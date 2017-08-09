package in.ac.cmrtc.cmrattendanceapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Display extends AppCompatActivity {

    File imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(MainActivity.getBranch()+" - "+Year.getYear()+" - " + Section.getSecLetter()
                + " - List of students");
        setContentView(R.layout.activity_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(StudentList.getText());
        final Button screen = (Button) findViewById(R.id.screen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking Storage Permissions are granted or not
                if (ContextCompat.checkSelfPermission(Display.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //If permission is not granted requestin+g the user to grant the permission
                    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
                    ActivityCompat.requestPermissions(Display.this,new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                // Checking Storage Permissions are granted or not
                //if granted take screen Short and save it to internal storage
                if (ContextCompat.checkSelfPermission(Display.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Getting only the app screen and all other details are set invisible
                    screen.setVisibility(View.GONE);
                    //takeScreenshot() method gives a bitmap image
                    Bitmap bitmap = takeScreenshot();
                    //saveBitMap() saves the picture
                    saveBitmap(bitmap);
                    //Retriving the display to normal
                    screen.setVisibility(View.VISIBLE);
                    //Giving feedback to user regarding the screen short
                    Toast.makeText(Display.this, "Screen Captured", Toast.LENGTH_SHORT).show();
                    //The Location where it is stored
                    Toast.makeText(Display.this, StudentList.getInfo() +
                                    ".png is  saved to (Attendance List) folder in device",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    //Hinting the user to grant permission
                    Toast.makeText(Display.this, "Please Grant permission to store", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //This method returns the screen short
    public Bitmap takeScreenshot() {
        //Getting the id of current view that is displayed on the screen
        View rootView = findViewById(android.R.id.content).getRootView();
        //Capturing the view
        rootView.setDrawingCacheEnabled(true);
        //returning the captured image
        return rootView.getDrawingCache();
    }
    //This Method Stores the ScreenShort
    public void saveBitmap(Bitmap bitmap) {
        //Checking weather the folder (Attendance List) Exists in the directory or not
        File file = new File(Environment.getExternalStorageDirectory(), "/Attendance List/");
        if (!file.exists()) { // if the file doesn't exist
            if (!file.mkdirs()) { //try creating a new directory
                //If creation fails logs this message
                Log.e("DirectoryErr :: ", "Problem creating Image folder");
            }
        }
        //Setting the image name and path
        imagePath = new File(Environment.getExternalStorageDirectory() +
                "/Attendance List/"+StudentList.getInfo()+".jpg");
        //writing the file into disk
        FileOutputStream fos;
        try {
            //creating new file
            fos = new FileOutputStream(imagePath);
            //setting thr type of the file
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //clearing the temporary memory
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) { //Logging Errors
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }



}