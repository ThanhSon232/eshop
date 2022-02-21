package com.example.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class detailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton backButton;
    ImageButton cart;
    Bundle bundle;
    ImageView imageView;
    TextView name;
    TextView price;
    TextView description;
    RatingBar ratingBar;
    Button addCart;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpage);
        backButton = (ImageButton) findViewById(R.id.previous);
        bundle = getIntent().getExtras();
        imageView = findViewById(R.id.detailImage);
        name = findViewById(R.id.detailName);
        price = findViewById(R.id.detailPrice);
        description = findViewById(R.id.detailDescription);
        ratingBar = findViewById(R.id.detailRating);
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(this);
        addCart = findViewById(R.id.add_to_cart);
        addCart.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Picasso.get().load(bundle.get("image").toString()).into(imageView);
        name.setText(bundle.get("name").toString());
        price.setText(bundle.get("price").toString() + "VND");
        description.setText(bundle.get("description").toString());
        ratingBar.setRating(Float.valueOf(bundle.get("star").toString()));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailActivity.super.onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cart:
                startActivity(new Intent(this,cartActivity.class));
                break;
            case R.id.add_to_cart:
//                String uniqueID = UUID.randomUUID().toString();

                String email = mAuth.getCurrentUser().getEmail();
                Map<String,Object> data = new HashMap<>();
                data.put("name",bundle.get("name").toString());
                data.put("price",bundle.get("price").toString());
                data.put("image",bundle.get("image").toString());
                data.put("description",bundle.get("description").toString());
                data.put("star",bundle.get("star"));
                data.put("quantity",1);
                data.put("check",true);


                DocumentReference docIdRef = firebaseFirestore.collection("carts").document(email).collection("items").document(bundle.get("id").toString());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                firebaseFirestore.collection("carts").document(email).collection("items").document(bundle.get("id").toString()).update("quantity",Integer.valueOf(document.getData().get("quantity").toString()) + 1);
                            } else {
                                firebaseFirestore.collection("carts").document(email).collection("items").document(bundle.get("id").toString()).set(data);
                            }
                        }
                    }
                });

                Toast.makeText(detailActivity.this,"Success",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}