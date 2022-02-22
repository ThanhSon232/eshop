package com.example.adapter.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adapter.product.product;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;


public class cartAdapter extends ArrayAdapter<product>  {
    private Context context;
    int resource;



    public cartAdapter(@NonNull Context context, int resource, @NonNull List<product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String name = getItem(position).getName();
        String price = getItem(position).getPrice();
        String image = getItem(position).getImage().get(0);
        String quantity = getItem(position).getQuantity().toString();
        String id = getItem(position).getId();
        Boolean check = getItem(position).getCheck();


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView cart_name = convertView.findViewById(R.id.cart_name);
        TextView cart_price = convertView.findViewById(R.id.cart_price);
        ImageView imageView = convertView.findViewById(R.id.cart_image);
        Picasso.get().load(image).into(imageView);
        cart_name.setText(name);
        cart_price.setText(price+"đ");
        EditText editText = convertView.findViewById(R.id.et_number);
        editText.setText(quantity);
        Button buttonLess = convertView.findViewById(R.id.btn_less);
        buttonLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.valueOf(editText.getText().toString()) >= 2){
                    editText.setText(String.valueOf(Integer.valueOf(editText.getText().toString()) -1));
                }
                else{
                    Toast.makeText(context,"Can't be lower than 0",Toast.LENGTH_LONG).show();
                }
            }
        });
        Button buttonMore = convertView.findViewById(R.id.btn_more);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(String.valueOf(Integer.valueOf(editText.getText().toString()) +1));

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                firebaseFirestore.collection("carts").document(mAuth.getCurrentUser().getEmail()).collection("items").document(id).update("quantity",Integer.valueOf(editable.toString()));

            }
        });

        CheckBox checkBox = convertView.findViewById(R.id.checkButton);
        if(check == true){
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("carts").document(mAuth.getCurrentUser().getEmail()).collection("items").document(id).update("check",checkBox.isChecked());
            }
        });


        return convertView;

    }

}

