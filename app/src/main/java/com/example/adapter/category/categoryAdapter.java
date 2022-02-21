package com.example.adapter.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activites.allProductActivity;
import com.example.activites.registerActivity;
import com.example.myapplication.R;
import com.example.adapter.product.productAdapter;

import java.io.Serializable;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.categoryViewHolder>{
    Context context;
    List<category> categoryList;


    public categoryAdapter(Context context) {
        this.context = context;
    }



    public void setCategoryList(List<category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new categoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        category category = categoryList.get(position);
        if(category == null) return;
        holder.textView.setText(category.getName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        productAdapter productAdapter = new productAdapter();
        productAdapter.setProductlist(context,category.getProductList());
        holder.recyclerView.setAdapter(productAdapter);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("category",category.getName());
                Intent intent = new Intent(context, allProductActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(!categoryList.isEmpty()) return categoryList.size();
        return 0;
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView textView;
        Button button;
        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rcv_product);
            textView = itemView.findViewById(R.id.sectionName);
            button = itemView.findViewById(R.id.all);
        }
    }
}
