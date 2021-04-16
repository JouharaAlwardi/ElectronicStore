package com.example.electronicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Item> list;
    RecyclerView recyclerView;
    SearchView searchView;
    FirebaseAuth fAuth;
    AdapterClass adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);


        list = new ArrayList<>();
        adapterClass = new AdapterClass(list, this);
        recyclerView.setAdapter(adapterClass);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ref != null) {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            Item userObj = ds.getValue(Item.class);
                            list.add(userObj);
                            adapterClass.notifyItemInserted(list.size() - 1);
                            recyclerView.setAdapter(adapterClass);

                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }

        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);

                    return true;
                }
            });

        }

    }

    private void search(String str) {
        ArrayList<Item> mylist = new ArrayList<>();
        for (Item object : list) {

            if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                mylist.add(object);
                //System.out.println(object.getTAG());
                adapterClass.notifyItemInserted(mylist.size() - 1);


            }
            //recyclerView.setAdapter(adapterClass);
            AdapterClass adapterClass = new AdapterClass(mylist, this);
            recyclerView.setAdapter(adapterClass);

        }

    }
}