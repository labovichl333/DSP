package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

import java.util.Arrays;

public class Controller {
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;

    @FXML
    private Slider slider1;

    @FXML
    private Slider slider2;

    @FXML
    private Slider slider3;

    @FXML
    private Slider slider4;

    @FXML
    private Slider slider5;

    @FXML
    private LineChart chart2a;

    @FXML
    private LineChart chart2b;

    @FXML
    private LineChart chart2c;

    @FXML
    private LineChart chart3;

    @FXML
    private LineChart chart4;

    @FXML
    private ToggleGroup phase1;

    @FXML
    private ToggleGroup frequency1;

    @FXML
    private ToggleGroup amplitude1;

    @FXML
    private ToggleGroup n1;

    @FXML
    private ToggleGroup n2;

    @FXML
    private ToggleGroup n3;

    @FXML
    private ToggleGroup n4;

    @FXML
    private ToggleGroup n5;


    private double sliderVal1=Math.PI / 4;
    private double sliderVal2=3*Math.PI / 4;
    private double sliderVal3=2*Math.PI / 3;
    private double sliderVal4=Math.PI / 2;
    private double sliderVal5=Math.PI / 3;



    public void initialize() {

        slider1.valueProperty().addListener((observable, oldValue, newValue) -> {
                    label1.textProperty().setValue(String.valueOf(newValue.doubleValue()));
                    clear();
                    task3Start();
        });
        slider2.valueProperty().addListener((observable, oldValue, newValue) -> {
            label2.textProperty().setValue(String.valueOf(newValue.doubleValue()));
            clear();
            task3Start();
        });
        slider3.valueProperty().addListener((observable, oldValue, newValue) -> {
            label3.textProperty().setValue(String.valueOf(newValue.doubleValue()));
            clear();
            task3Start();
        });
        slider4.valueProperty().addListener((observable, oldValue, newValue) -> {
            label4.textProperty().setValue(String.valueOf(newValue.doubleValue()));
            clear();
            task3Start();
        });
        slider5.valueProperty().addListener((observable, oldValue, newValue) -> {
            label5.textProperty().setValue(String.valueOf(newValue.doubleValue()));
            clear();
            task3Start();
        });

        setChartProperties(chart2a);
        setChartProperties(chart2b);
        setChartProperties(chart2c);
        setChartProperties(chart3);
        setChartProperties(chart4);


    }


    public void task2aStart() {
        RadioButton rbPhase = (RadioButton) phase1.getSelectedToggle();
        RadioButton rbN1 = (RadioButton) n1.getSelectedToggle();
        double phase = getPhaseFromString(rbPhase.getText());
        double amplitude = 8;
        double frequency = 4;
        double N = Double.parseDouble(rbN1.getText());
        performCalculation(chart2a, phase, amplitude, frequency, N);
    }

    public void task2bStart() {
        RadioButton rbFrequency = (RadioButton) frequency1.getSelectedToggle();
        RadioButton rbN2 = (RadioButton) n2.getSelectedToggle();

        double phase = 0;
        double amplitude = 4;
        double frequency = Double.parseDouble(rbFrequency.getText());
        double N = Double.parseDouble(rbN2.getText());
        performCalculation(chart2b, phase, amplitude,frequency, N);
    }

    public void task2cStart() {
        RadioButton rbAmplitude = (RadioButton) amplitude1.getSelectedToggle();
        RadioButton rbN3 = (RadioButton) n3.getSelectedToggle();

        double phase = 0;
        double amplitude = Double.parseDouble(rbAmplitude.getText());
        double frequency = 2;
        double N = Double.parseDouble(rbN3.getText());
        performCalculation(chart2c, phase, amplitude, frequency, N);
    }

    public void task3Start() {
        RadioButton rbN4 = (RadioButton) n4.getSelectedToggle();
        double[] phase = {slider1.getValue(), slider2.getValue(), slider3.getValue(), slider4.getValue(), slider5.getValue()};
        label1.setText(String.valueOf(phase[0]));
        label2.setText(String.valueOf(phase[1]));
        label3.setText(String.valueOf(phase[2]));
        label4.setText(String.valueOf(phase[3]));
        label5.setText(String.valueOf(phase[4]));
        double[] amplitude = {3, 3, 3, 3, 3};
        double[] frequency = {1, 2, 3, 4, 5};
        double N = Double.parseDouble(rbN4.getText());
        XYChart.Series series = new XYChart.Series();

        for (int n = 0; n <= N; n++) {
            double funcValue = 0;

            for (int k = 0; k < 5; k++) {
                funcValue += getFunctionResult(phase[k], amplitude[k], frequency[k], N, n);
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(n), funcValue));
        }
        chart3.getData().add(series);
    }

    public void task4Start() {
        RadioButton rbN5 = (RadioButton) n5.getSelectedToggle();
        double[] phase = {Math.PI / 6, Math.PI / 2, Math.PI / 3, Math.PI / 9, 0};
        double[] amplitude = {3, 3, 3, 3, 3};
        double[] frequency = {1, 2, 3, 4, 5};
        double N = Double.parseDouble(rbN5.getText());
        XYChart.Series series = new XYChart.Series();
        for (int n = 0; n <=  N; n++) {
            double[] multipliers = {0.20 / (N / frequency[0]), 0.20 / (N / frequency[1]), 0.20 / (N / frequency[2]), 0.20 / (N / frequency[3]), 0.20 / (N / frequency[4])};

            double funcValue = 0;
            for (int k = 0; k < 5; k++) {
                funcValue += getFunctionResult(phase[k], amplitude[k], frequency[k], N, n);
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(n), funcValue));
            incrementParams(phase, multipliers);
            incrementParams(amplitude, multipliers);
            incrementParams(frequency, multipliers);

        }
        chart4.getData().add(series);
    }



    private void incrementParams(double[] array, double[] multipliers) {
        for (int i = 0; i < array.length; i++) {
            array[i] += array[i] * multipliers[i];
        }
    }


    public void clear(){
        chart2a.getData().clear();
        chart2b.getData().clear();
        chart2c.getData().clear();
        chart3.getData().clear();
        chart4.getData().clear();
    }
    private void performCalculation(LineChart chart, double phase, double amplitude, double frequency, double N) {
        XYChart.Series series = new XYChart.Series();

        for (int n = 0; n <= N; n++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n), getFunctionResult(phase, amplitude, frequency, N, n)));

        }
        chart.getData().add(series);
    }


    private double getFunctionResult(double phase, double amplitude, double frequency, double N, double n) {
        return amplitude * Math.sin(2 * Math.PI * frequency * n / N + phase);
    }

    private void setChartProperties(LineChart chart) {
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setAnimated(false);
    }

    private double getPhaseFromString(String text) {
        switch (text) {
            case "π/6":
                return Math.PI / 6;
            case "π/3":
                return Math.PI / 3;
            case "2π/3":
                return 2*Math.PI / 3;
            case "π/4":
                return Math.PI / 4;
            case "0":
                return 0;
        }
        return 0;
    }
}
