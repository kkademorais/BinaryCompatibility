package app;

import lib.v1.math.Calculator;

public class Main {
    public static void main(String[] args) {

        Calculator calc = new Calculator();

        System.out.println("Calculando 22 + 13 = " + calc.sum(22, 13));


    }
}
