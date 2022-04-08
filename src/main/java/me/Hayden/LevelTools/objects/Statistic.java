package me.Hayden.LevelTools.objects;

public class Statistic {

    private String name;
    private String loreLine;
    private int value;

    public Statistic(String name, String loreLine, int value) {
        this.name = name;
        this.loreLine = loreLine;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoreLine() {
        return loreLine;
    }

    public void setLoreLine(String loreLine) {
        this.loreLine = loreLine;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
