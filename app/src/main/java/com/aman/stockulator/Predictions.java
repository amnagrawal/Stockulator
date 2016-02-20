package com.aman.stockulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Predictions extends AppCompatActivity {

    EditText openingPriceEditText;
    Button PredictionButton;
    TextView textView5;
    String companyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictions);
        openingPriceEditText = (EditText)findViewById(R.id.openingPriceEditText);
        PredictionButton = (Button)findViewById(R.id.Predictionbutton);
        textView5 = (TextView)findViewById(R.id.textView5);
        companyName = getIntent().getStringExtra("Name");

        /*if(companyName=="Wipro")
            companyName="wipro";
        else if(companyName=="Aplab Bo")
            companyName="aplab-bo";
        else if(companyName=="Andhra Bank")
            companyName="andhra-bank";
        else if(companyName=="India Cements")
            companyName="india-cements";
*/
        PredictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView5.setVisibility((textView5.getVisibility() == View.VISIBLE)
                        ? View.GONE : View.VISIBLE);

                sendRequest(String.valueOf(openingPriceEditText.getText()));

            }

            public void sendRequest(String str) {

                String url = "http://10.60.48.37:5000/" + companyName + "/" + str;
                Log.i("url", url);

                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                double expectation;
                                double error;

                                try {
                                    Log.i("response: ", response.toString());
                                    expectation = response.getDouble("closing_price");
                                    //error = response.getDouble("percentage_error");
                                    textView5.setText(String.valueOf(expectation));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                Volley.newRequestQueue(getBaseContext()).add(jsonRequest);
            }
        });
    }
}
