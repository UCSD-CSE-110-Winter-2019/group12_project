package com.example.group12_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;



public class StepChart extends AppCompatActivity {

    BarChart barChart;
    ArrayList<String> dates;
    Random random;
    ArrayList<BarEntry> Entries;
    Integer goal;
    String First= "2016/05/05", Last= "2016/05/13";
    int[] a ={5530,5000,4500,4545,6000,3450,5470,6473,4954};
    int goals = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_chart);

        barChart = (BarChart) findViewById(R.id.bargraph);

        createRandomBarGraph(First,Last,goals,a);
    }

    public void createRandomBarGraph(String Date1, String Date2, int goal, int[] steps){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();

            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1,mDate2);

            Entries = new ArrayList<>();

            // needs steps for each day

            //float max = 0f;
            //float min = 0f;
            //float value = 0f;
            //random = new Random();
            for(int j = 0; j< steps.length; j++){
                //max = 3000f;
                //min = 3000f;
                //value = random.nextFloat()*max + min;
                BarEntry Bar = new BarEntry(steps[j], j);

                Entries.add(Bar);
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
        upper_limit.setTextColor(android.graphics.Color.parseColor("#FF0000"));
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);

        BarDataSet barDataSet = new BarDataSet(Entries,"Steps");

        //barDataSet.setColor(android.graphics.Color.parseColor("#7FFF00"));
        BarData barData = new BarData(dates,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("This shows the number of your steps from " + First+ " to " + Last );
        barChart.setDescriptionTextSize(9f);
    }


    public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }

    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate =  new SimpleDateFormat("yyy/MM/dd").format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }

}