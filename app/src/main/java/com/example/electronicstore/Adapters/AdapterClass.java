package com.example.electronicstore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.electronicstore.Objects.Item;
import com.example.electronicstore.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                //int num = Integer.parseInt(list.get(i).getStock())-1;
                //list.get(i).setStock(String.valueOf(num));
                addToCart(view, list.get(i).getTitle(), list.get(i).getManufacturer(), list.get(i).getCategory(), list.get(i).getPrice(),  list.get(i).getStock(),list.get(i).getImage() );
                Toast.makeText(context, "Item Added to Cart", Toast.LENGTH_SHORT).show();
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
            titleView = itemView.findViewById(R.id.orderNum);
            manuView = itemView.findViewById(R.id.purchaseName);
            image = itemView.findViewById(R.id.imageViewOrder);
            categoryView = itemView.findViewById(R.id.purchasePrice);
            priceView = itemView.findViewById(R.id.purchaseManu);
            stockView = itemView.findViewById(R.id.purchaseCategory);
            btn = itemView.findViewById(R.id.updateData);

        }

    }

    public void addToCart(View view, String title, String manufacturer, String category, String price, String stock, String image) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        int min = 50;
        int max = 100;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("Cart").child(uid);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference uidRef = dataSnapshot.child("Item" + String.valueOf(random_int)).getRef();
                uidRef.child("title").setValue(title);
                uidRef.child("manufacturer").setValue(manufacturer);
                uidRef.child("category").setValue(category);
                uidRef.child("price").setValue(price);
                uidRef.child("image").setValue(image);
                uidRef.child("stock").setValue(stock);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage(); //Don't ignore errors!
            }
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);



    }
}
