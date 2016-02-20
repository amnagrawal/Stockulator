package com.aman.stockulator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

public class lastdayinfo extends AppCompatActivity {

    TextView companyTextView,openTextView,closeTextView,highestTextView,lowestTextView,volumeTextView;
    private XYPlot mySimpleXYPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lastdayinfo);

        companyTextView = (TextView)findViewById(R.id.companyTextView);
        openTextView = (TextView)findViewById(R.id.openTextView);
        closeTextView = (TextView)findViewById(R.id.closeTextView);
        highestTextView = (TextView)findViewById(R.id.highestTextView);
        lowestTextView = (TextView)findViewById(R.id.lowestTextView);
        volumeTextView = (TextView)findViewById(R.id.volumeTextView);

        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        int[] dps =getIntent().getExtras().getIntArray("data_pts");
        double high = getIntent().getExtras().getDouble("high");
        double low = getIntent().getExtras().getDouble("low");
        double open = getIntent().getExtras().getDouble("open");
        double close = getIntent().getExtras().getDouble("close");
        int volume = getIntent().getExtras().getInt("volume");

        openTextView.setText(String.valueOf(open));
        closeTextView.setText(String.valueOf(close));
        highestTextView.setText(String.valueOf(high));
        lowestTextView.setText(String.valueOf(low));
        volumeTextView.setText(String.valueOf(volume));
        companyTextView.setText(getIntent().getStringExtra("Name"));


        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = new Number[dps.length];
        for(int i=0; i<dps.length; i++)
            series1Numbers[i] = dps[i];
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "Series1");                             // Set the display title of the series

        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Create a formatter to use for drawing a series using LineAndPointRenderer:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0, 200, 0),                   // line color
                Color.rgb(0, 100, 0),                   // point color
                null,                                   // fill color (none)
                null);                           // text color

        // add a new series' to the xyplot:
        mySimpleXYPlot.addSeries(series1, series1Format);

        // same as above:
        mySimpleXYPlot.addSeries(series2,
                new LineAndPointFormatter(
                        Color.rgb(0, 0, 200),
                        Color.rgb(0, 0, 100),
                        null,
                        null));

        // reduce the number of range labels
        mySimpleXYPlot.setTicksPerRangeLabel(3);


    }


}
