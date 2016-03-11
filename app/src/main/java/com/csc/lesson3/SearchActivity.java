package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends Activity {

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button searchButton = (Button) findViewById(R.id.search_action);
        final EditText searchLine = (EditText) findViewById(R.id.search_line);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchLine.getText().toString();

                text = text.trim().toLowerCase();
                Log.d(TAG, "onClick: Text = " + text);
                if (check(text)) {
                    Intent intent = new Intent(getBaseContext(), ResultsActivity.class);
                    intent.putExtra("SEARCH_QUERY", text);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.query_check_error, Toast.LENGTH_LONG);
                    toast.show();
                    Log.e(TAG, "onClick: check returned false");
                }
            }
        });
    }

    private boolean check(String query) {
        return !query.contains(" ");
    }
}
