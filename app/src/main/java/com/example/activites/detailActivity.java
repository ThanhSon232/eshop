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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class detailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton backButton;
    ImageButton cart;
    Bundle bundle;
    ImageSlider slider;
    TextView name;
    TextView price;
    TextView description;
    RatingBar ratingBar;
    Button addCart;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> image = new ArrayList<>();
    List<SlideModel> slideModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpage);
        backButton = (ImageButton) findViewById(R.id.previous);
        bundle = getIntent().getExtras();
        slider = findViewById(R.id.slider);
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
        name.setText(bundle.get("name").toString());
        price.setText(bundle.get("price").toString() + "đ");
        description.setText(bundle.get("description").toString());
        ratingBar.setRating(Float.valueOf(bundle.get("star").toString()));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailActivity.super.onBackPressed();
            }
        });
        getData();
    }

    void getData(){
        image = (ArrayList<String>) bundle.getSerializable("image");
        for(String s : image){
            slideModelList.add(new SlideModel(s));
        }
        slider.setImageList(slideModelList,false);
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
                data.put("image",bundle.get("image"));
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