package com.example.bmpmessanger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginIn extends AppCompatActivity {

    FirebaseAuth auth;
    EditText Email ;
    EditText Pass ;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        auth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.editLogEmailAddress);
        Pass = findViewById(R.id.editLogPassword);
        Button Login = findViewById(R.id.Loginbutton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailReal = Email.getText().toString();
                String PassReal = Pass.getText().toString();

                if(TextUtils.isEmpty(EmailReal))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Please Enter the Email !!!",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(PassReal))
                {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Please Enter the Password !!!",Toast.LENGTH_SHORT).show();
                }
                else if(!EmailReal.matches(emailPattern))
                {
                    progressDialog.dismiss();
                    Email.setError("Please Give proper Email !!!");
                }
                else if(PassReal.length()<6)
                {
                    progressDialog.dismiss();
                    Pass.setError("More than six characters are Required !!!");
                    Toast.makeText(getApplicationContext(),"Password needs To be Longer than six Characters !",Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(EmailReal,PassReal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.show();
                                Intent intent = new Intent(LoginIn.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginIn.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        TextView textView = findViewById(R.id.SIgnUpTextView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginIn.this,registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}