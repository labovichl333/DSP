package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sample.model.Function;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    LineChart chart;

    @FXML
    LineChart chartSin;

    @FXML
    ToggleGroup nToggleGroup;

    @FXML
    ToggleGroup phaseToggleGroup;

    private Function function;



    public void initialize() {
        buildSinChart(1024);
        setChartProperties(chartSin);
        setChartProperties(chart);

    }


    public void performCalculation() {
        RadioButton rbPhase = (RadioButton) phaseToggleGroup.getSelectedToggle();
        RadioButton rbN = (RadioButton) nToggleGroup.getSelectedToggle();
        function = new Function();
        function.setPhase(getPhaseFromString(rbPhase.getText()));
        function.setN(Integer.parseInt(rbN.getText()));

        int K = function.getN() / 4;
        double inc_m = (double) function.getN() * (1. / 4.) / 8;

        chart.getData().clear();

        XYChart.Series seriesErrorRmsA = new XYChart.Series();
        XYChart.Series seriesErrorRmsB = new XYChart.Series();
        XYChart.Series seriesErrorAmplitude = new XYChart.Series();

        for (int M = K - 1; M < 5 * function.getN(); M += inc_m) {

            double rmsA = calculateRmsA(M);
            double rmsB = calculateRmsB(M);
            double amplitude = getAmplitudeFourierTransform(M);

            double errorRmsA = 0.707 - rmsA;
            double errorRmsB = 0.707 - rmsB;
            double errorAmplitude = 1 - amplitude;

            seriesErrorRmsA.getData().add(new XYChart.Data<>(String.valueOf(M), errorRmsA));
            seriesErrorRmsB.getData().add(new XYChart.Data<>(String.valueOf(M), errorRmsB));
            seriesErrorAmplitude.getData().add(new XYChart.Data<>(String.valueOf(M), errorAmplitude));

        }
        seriesErrorRmsA.setName("Отклонение среднеквадратичного значения A");
        seriesErrorRmsB.setName("Отклонение среднеквадратичного значения B");
        seriesErrorAmplitude.setName("Отклонение амплитуды");
        chart.getData().addAll(seriesErrorRmsA, seriesErrorRmsB, seriesErrorAmplitude);
    }

    private void buildSinChart(int N    ) {
        chartSin.getData().clear();
        XYChart.Series series = new XYChart.Series();
        XYChart.Series seriesPhase = new XYChart.Series();

        for (int n = 0; n < N; n++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n), Math.sin(2 * Math.PI * n / N)));
            seriesPhase.getData().add(new XYChart.Data<>(String.valueOf(n), Math.sin(2 * Math.PI * n / N + Math.PI * 3 / 4)));
        }
        series.setName("x(n) =  sin( 2 * π * n / N ) ");
        seriesPhase.setName("x(n) =  sin( 2 * π * n / N + 3π/4) ");
        chartSin.getData().addAll(series, seriesPhase);

    }

    private double getAmplitudeFourierTransform(int M) {
        double As = 0;
        double Ac = 0;

        for (int n = 0; n < M; n++) {
            As += function.getFunctionValue(n) * Math.sin(2 * Math.PI * n / M);
            Ac += function.getFunctionValue(n) * Math.cos(2 * Math.PI * n / M);
        }
        Ac *= 2.0 / M;
        As *= 2.0 / M;

        return Math.sqrt(Ac * Ac + As * As);
    }

    private double calculateRmsA(int M) {
        double sum = 0;
        for (int n = 0; n < M; n++) {
            sum += Math.pow(function.getFunctionValue(n), 2);
        }
        return Math.sqrt(sum / (M + 1));
    }

    private double calculateRmsB(int M) {
        double leftSum = 0;
        double rightSum = 0;
        double funcValue;
        for (int n = 0; n < M; n++) {
            funcValue = function.getFunctionValue(n);
            leftSum += Math.pow(funcValue, 2);
            rightSum += funcValue;
        }
        return Math.sqrt(leftSum / (M + 1) - Math.pow(rightSum / (M + 1), 2));
    }


    private double getPhaseFromString(String text) {
        switch (text) {
            case "0":
                return 0;
            case "3π/4":
                return 3 * Math.PI / 4;
        }
        return 0;
    }

    private void setChartProperties(LineChart chart) {
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAnimated(false);
    }


}
