package com.example.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class profileActivity extends AppCompatActivity implements  View.OnClickListener {
    ImageButton previous;
    ImageButton close;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    TextView name;
    TextView email;
    TextView address;
    TextView phoneNumber;
    ImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        previous = findViewById(R.id.previous);
        close = findViewById(R.id.close);
        name = findViewById(R.id.profileName);
        email = findViewById(R.id.Email);
        address = findViewById(R.id.Address);
        phoneNumber = findViewById(R.id.PhoneNumber);
        avatar = findViewById(R.id.avatar);
        previous.setOnClickListener(this);
        close.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getData();
    }

    void getData(){
        Task<DocumentSnapshot> temp = firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid()).get();
        temp.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getData().get("name").toString());
                email.setText(documentSnapshot.getData().get("email").toString());
                address.setText(documentSnapshot.getData().get("address").toString());
                phoneNumber.setText(documentSnapshot.getData().get("phoneNumber").toString());
                Picasso.get().load(documentSnapshot.getData().get("image").toString()).into(avatar);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:
                super.onBackPressed();
                break;
            case R.id.close:
                mAuth.signOut();
                Toast.makeText(this,"Success",Toast.LENGTH_SHORT);
                super.onBackPressed();
                break;
        }
    }
}