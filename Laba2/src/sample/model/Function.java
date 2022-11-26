package sample.model;

public class Function {
    private double phase;
    private int n;

    public void setPhase(double phase) {
        this.phase = phase;
    }


    public void setN(int n) {
        this.n = n;
    }

    public double getPhase() {
        return phase;
    }

    public int getN() {
        return n;
    }

    public double getFunctionValue(int n) {
        return Math.sin(2 * Math.PI * n / this.n + phase);
    }

}
