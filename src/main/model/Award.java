package model;

public class Award {
    private String awardName;
    private int year;
    private double awardMoney;

    // EFFECTS: creates a award with awardName, date and awardMoney
    public Award(String awardName, int year, double awardMoney) {
        this.awardName = awardName;
        this.year = year;
        this.awardMoney = awardMoney;
    }

    //getters
    public String getAwardName() {
        return awardName;
    }

    public int getYear() {
        return year;
    }

    public double getAwardMoney() {
        return awardMoney;
    }

    //setters
    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public void setDate(int year) {
        this.year = year;
    }

    public void setPrize(double awardMoney) {
        this.awardMoney = awardMoney;
    }

}


