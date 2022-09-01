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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullName ,editTextAge, editTextEmail ,editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        TextView banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        TextView bannerDescription = findViewById(R.id.bannerDescription);
        bannerDescription.setOnClickListener(this);

        Button loginReg=findViewById(R.id.btnLoginReg);
        loginReg.setOnClickListener(this);

        TextView registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName= findViewById(R.id.fullName);
        editTextAge= findViewById(R.id.age);
        editTextEmail= findViewById(R.id.email);
        editTextPassword= findViewById(R.id.password);
        progressBar= findViewById(R.id.progressBar);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.banner:
            case R.id.bannerDescription:
            case R.id.btnLoginReg:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }


    private void registerUser() {

        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String fullName=editTextFullName.getText().toString().trim();
        String age=editTextAge.getText().toString().trim();

        if(fullName.isEmpty() ){
            editTextFullName.setError("Full Name is Required");
            editTextFullName.requestFocus();
            return;
        }

        if(age.isEmpty() ){
            editTextAge.setError("Full Name is Required");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty() ){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if( ! Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid Email");
            editTextEmail.requestFocus();
            return;
        }


        if(password.isEmpty() ){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6 ){
            editTextPassword.setError("Minimum password length is six ");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            User user = new User(fullName, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterUser.this, "User Has Been Registered Sucessfully", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterUser.this,MainActivity.class));
                                                //REDIRECT TO LOGIN
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Failed to register Please try again", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register!!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}