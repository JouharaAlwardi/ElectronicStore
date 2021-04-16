package com.example.electronicstore;

import android.content.Context;
import android.content.Intent;
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

        holder.name.setText(list.get(i).getTitle());
        //holder.tag.setText(list.get(i).getTAG());
        holder.description.setText(list.get(i).getManufacturer());
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
        TextView name, description, tag, coffeeRatio;
        ImageView image;
        ImageView grindImage;
        Button btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameView);
            description = itemView.findViewById(R.id.descriptionView);

            tag = itemView.findViewById(R.id.tagView);
            image = itemView.findViewById(R.id.imageView);


            btn = itemView.findViewById(R.id.startBrew);

        }

    }
}
