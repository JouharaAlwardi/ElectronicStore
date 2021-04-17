package com.example.electronicstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder>{

    ArrayList<Item> list;
    Context context;


    public AdapterClass(ArrayList<Item> list,Context context ) {

        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_holder, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.titleView.setText(list.get(i).getTitle());
        holder.manuView.setText(list.get(i).getManufacturer());
        holder.categoryView.setText(list.get(i).getCategory());
        holder.priceView.setText(list.get(i).getPrice()+"$");
        holder.stockView.setText("Stock: "+list.get(i).getStock());
        Glide.with(context).load(list.get(i).getImage()).into(holder.image);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent3 = new Intent(context, DisplaySteps.class);
                //context.startActivity(intent3);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView titleView, manuView, categoryView, priceView, stockView;
        ImageView image;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.titleView);
            manuView = itemView.findViewById(R.id.manuView);
            image = itemView.findViewById(R.id.imageView);
            categoryView = itemView.findViewById(R.id.categoryView);
            priceView = itemView.findViewById(R.id.priceView);
            stockView = itemView.findViewById(R.id.stockView);
            btn = itemView.findViewById(R.id.startBrew);

        }

    }
}
