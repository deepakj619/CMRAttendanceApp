package in.ac.cmrtc.cmrattendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ResetPassword extends AppCompatActivity {

    private EditText email;
    private Button change;
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        final FirebaseAuth auth=FirebaseAuth.getInstance();
        email=(EditText)findViewById(R.id.Femail);
        change=(Button) findViewById(R.id.FLogin);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email=email.getText().toString();
                if (TextUtils.isEmpty(Email)) {

                    email.setError("Email can not be blank");
                    return;
                }
                auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(ResetPassword.this, "Check your email to Change Password", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(ResetPassword.this,LoginActivity.class);
                            startActivity(intent);

                        }
                        else{

                            try{

                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){


                                Toast.makeText(ResetPassword.this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

    }
}
