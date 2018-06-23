package model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String sex;
    private double height;
    private double weight;
    private String phone;
    private int max_cal;
    private double max_protein;
    private double max_fiber;
    private int max_sodium;
    private double max_sugar;
    private double max_carb;
    private double max_fat;
    private int age;

    public User() {

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getMax_cal() {
        return max_cal;
    }

    public void setMax_cal(Integer max_cal) {
        this.max_cal = max_cal;
    }

    public double getMax_protein() {
        return max_protein;
    }

    public void setMax_protein(Double max_protein) {
        this.max_protein = max_protein;
    }

    public double getMax_fat() {
        return max_fat;
    }

    public void setMax_fat(Double max_fat) {
        this.max_fat = max_fat;
    }

    public int getMax_sodium() {
        return max_sodium;
    }

    public void setMax_sodium(Integer max_sodium) {
        this.max_sodium = max_sodium;
    }

    public double getMax_sugar() {
        return max_sugar;
    }

    public void setMax_sugar(Double max_sugar) {
        this.max_sugar = max_sugar;
    }

    public double getMax_carb() {
        return max_carb;
    }

    public void setMax_carb(Double max_carb) {
        this.max_carb = max_carb;
    }

    public double getMax_fiber() {
        return max_fiber;
    }

    public void setMax_fiber(Double max_fiber) {
        this.max_fiber = max_fiber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
