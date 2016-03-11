package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ResultsActivity extends Activity {

    private static final String TAG = "ResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        String query = intent.getStringExtra("SEARCH_QUERY");
        Log.d(TAG, "onCreate: Received query: " + query);
    }
}
