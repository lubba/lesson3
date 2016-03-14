package com.csc.lesson3;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectTranslatorTask extends AsyncTask<String, Void, List<String>> {
    private static final String TAG = "ConnectTranslatorTask";
    HashMap<Integer, String> serverAnswers = new HashMap<>();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        serverAnswers.put(200, "Операция выполнена успешно");
        serverAnswers.put(401, "Неправильный ключ API");
        serverAnswers.put(402, "Ключ API заблокирован");
        serverAnswers.put(403, "Превышено суточное ограничение на количество запросов");
        serverAnswers.put(404, "Превышено суточное ограничение на объем переведенного текста");
        serverAnswers.put(413, "Превышен максимально допустимый размер текста");
        serverAnswers.put(422, "Текст не может быть переведен");
        serverAnswers.put(501, " Заданное направление перевода не поддерживается");
    }

    @Override
    protected List<String> doInBackground(String... params) {
        String address = params[0];
        try {
            URL url = new URL(address);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int responseCode = connection.getResponseCode();
            Log.d(TAG, "doInBackground: response code = " + responseCode + " : " + serverAnswers.get(responseCode));

            if (responseCode > 200) {
                return null;
            }

            InputStream inputStream = connection.getInputStream();
            InputSource source = new InputSource(inputStream);
            source.setEncoding("Cp1251");

            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            //parser.setInput(inputStream, null);
            parser.setInput(new BufferedReader(new InputStreamReader(source.getByteStream())));
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.TEXT) {
                    Log.d(TAG, "doInBackground: text " + parser.getText());
                    list.add(parser.getText());
                }
                parser.next();
            }
            inputStream.close();

        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "doInBackground: ", e);
        }

        return list;
    }

}
