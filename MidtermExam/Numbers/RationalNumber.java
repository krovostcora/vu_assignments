package Exam.Numbers;

import java.io.Serializable;

public class RationalNumber implements Serializable {
    private double a;
    private double b;

    public RationalNumber() {
        this.a = 1.0;
        this.b = 1.0;
    }
    public RationalNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    private double getA() {
        return a;
    }
    private double getB(){
        return b;
    }
    public void print(){
        System.out.println(toString());
    }
    public String toString(){
        return a + "/" + b;
    }

    public double computeSum(RationalNumber number2){
        return (a / b) + (number2.getA() / number2.getB());
    }

    public double computeProduct(RationalNumber number2){
        return (a / b) * (number2.getA() / number2.getB());
    }
}