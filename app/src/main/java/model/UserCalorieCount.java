package model;

import java.util.Date;

public class UserCalorieCount {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotal_cal() {
        return total_cal;
    }

    public void setTotal_cal(int total_cal) {
        this.total_cal = total_cal;
    }

    public double getTotal_protein() {
        return total_protein;
    }

    public void setTotal_protein(double total_protein) {
        this.total_protein = total_protein;
    }

    public double getTotal_fat() {
        return total_fat;
    }

    public void setTotal_fat(double total_fat) {
        this.total_fat = total_fat;
    }

    public int getTotal_sodium() {
        return total_sodium;
    }

    public void setTotal_sodium(int total_sodium) {
        this.total_sodium = total_sodium;
    }
    public double getTotal_sugar() {
        return total_sugar;
    }

    public void setTotal_sugar(double total_sugar) {
        this.total_sugar = total_sugar;
    }
    public double getTotal_carb() {
        return total_carb;
    }

    public void setTotal_carb(double total_carb) {
        this.total_carb = total_carb;
    }

    public double getTotal_fiber() {
        return total_fiber;
    }

    public void setTotal_fiber(double total_fiber) {
        this.total_fiber = total_fiber;
    }

    private Date date;
    private int total_cal;
    private double total_protein;
    private double total_fat;
    private double total_fiber;
    private int total_sodium;
    private double total_sugar, total_carb;

}
