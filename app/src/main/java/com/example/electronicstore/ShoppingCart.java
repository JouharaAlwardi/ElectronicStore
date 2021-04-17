package com.example.electronicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ShoppingCart extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Item> list;
    RecyclerView recyclerView;
    SearchView searchView;
    FirebaseAuth fAuth;
    ShoppingCartAdapter adapterClass;
    TextView totalValue;
    double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(fAuth.getCurrentUser().getUid());
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);



        list = new ArrayList<>();
        adapterClass = new ShoppingCartAdapter(list, this);
        recyclerView.setAdapter(adapterClass);


        //double price = adapterClass.getGrandTotal();
        totalValue = findViewById(R.id.totalValue);


    }




    @Override
    protected void onStart() {

        super.onStart();
        if(ref != null)
        {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {

                        for(DataSnapshot ds : snapshot.getChildren())
                        {

                            Item userObj = ds.getValue(Item.class);
                            list.add(userObj);
                            adapterClass.notifyItemInserted(list.size()-1);
                            recyclerView.setAdapter(adapterClass);




                        }


                         for (int i = 0; i <= list.size()-1; i++) {
                            total = total + Double.parseDouble(list.get(i).getPrice());
                             totalValue.setText(String.valueOf(total));
                         }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ShoppingCart.this, error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }



    }



    }
