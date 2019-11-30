package org.openjfx;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

public class PrimaryController {

    @FXML
    ScatterChart<Number, Number> scatterChart;
    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    public void initialize(){
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        xAxis.setLowerBound(-500);
        xAxis.setUpperBound(500);

        yAxis.setLowerBound(-500);
        yAxis.setUpperBound(500);
        generateTickets();
    }
    public int digitSum(int number) {
        return (number == 0)
                ? 0
                : (number % 10) + digitSum(number / 10);
    }

    public void generateTickets() {
        Double start = 1001.0;
        Double left = 0.0;
        Double right = 0.0;
        XYChart.Series chartSeries = new XYChart.Series();

        while (start < 999999){
            left = Math.floor(start / 1000);
            right = start % 1000;
//            if (left.equals(right)){
            if(digitSum(left.intValue()) == digitSum(right.intValue())){
                addPoint(chartSeries, start - 1000);
            }
            start++;
        }
        scatterChart.getData().add(chartSeries);
    }

    public void addPoint(XYChart.Series series, Double n){
        Double k = Math.ceil((Math.sqrt(n) - 1) / 2);
        Double t = 2 * k + 1;
        Double m = Math.pow(t, 2);
        t--;
        Double x = 0.0;
        Double y = 0.0;
        Boolean changed = false;
        if (n >= m - t){
            x = k - (m - n);
            y = -k;
            changed = true;
        }
        m -= t;

        if (n >= m - t && !changed) {
            x = -k;
            y = -k + (m - n);
            changed = true;
        }

        m -= t;

        if (n >= m - t && !changed) {
            x = -k + (m - n);
            y = k;
            changed = true;
        }

        if (!changed){
            x = k;
            y = k - (m - n - t);
        }

        System.out.println(n+1000+" =>  " + x + " : " + y);
        XYChart.Data chartData = new XYChart.Data(x, y);
        chartData.setNode(new ShowCoordinatesNode(x, y));
        series.getData().add(chartData);
    }
}
