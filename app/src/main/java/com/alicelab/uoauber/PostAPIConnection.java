package com.alicelab.uoauber;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 2017/10/28.
 */

public class PostAPIConnection extends AsyncTask<Void, Void, JSONObject> {
    private Context context;
    private ProgressDialog progressDialog_ = null;
    private String requestMode = null;
    private String post_json = null;

    public PostAPIConnection(Context context, String requestMode, String post_json){
        super();
        this.context = context;
        this.requestMode = requestMode;
        this.post_json = post_json;
    }

    @Override
    protected void onPreExecute() {

        // 進捗ダイアログを開始
        progressDialog_ = new ProgressDialog(context);
        progressDialog_.setMessage("Sending message ...");
        progressDialog_.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog_.setCancelable(true);
        progressDialog_.show();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        HttpURLConnection con;
        URL url;
        String urlStr;
        JSONObject get_json = null;
        BufferedReader reader;
        OutputStream os;
        PrintStream ps;

        if (requestMode.equals("REGISTER")) urlStr = "http://222.158.238.93/register.php";
        else urlStr = "";

        try {
            url = new URL(urlStr);
            con = (HttpURLConnection)url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(20000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            //make request body
            os = con.getOutputStream();
            ps = new PrintStream(os);
            ps.print(post_json);

            con.connect();
            ps.close();

            int status = con.getResponseCode();

            switch (status) {
                case HttpURLConnection.HTTP_OK:
                    InputStream in = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    String readStr = new String();

                    while (null != (line = reader.readLine())){
                        readStr += line;
                    }
                    Log.d("GetAPIConnection","read string: " + readStr);

                    in.close();

                    get_json = new JSONObject(readStr);
                    break;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    break;
                default:
                    Log.e("GetAPIConnection","http connection error");
                    break;
            }

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return get_json;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        if (progressDialog_.isShowing()) {

            // 進捗ダイアログを終了
            progressDialog_.dismiss();
        }

        if (result == null){
            Toast.makeText(context, "Connection Error!", Toast.LENGTH_LONG).show();
            return;
        }

        if (requestMode.equals("REGISTER")) {
            try {
                if (result.getString("success").equals("1")) {
                    Toast.makeText(context, "会員登録完了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Register Filed! \nPlease try again!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
