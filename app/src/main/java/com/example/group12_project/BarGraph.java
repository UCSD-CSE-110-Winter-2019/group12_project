package com.example.group12_project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class BarGraph {

    private BarChart barChart;
    private ArrayList<BarEntry> Entries;
    private String First= "2016/05/05", Last= "2016/05/13";
    private int[] a ={2000,1500,4545,3000,3450,4470,2473};
    private int goals;
    private static final String TAG = "[BarGraph]";

    BarGraph(Activity activity) {
        this.barChart = activity.findViewById(R.id.bargraph);
        SharedPreferences storedGoal
                = activity.getSharedPreferences("storedGoal", Context.MODE_PRIVATE);

        // check if goal is null
        String goalValue = storedGoal.getString("goal","");
        if (goalValue != null) {
            goals = Integer.parseInt(goalValue);
            Log.i(TAG, String.format("Goals got from shared preferences is %s", goals));
        } else {
            Log.i(TAG, "Can't access goals");
            activity.finish();
        }
        createRandomBarGraph(First, Last, goals, a);
    }

    private void createRandomBarGraph(String Date1, String Date2, int goal, int[] steps){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();

            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            ArrayList<String> dates = new ArrayList<>();
            dates = getList(mDate1,mDate2);

            Entries = new ArrayList<>();

            // needs steps for each day

            //float max = 0f;
            //float min = 0f;
            //float value = 0f;
            //random = new Random();
            int day = 1;

            for(int j : steps) {
                //max = 3000f;
                //min = 3000f;
                //value = random.nextFloat()*max + min;
                BarEntry Bar = new BarEntry(day, j);
                Entries.add(Bar);
                day++;
            }

        }catch(ParseException e){
            e.printStackTrace();
        }

        // needs value of goal
        LimitLine upper_limit = new LimitLine(goal, "Your Goal!");
        upper_limit.setLineWidth(5f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(13f);
        upper_limit.setTextColor(android.graphics.Color.parseColor("#D81B60"));
        YAxis leftAxis = this.barChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);

        BarDataSet barDataSet = new BarDataSet(Entries,"Steps");

        barDataSet.setColor(android.graphics.Color.parseColor("#008577"));
        BarData barData = new BarData(barDataSet);
        this.barChart.setData(barData);
        this.barChart.setScaleEnabled(true);
        this.barChart.setDescription("This shows the number of your steps from " + First + " to " + Last );
        this.barChart.setDescriptionTextSize(9f);
    }


    private ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }

    private String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd", Locale.US).parse(curDate);
            curDate =  new SimpleDateFormat("yyy/MM/dd", Locale.US).format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }

}