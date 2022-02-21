package com.example.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class category_list_adapter extends RecyclerView.Adapter<category_list_adapter.category_list_viewholder> {
    List<categoryModel> item = new ArrayList<>();
    Context context;

    public void setItem(List<categoryModel> item) {
        this.item = item;
    }

    public category_list_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public category_list_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bestselling,parent,false);
        return new category_list_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull category_list_viewholder holder, int position) {
        categoryModel categoryModel = item.get(position);
        if(categoryModel == null) return;
        Picasso.get().load(categoryModel.getImage().toString()).into(holder.imageview);
        holder.textView.setText(categoryModel.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,categoryModel.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class category_list_viewholder extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView textView;
        CardView cardView;
        public category_list_viewholder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.image1);
            textView = itemView.findViewById(R.id.name1);
            cardView = itemView.findViewById(R.id.card1);
        }
    }
}
