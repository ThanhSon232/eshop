package com.example.adapter.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activites.detailActivity;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productAdapter extends RecyclerView.Adapter<productAdapter.productViewHolder>{
    List<product> productlist;
    Context context;

    public void setProductlist(Context context,List<product> productlist) {
        this.context = context;
        this.productlist = productlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardexplore,parent,false);
        return new productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        product product = productlist.get(position);
        if(product == null) return;

        Picasso.get().load(product.getImage().toString()).into(holder.image);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice() +"đ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id",product.getId());
                bundle.putSerializable("name",product.getName());
                bundle.putSerializable("price",product.getPrice());
                bundle.putSerializable("image",product.getImage());
                bundle.putSerializable("description",product.getDescription());
                bundle.putSerializable("star",product.getStar());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(!productlist.isEmpty()) return productlist.size();
        return 0;
    }

    public class productViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView price;
        CardView cardView;
        public productViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exploreImage);
            name = itemView.findViewById(R.id.exploreName);
            price = itemView.findViewById(R.id.explorePrice);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
