package ru.program.coffeeMaker.exception;

public class IncorrectValueIngredients {
    private String info;

    public IncorrectValueIngredients() {

    }
    public IncorrectValueIngredients(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
