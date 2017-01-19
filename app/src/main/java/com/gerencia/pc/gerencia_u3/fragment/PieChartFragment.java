package com.gerencia.pc.gerencia_u3.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gerencia.pc.gerencia_u3.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PieChartFragment extends Fragment {

    private PieChart mChart;
    View view;

    float[] yData;
    String[] xData;
    String[] color;
    int total_inci;

    TextView input_incide;

    public PieChartFragment(String[] x,float[] y,String[] colors,int total) {
        this.xData=x;
        this.yData=y;
        this.color=colors;
        this.total_inci=total;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_pie_chart, container, false);
        input_incide=(TextView)view.findViewById(R.id.txt_total_inci);
        input_incide.setText(""+total_inci);

        mChart=(PieChart) view.findViewById(R.id.piechar);

        mChart.setUsePercentValues(true);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        Description dec =new Description();
        dec.setText("PieChart de incidencias");
        dec.setTextColor(Color.WHITE);
        mChart.setDescription(dec);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getContext(),"Estado de incidencias: "+ xData[(int)h.getX()]+"\n Porcentaje: "+(int)e.getY()+"%",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        addData();

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setTextColor(Color.WHITE);
        mChart.animateXY(1400, 1400);
        mChart.setExtraBottomOffset(16);


        return view ;
    }

    public  void addData(){
        ArrayList<PieEntry> yVals=new ArrayList<PieEntry>();
        for(int i=0; i<yData.length; i++){
            yVals.add(new PieEntry(yData[i],xData[i]));
        }

        PieDataSet dataSet= new PieDataSet(yVals,"");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors =new ArrayList<Integer>();

        for(int i=0; i<color.length; i++){
            colors.add(Color.parseColor(color[i]));
        }

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data=new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(25);


        mChart.setData(data);

        mChart.highlightValues(null);

        mChart.invalidate();


    }

}
