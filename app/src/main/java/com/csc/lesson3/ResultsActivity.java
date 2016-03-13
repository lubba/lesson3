package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResultsActivity extends Activity {

    private static final String TAG = "ResultsActivity";
    private static final String KEY = "trnsl.1.1.20160312T231610Z.10faba7791652fe5.1196e76facb0bcd91164ec9e4a1bc41958df57f4";
    private static final String lang = "ru-en";

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

        String address = String.format("https://translate.yandex.net/api/v1.5/tr/translate?key=%s&text=%s&lang=%s", KEY, query, lang);
        ConnectTranslatorTask translatorTask = new ConnectTranslatorTask();

        String directory = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d(TAG, "onCreate: path = " + directory);
        translatorTask.execute(address, directory);
        try {
            List<String> list = translatorTask.get();
            List<Card> cards = new ArrayList<>();

            for (String translation : list) {
                Card card = new Card(translation, null);
                cards.add(card);
                Log.d(TAG, "onCreate: adding " + translation);
            }

            recyclerView.setAdapter(new RVAdapter(cards));
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "onCreate: failed to translate: ", e);
        }




    }


}
