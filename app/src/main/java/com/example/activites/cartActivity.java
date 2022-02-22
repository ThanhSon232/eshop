package com.example.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.cart.cartAdapter;
import com.example.adapter.category.category;
import com.example.adapter.product.product;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cartActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<product> arrayList = new ArrayList<>();
    FirebaseFirestore db;
    cartAdapter cartAdapter;
    FirebaseAuth firebaseAuth;
    ImageButton previous;
    CardView cardView;
    TextView textView;
    TextView textView1;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_cart);
        previous = findViewById(R.id.previous);
        previous.setOnClickListener(this);
        cardView = findViewById(R.id.card_bottom);
        ListView listView = findViewById(R.id.list_view);
        textView = findViewById(R.id.selected_price);
        textView1 = findViewById(R.id.tax);
        textView2 = findViewById(R.id.totalPrice);
        firebaseAuth = FirebaseAuth.getInstance();
        cartAdapter = new cartAdapter(this,R.layout.cart_item,arrayList);
        listView.setAdapter(cartAdapter);
        getChange();

    }


    void getChange(){
        db = FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            db.collection("/carts/" + firebaseAuth.getCurrentUser().getEmail() + "/items").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        arrayList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            arrayList.add(new product(document.getId(), document.getData().get("name").toString(), (ArrayList<String>) document.getData().get("image"), document.getData().get("description").toString(), document.getData().get("price").toString(), Float.valueOf(document.getData().get("star").toString()), Integer.valueOf(document.getData().get("quantity").toString()), Boolean.valueOf(document.getData().get("check").toString()),document.getData().get("category").toString()));
                        }
                        cartAdapter.notifyDataSetChanged();
                        int total = 0;
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                        for(product i : arrayList){
                            if(i.getCheck()){
                                String[] str = i.getPrice().split(",");
                                String price = "";
                                for(String s : str) price += s;
                                total += Integer.valueOf(price.trim()) * i.getQuantity();
                            }
                        }
                        textView.setText(currencyVN.format(total));
                        textView1.setText(currencyVN.format(total*0.05));
                        textView2.setText(currencyVN.format(total+total*0.05));
                }
            });
        } else {
            startActivity(new Intent(this, loginActivity.class));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:
                super.onBackPressed();
                break;

        }
    }
}