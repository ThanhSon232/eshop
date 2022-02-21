package com.example.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton backButton;
    Button loginButton;
    Button registerButton;
    TextInputEditText emailC;
    TextInputEditText passwordC;
    TextInputEditText cPasswordC;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        backButton = (ImageButton) findViewById(R.id.previous);
        backButton.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(view -> {
            createAccount();
        });
        emailC =  (TextInputEditText) findViewById(R.id.email);
        passwordC = (TextInputEditText) findViewById(R.id.registerPassword);
        cPasswordC = (TextInputEditText) findViewById(R.id.confirmPassword);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                registerActivity.super.onBackPressed();
        }
    }

    void createAccount(){
        String email = emailC.getText().toString();
        String password = passwordC.getText().toString();
        String confirmPassword = cPasswordC.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailC.setError("Email cannot be empty");
            emailC.requestFocus();
        }
        else
        if(TextUtils.isEmpty(password)){
            passwordC.setError("Password cannot be empty");
            passwordC.requestFocus();
        }
        else
        if(TextUtils.isEmpty(confirmPassword)){
            cPasswordC.setError("Password cannot be empty");
            cPasswordC.requestFocus();
        }
        else
        if(!TextUtils.equals(password,confirmPassword)){
            cPasswordC.setError("Confirmed password doesn't match");
            cPasswordC.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(registerActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                        String userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                        Map<String,Object> infor = new HashMap<>();
                        infor.put("email",email);
                        infor.put("name","Anonymous");
                        infor.put("address","Updating...");
                        infor.put("phoneNumber","Updating...");
                        infor.put("image","https://i.ibb.co/L5knT0t/sample2.jpg");
                        documentReference.set(infor).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Tag","Success");
                            }
                        });
                        registerActivity.super.onBackPressed();
                    }
                    else{
                        Toast.makeText(registerActivity.this,"Registration error: "+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}