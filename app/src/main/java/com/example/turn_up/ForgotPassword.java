package com.example.turn_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText resetEmail;
    private Button btnResetPassword;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetEmail=findViewById(R.id.resetEmail);
         btnResetPassword=findViewById(R.id.btnResetPassword);
        progressBar=findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        String resEmail=resetEmail.getText().toString().trim();

        if(resEmail.isEmpty()){
            resetEmail.setError("Email is required");
            resetEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(resEmail).matches()){
            resetEmail.setError("Enter a valid email");
            resetEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(resEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"A reset password request has been sent to your email!!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                    return;
                }else{
                    Toast.makeText(ForgotPassword.this, "Something went wrong!Please try again", Toast.LENGTH_LONG).show();
                    resetEmail.requestFocus();
                    return;
                }

            }
        });

    }
}