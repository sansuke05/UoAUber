package com.alicelab.uoauber;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by user on 2017/10/28.
 */

public class GetAPIConnection extends AsyncTask<Void, Void, JSONArray> {

    private MainActivity main_;
    private ProgressDialog progressDialog_ = null;

    public GetAPIConnection(MainActivity main){
        super();
        main_ = main;
    }

    @Override
    protected void onPreExecute() {

        // 進捗ダイアログを開始
        progressDialog_ = new ProgressDialog(main_);
        progressDialog_.setMessage("List Loading ...");
        progressDialog_.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog_.setCancelable(true);
        progressDialog_.show();
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        HttpURLConnection con;
        URL url;
        String urlStr = "http://222.158.238.93/get_supply_car.php";
        JSONArray jsonArray = null;
        BufferedReader reader;

        try {
            url = new URL(urlStr);
            con = (HttpURLConnection)url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(20000);
            con.setRequestMethod("GET");

            con.connect();

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

                    jsonArray = new JSONArray(readStr);
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

        return jsonArray;
    }

    @Override
    protected void onPostExecute(final JSONArray result) {
        super.onPostExecute(result);

        ArrayList<DriverData> list = new ArrayList<>();
        DriverAdapter dAdapter = new DriverAdapter(main_);
        JSONObject json;

        try {
            for (int i = 0; i < result.length(); i++) {
                json = result.getJSONObject(i);

                DriverData driver = new DriverData();
                driver.setName(json.getString("uname"));
                driver.setDepartureTime(json.getString("res_time"));
                driver.setDeparturePlace(json.getString("address"));
                list.add(driver);

                dAdapter.setDriverList(list);
                main_.listView.setAdapter(dAdapter);

                main_.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        try {
                            String user = "taka"; //デバッグ用
                            String supply_id = result.getJSONObject(position).getString("id");
                            JSONObject post_json = new JSONObject();
                            post_json.accumulate("id", user);
                            post_json.accumulate("supply_car_id", supply_id);

                            Log.d("debug",post_json.toString());

                            PostAPIConnection task = new PostAPIConnection(main_, "REQUEST", post_json.toString());
                            task.execute();

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        if (progressDialog_.isShowing()) {

            // 進捗ダイアログを終了
            progressDialog_.dismiss();
        }
    }
}
