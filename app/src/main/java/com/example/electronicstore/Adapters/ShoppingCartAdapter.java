package com.example.electronicstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronicstore.Objects.Item;
import com.example.electronicstore.R;

import java.util.ArrayList;

public class ShoppingCartAdapter  extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    ArrayList<Item> list;
    Context context;
    double totalPrice;





    public ShoppingCartAdapter(ArrayList<Item> list, Context context) {

        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ShoppingCartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_product_holder, parent, false);
        return new ShoppingCartAdapter.MyViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.MyViewHolder holder, int i) {

        holder.titleView.setText(list.get(i).getTitle());
        holder.manuView.setText(list.get(i).getManufacturer());
        holder.priceView.setText(list.get(i).getPrice()+"$");
        Glide.with(context).load(list.get(i).getImage()).into(holder.image);




        }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, manuView, priceView;
        ImageView image;





        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.orderNum);
            manuView = itemView.findViewById(R.id.purchaseName);
            image = itemView.findViewById(R.id.imageViewOrder);
            priceView = itemView.findViewById(R.id.purchaseManu);


        }

    }







}