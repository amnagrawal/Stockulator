package com.aman.stockulator;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Company extends AppCompatActivity {

    //TextView date;
    DatePickerDialog datePickerDialog;
    Button decidePredictionButton,detailsbutton;
    TextView companyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        companyTextView = (TextView)findViewById(R.id.companyTextView);
        companyTextView.setText(getIntent().getStringExtra("Name"));

        ///decide prediction Button
        decidePredictionButton = (Button)findViewById(R.id.decidePredictionbutton);
        decidePredictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Company.this, Predictions.class);
                mIntent.putExtra("Name", companyTextView.getText());
                startActivity(mIntent);
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        detailsbutton = (Button)findViewById(R.id.detailsbutton);
        detailsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(Company.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar now = Calendar.getInstance();
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        sendRequest(sdf.format(newDate.getTimeInMillis()));


                        //date.setText(sdf.format(newDate.getTimeInMillis()));
                    }

                    public void sendRequest(String date) {

                        final ProgressDialog dialog = new ProgressDialog(Company.this);
                        dialog.setMessage("Please Wait...");
                        dialog.show();

                        String url = "http://10.60.48.37:5000/"+companyTextView.getText()+"/"+date;
                        Log.i("url", url);

                        JsonObjectRequest jsonRequest = new JsonObjectRequest
                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // the response is already constructed as a JSONObject!
                                        JSONArray plot_details;
                                        JSONObject stock_details;
                                        int[] data_pts;
                                        double high, low, open, close;
                                        int volume;

                                        try {
                                            plot_details = response.getJSONArray("graph_plot");
                                            stock_details = response.getJSONObject("stock_details");

                                            data_pts = new int[plot_details.length()];

                                            for (int i = 0; i < plot_details.length(); i++) {
                                                JSONObject jsonObject = plot_details.getJSONObject(i);
                                                data_pts[i] = jsonObject.getInt("open_price");
                                            }

                                            high = stock_details.getDouble("High");
                                            low = stock_details.getDouble("Low");
                                            open = stock_details.getDouble("Open");
                                            close = stock_details.getDouble("Close");
                                            volume = stock_details.getInt("Volume");

                                            Intent intent = new Intent(Company.this, lastdayinfo.class);

                                            intent.putExtra("data_pts", data_pts);
                                            intent.putExtra("high", high);
                                            intent.putExtra("low", low);
                                            intent.putExtra("open", open);
                                            intent.putExtra("close", close);
                                            intent.putExtra("volume", volume);
                                            intent.putExtra("Name", companyTextView.getText());

                                            startActivity(intent);

                                            dialog.dismiss();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Intent intent = new Intent(Company.this, lastdayinfo.class);

                                        intent.putExtra("data_pts", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
                                        intent.putExtra("high", 500.00);
                                        intent.putExtra("low", 100.00);
                                        intent.putExtra("open", 120.00);
                                        intent.putExtra("close", 300.00);
                                        intent.putExtra("volume", 4000);

                                        startActivity(intent);

                                        dialog.dismiss();

                                        error.printStackTrace();
                                    }
                                });

                        Volley.newRequestQueue(getBaseContext()).add(jsonRequest);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());

                datePickerDialog.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
