package com.example.electronicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<Item> list;
    RecyclerView recyclerView;
    SearchView searchView;
    FirebaseAuth fAuth;
    AdapterClass adapterClass;
    CheckBox titleButton, manuButton, categoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);


        //checkboxes
        titleButton = findViewById(R.id.titleButton);
        manuButton = findViewById(R.id.manuButton);
        categoryButton = findViewById(R.id.categoryButton);

        //override checkboxes

        titleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    manuButton.setChecked(false);
                    categoryButton.setChecked(false);
                }
            }
        });
        manuButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    categoryButton.setChecked(false);
                    titleButton.setChecked(false);
                }
            }
        });
        categoryButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    manuButton.setChecked(false);
                    titleButton.setChecked(false);
                }
            }
        });



        list = new ArrayList<>();
        adapterClass = new AdapterClass(list, this);
        recyclerView.setAdapter(adapterClass);


        Button dialogButton = (Button) findViewById(R.id.s_button);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });




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

            if(titleButton.isChecked()) {
                if (object.getTitle().toLowerCase().contains(str.toLowerCase())) {
                    mylist.add(object);

                    adapterClass.notifyItemInserted(mylist.size() - 1);

                }
            }

            if(manuButton.isChecked()) {
                if (object.getManufacturer().toLowerCase().contains(str.toLowerCase())) {
                    mylist.add(object);

                    adapterClass.notifyItemInserted(mylist.size() - 1);

                }
            }

            if(categoryButton.isChecked()) {
                if (object.getCategory().toLowerCase().contains(str.toLowerCase())) {
                    mylist.add(object);

                    adapterClass.notifyItemInserted(mylist.size() - 1);

                }
            }
            //recyclerView.setAdapter(adapterClass);
            AdapterClass adapterClass = new AdapterClass(mylist, this);
            recyclerView.setAdapter(adapterClass);

        }

    }

    public void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);



        Button dialogButton = (Button) dialog.findViewById(R.id.sortButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}