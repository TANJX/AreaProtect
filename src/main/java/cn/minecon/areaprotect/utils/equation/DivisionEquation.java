package cn.minecon.areaprotect.utils.equation;

import java.util.Map;

public class DivisionEquation extends Equation {
    private final Equation left;
    private final Equation right;

    public DivisionEquation(Equation left, Equation right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double calculate(Map<String, Double> variables) {
        return left.calculate(variables) / right.calculate(variables);
    }

    public String toString() {
        return "(" + left.toString() + " / " + right.toString() + ")";
    }
}
