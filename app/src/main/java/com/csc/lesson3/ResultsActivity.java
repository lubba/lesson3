package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends Activity {

    private static final String TAG = "ResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String query = intent.getStringExtra("SEARCH_QUERY");
        Log.d(TAG, "onCreate: Received query: " + query);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.smile_icon));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.smile_icon));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.smile_icon));

        recyclerView.setAdapter(new RVAdapter(persons));

    }


}
