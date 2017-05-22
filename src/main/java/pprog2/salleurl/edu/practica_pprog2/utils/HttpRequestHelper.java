package pprog2.salleurl.edu.practica_pprog2.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mundirisa on 16/05/2017.
 */

public class HttpRequestHelper {

    private static HttpRequestHelper instance = null;
    private final int DEFAULT_TIMEOUT = 500;

    private HttpRequestHelper(){}

    public static HttpRequestHelper getInstance() {
        if (instance == null) {
            instance = new HttpRequestHelper();
        }

        return instance;
    }

    public JSONArray doHttpRequest(String url, String method) {
        HttpURLConnection c = null;
        JSONArray jsonArray = new JSONArray();
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod(method);
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(DEFAULT_TIMEOUT);
            c.setReadTimeout(DEFAULT_TIMEOUT);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();

                    jsonArray = new JSONArray(sb.toString());
            }

        } catch (Exception ex) {
            Log.e(getClass().getName(), "Exception", ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Log.e(getClass().getName(), "Exception", ex);
                }
            }
        }

        return jsonArray;
    }


    public Bitmap doHttpRequestForBitmap (String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

        InputStream is = connection.getInputStream();
        Bitmap img = BitmapFactory.decodeStream(is);

        return img;
    }

}