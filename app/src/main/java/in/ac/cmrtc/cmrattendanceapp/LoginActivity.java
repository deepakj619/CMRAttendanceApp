package in.ac.cmrtc.cmrattendanceapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity{

    private EditText email;
    private EditText password;
    private Button login;
    private ProgressDialog progressDialog;
    private TextView forgottv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){

            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        email=(EditText)findViewById(R.id.etEmail);
        password=(EditText)findViewById(R.id.etPassword);
        forgottv=(TextView)findViewById(R.id.forgot);
        login=(Button)findViewById(R.id.bLogin);
        progressDialog = new ProgressDialog(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userlogin();

            }
        });
        forgottv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this,ResetPassword.class);
                startActivity(intent);
            }
        });

    }

    private void userlogin(){

        String Email=email.getText().toString().trim();
        String Password=password.getText().toString().trim();

        if (TextUtils.isEmpty(Email)) {

            email.setError("Email can not be empty");
            return;
        }
        if (TextUtils.isEmpty(Password)) {

            password.setError("Please Enter Password");
            return;
        }

        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){

                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{

                            try{

                                throw  task.getException();
                                
                            }catch (FirebaseAuthInvalidUserException e){


                                Toast.makeText(LoginActivity.this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e){

                                Toast.makeText(LoginActivity.this,"Please Enter Valid Password",Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });
    }


}
