package sql;

import android.content.Context;
import android.os.StrictMode;

import com.mysql.jdbc.Connection;


import com.mysql.jdbc.Statement;

import java.sql.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.ItemNutrient;
import model.User;
import model.UserCalorieCount;

public class DatabaseHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "finalprojectcsun";
    private static final String TABLE_USER = "userprofile";
    private static final String USER_PERDAY_COUNTER="dailyfoodcounter";
    private static final String FETCH_NUTRIENTS="nutrients_info";

    String url="jdbc:mysql://finalprojectcsun.curhdrjmgd2k.us-west-2.rds.amazonaws.com/foodnutrients";
    String userCon="atilpai";
    String passwordCon="anishatil";
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private double max_protein,max_fiber, max_carb, max_sugar, max_fat;
    private int max_sodium=2300, max_cal;
    private UserCalorieCount userCalCount;


    public DatabaseHelper(Context context) {
        //super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper() {

    }

    public Connection getConnection(){
        Connection con=null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = (Connection) DriverManager.getConnection(url, userCon, passwordCon);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }


    public void addUser(User user) {

        try {
            Connection con = getConnection();
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs;
            String sql = "INSERT INTO userprofile"
                    + "(email, password, name) " + "VALUES"
                    + "("+"'"+user.getEmail()+"',"+"'"+user.getPassword()+"', '"+user.getName()+"')";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addPerDayCounter(User user){
        Connection con = getConnection();
        Statement stmt = null;
        try {

            java.util.Date date=c.getTime();
            stmt = (Statement) con.createStatement();
            String sqluserCal = "INSERT INTO dailyfoodcounter"
                    + "(mainid,currentdate) " + "VALUES"
                    + "("+"'"+getId(user.getEmail())+"'"+",'"+df.format(date)+"'"+")";
            stmt.executeUpdate(sqluserCal);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteUser(User user) {
        try{
            Connection con = getConnection();
            String sql ="DELETE FROM userprofile WHERE id=" + user.getId();
            PreparedStatement stmt= (PreparedStatement) con.prepareStatement(sql);
            stmt.executeUpdate();
            String sql1 ="DELETE FROM dailyfoodcounter WHERE mainid=" + user.getId();
            PreparedStatement stmt1=(PreparedStatement) con.prepareStatement(sql1);
            stmt.executeUpdate();
            stmt1.executeUpdate();
            con.commit();
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public boolean checkUser(String email, String password) {

        Connection con = getConnection();
        boolean userExists = false;
        try {
            String sql = "SELECT email, password FROM userprofile where email="+
                    "'"+ email+ "'" +" AND password="+"'"+password+ "'";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs;

            rs= (ResultSet) stmt.executeQuery();
            if(getSize(rs)== 1){
                userExists =  true;
            }else{
                userExists = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }

    private int getSize(ResultSet rs) {
        int n=0;
        try {
            while(rs.next()){
                n+=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n;
    }

    public void saveToUserTable(User user) {
        Connection con = getConnection();
        try {
            String sql = "UPDATE userprofile set name=?, sex=?, age=?, height=?, weight=?," +
                    "max_cal=?, max_protein=?, max_fiber=?, max_sugar=?, max_sodium=?, max_carb=?, max_fat=?"
                    +" WHERE email='"+user.getEmail()+"'";

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            stmt.setString(1,user.getName());
            stmt.setString(2,user.getSex());
            stmt.setInt(3,user.getAge());
            stmt.setDouble(4,user.getHeight());
            stmt.setDouble(5,user.getWeight());
            stmt.setInt(6,user.getMax_cal());
            stmt.setDouble(7,user.getMax_protein());
            stmt.setDouble(8,user.getMax_fiber());
            stmt.setDouble(9,user.getMax_sugar());
            stmt.setInt(10,user.getMax_sodium());
            stmt.setDouble(11,user.getMax_carb());
            stmt.setDouble(12, user.getMax_fat());

            stmt.executeUpdate();
            con.commit();
            con.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public User calculateRequiredValues(User user, double activityLevel) {
        double bmr = 0.0;
        if (user.getSex().equals("M") || user.getSex().equals("m") || user.getSex().equals("Male") || user.getSex().equals("male"))
            bmr = (10*((user.getHeight()*30.48)-100)) + (6.25*user.getHeight()*30.48) -(5*user.getAge()) +5;
        else bmr = (10*((user.getHeight()*30.48)-100)) + (6.25*user.getHeight()*30.48) -(5*user.getAge()) -161;

        max_cal= (int) (bmr*activityLevel);

        max_protein = 0.2*max_cal*0.25;
        max_carb = max_cal*0.1375;

        if (user.getSex().equals("M") || user.getSex().equals("m") || user.getSex().equals("Male") || user.getSex().equals("male"))
            max_sugar=37.5;
        else max_sugar=25;

        max_fat=max_cal*61/2000;

        if(user.getSex().equals("M")|| user.getSex().equals("m")|| user.getSex().equals("Male") || user.getSex().equals("male")){
            if(user.getAge()<50){
                max_fiber=38;
            }
            else if(user.getAge()>=50){
                max_fiber=30;
            }
        }
        else {
            if(user.getAge()<50){
                max_fiber=25;
            }
            else if(user.getAge()>=50){
                max_fiber=21;
            }
        }
        user.setMax_protein(max_protein);
        user.setMax_fiber(max_fiber);
        user.setMax_cal(max_cal);
        user.setMax_sodium(max_sodium);
        user.setMax_sugar(max_sugar);
        user.setMax_carb(max_carb);
        user.setMax_fat(max_fat);

        return user;
    }


    public void updateUserCalorieCountTable(UserCalorieCount userCalCount, String source, Double serving) {

        Connection con= getConnection();
        java.util.Date date=c.getTime();
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        try{
            if(checkIfRecordPresentForCurrentDate(userCalCount.getId())) {
                String sql = "UPDATE dailyfoodcounter SET curr_cal="
                        + userCalCount.getTotal_cal() + ","
                        + "curr_proteins="
                        + userCalCount.getTotal_protein()
                        + ", curr_fiber="
                        + userCalCount.getTotal_fiber()
                        + ", curr_fat="
                        + userCalCount.getTotal_fat()
                        + ", curr_sodium="
                        + userCalCount.getTotal_sodium()
                        + ", curr_sugar="
                        + userCalCount.getTotal_sugar()
                        + ", curr_carb="
                        + userCalCount.getTotal_carb()
                        + " WHERE mainid = " + userCalCount.getId() + " and currentdate= '" + df.format(date)+"'";
                stmt = (PreparedStatement) con.prepareStatement(sql);
                stmt.executeUpdate();
            }else{
                String sql="INSERT INTO dailyfoodcounter"
                        + "(mainid, currentdate, curr_cal, curr_proteins, curr_fat, curr_fiber, curr_sugar, curr_sodium, curr_carb) " + "VALUES"
                        + "("+"'"+userCalCount.getId()+"'"+",'"+df.format(date)+"'"
                        +",'"+userCalCount.getTotal_cal()+"'"
                        +",'"+userCalCount.getTotal_protein()+"'"
                        +",'"+userCalCount.getTotal_fat()+"'"
                        +",'"+userCalCount.getTotal_fiber()+"'"
                        +",'"+userCalCount.getTotal_sugar()+"'"
                        +",'"+userCalCount.getTotal_sodium()+"'"
                        +",'"+userCalCount.getTotal_carb()+"'"
                        +")";
                stmt = (PreparedStatement) con.prepareStatement(sql);
                stmt.executeUpdate();
            }
            String sql3 = "INSERT INTO dailyFoodItemList" +"(mainid, source, date, serving) " + "VALUES" +
                            "("+"'"+userCalCount.getId()+"',"+"'"+source.replace("'", "")+"','"+df.format(date)+"','"+serving+"')";
            stmt1 = (PreparedStatement) con.prepareStatement(sql3);
            stmt1.execute();

            con.close();
            stmt.close();
            stmt1.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkIfRecordPresentForCurrentDate(int id) {
        try {
            java.util.Date date=c.getTime();
            Connection con = getConnection();
            String sql = "SELECT curr_cal FROM dailyfoodcounter WHERE id=" + id +" and currentdate= '"+ df.format(date)+"'";
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) stmt.executeQuery();
            if(getSize(rs)==1){
                return true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public UserCalorieCount fetchPreviousValue(int id) {

        UserCalorieCount userCalCount = new UserCalorieCount();
        Connection con = getConnection();
        java.util.Date date=c.getTime();
        try{
            String sql ="SELECT curr_cal, curr_proteins, curr_fat, curr_fiber, curr_sodium, curr_sugar, curr_carb FROM foodnutrients.dailyfoodcounter WHERE mainid=" + id
                    + " and currentdate='"+df.format(date)+"'";

            PreparedStatement stmt= (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs= (ResultSet) stmt.executeQuery();
            while(rs.next()) {
                userCalCount.setTotal_cal(rs.getInt("curr_cal"));
                userCalCount.setTotal_fiber(rs.getDouble("curr_fiber"));
                userCalCount.setTotal_protein(rs.getDouble("curr_proteins"));
                userCalCount.setTotal_fat(rs.getDouble("curr_fat"));
                userCalCount.setTotal_sodium(rs.getInt("curr_sodium"));
                userCalCount.setTotal_sugar(rs.getDouble("curr_sugar"));
                userCalCount.setTotal_carb(rs.getDouble("curr_carb"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return userCalCount;
    }

    public User fetchUserDetails(int id) {

        User user= new User();
       try {
           Connection con = getConnection();
           String sql = "SELECT max_cal, max_protein, max_fat, max_fiber,max_sugar, max_sodium, max_carb,name,age,email,sex,height,weight,phone" +
                   " FROM foodnutrients.userprofile WHERE id=" + id;
           PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
           ResultSet rs = (ResultSet) stmt.executeQuery();
           while(rs.next()) {

               user.setMax_cal(rs.getInt("max_cal"));
               user.setMax_fiber(rs.getDouble("max_fiber"));
               user.setMax_protein(rs.getDouble("max_protein"));
               user.setMax_fat(rs.getDouble("max_fat"));
               user.setMax_sodium(rs.getInt("max_sodium"));
               user.setMax_sugar(rs.getDouble("max_sugar"));
               user.setMax_carb(rs.getDouble("max_carb"));
               user.setName(rs.getString("name"));
               user.setEmail(rs.getString("email"));
               user.setAge(rs.getInt("age"));
               user.setSex(rs.getString("sex"));
               user.setHeight(rs.getDouble("height"));
               user.setWeight(rs.getDouble("weight"));
               user.setPhone(String.valueOf(rs.getInt("phone")));
               user.setId(id);

           }
       }
       catch(SQLException e){
           e.printStackTrace();
       }
        return user;
    }

    public User fetchUserDetailsForPDF(int id) {

        User user= new User();
        try {
            Connection con = getConnection();
            String sql = "SELECT max_cal, max_protein, max_fat, max_fiber, max_sodium, max_carb, max_sugar, name" +
                    " FROM foodnutrients.userprofile WHERE id=" + id;
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) stmt.executeQuery();
            while(rs.next()) {

                user.setMax_cal(rs.getInt("max_cal"));
                user.setMax_fiber(rs.getDouble("max_fiber"));
                user.setMax_protein(rs.getDouble("max_protein"));
                user.setMax_fat(rs.getDouble("max_fat"));
                user.setMax_sugar(rs.getDouble("max_sugar"));
                user.setMax_sodium(rs.getInt("max_sodium"));
                user.setMax_carb(rs.getDouble("max_carb"));
                user.setName(rs.getString("name"));
                user.setId(id);

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }


    public boolean checkUser(String email) {
        String sql = "SELECT  id FROM userprofile WHERE email= '" + email + "'";
        Boolean exist=false;
        try {
            Connection con = getConnection();
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) stmt.executeQuery();
            if(rs.next()){
                exist=true;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }


    public int getId(String email) {
        Connection con=getConnection();
        String getIdQuery="SELECT id FROM userprofile WHERE email=?";
        int id=0;
        try {
            PreparedStatement getIdSQL =(PreparedStatement) con.prepareStatement(getIdQuery);
            getIdSQL.setString(1,email);
            ResultSet rs= (ResultSet) getIdSQL.executeQuery();
            while(rs.next()){
                id=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getNameFromID(Integer id) {
        String name="";
        Connection con=getConnection();
        String getIdQuery="SELECT name FROM userprofile WHERE id=?";
        try {
            PreparedStatement getIdSQL =(PreparedStatement) con.prepareStatement(getIdQuery);
            getIdSQL.setInt(1,id);
            ResultSet rs= (ResultSet) getIdSQL.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public ArrayList<String> getMealHistory(int userID){
        ArrayList<String> items=new ArrayList<String>();
        String lastDate="";
        String currDate="";

        Connection con=getConnection();
        String sql = "SELECT source, serving, date from foodnutrients.dailyFoodItemList WHERE mainid=?";
        try{
            PreparedStatement getMeals = (PreparedStatement) con.prepareStatement(sql);
            getMeals.setString(1, Integer.toString(userID));
            ResultSet rs = (ResultSet) getMeals.executeQuery();
            int i=0;
            java.util.Date date=c.getTime();
            while(rs.next()){
                currDate = rs.getString("date");
                if (!lastDate.equals(currDate)){
                    lastDate = currDate;
                    if (currDate.equals(df.format(date))) items.add("TODAY");
                    else items.add(currDate.toUpperCase());
                }
                items.add(rs.getString("source") + "\nServing: "+ rs.getString("serving"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return items;
    }

    public ArrayList<String> getResults() {
        ArrayList<String> items=new ArrayList<String>();

        Connection con=getConnection();
        String sql="SELECT distinct_description, quantity_description from foodnutrients.nutrients_info";
        PreparedStatement pst= null;
        try {
            pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs=(ResultSet) pst.executeQuery();
            items.add("");
            while(rs.next()){
                items.add(rs.getString("distinct_description")+": "+rs.getString("quantity_description"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public ItemNutrient fetchValuesForItem(String selItem) {
        ItemNutrient itemNutrient=new ItemNutrient();
        Connection con=getConnection();
        String sql="SELECT * from foodnutrients.nutrients_info WHERE distinct_description=?";
        PreparedStatement getItemDetails = null;
        try {
            getItemDetails = (PreparedStatement) con.prepareStatement(sql);
            getItemDetails.setString(1,selItem);
            ResultSet rs=(ResultSet) getItemDetails.executeQuery();
            while (rs.next()){
                itemNutrient.setCalories( rs.getInt("calories_kcal"));
                itemNutrient.setFiber(rs.getDouble("fiber"));
                itemNutrient.setFat(rs.getDouble("fat"));
                itemNutrient.setProteins(rs.getDouble("proteins"));
                itemNutrient.setCarb(rs.getDouble("carbohydrates"));
                itemNutrient.setSugar(rs.getDouble("sugar"));
                itemNutrient.setSodium(rs.getInt("sodium_mg"));
                itemNutrient.setItem(selItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemNutrient;
    }

    public ArrayList<UserCalorieCount> fetchCurrValuesForReport(int id, String fromDate, String toDate) {
        ArrayList<UserCalorieCount> userCalCountList = new ArrayList<>();
        userCalCount=new UserCalorieCount();
        Connection con = getConnection();
        try{
            String sql ="SELECT curr_cal, curr_proteins, curr_fat, curr_fiber, curr_date FROM " +
                    "all_nutrients.user_perday_counter WHERE id=" + id +" and curr_date BETWEEN"+"'"+fromDate+"' AND '"+toDate+"'" ;

            PreparedStatement stmt= (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs= (ResultSet) stmt.executeQuery();
            while(rs.next()) {

                userCalCount.setTotal_cal(rs.getInt("curr_cal"));
                userCalCount.setTotal_fiber(rs.getDouble("curr_fiber"));
                userCalCount.setTotal_protein(rs.getDouble("curr_proteins"));
                userCalCount.setTotal_fat(rs.getDouble("curr_fat"));
                userCalCount.setTotal_sugar(rs.getDouble("curr_sugar"));
                userCalCount.setTotal_sodium(rs.getInt("curr_sodium"));
                userCalCount.setTotal_carb(rs.getDouble("curr_carb"));
                userCalCount.setDate(rs.getDate("curr_date"));
                userCalCountList.add(userCalCount);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return userCalCountList;

    }

    public UserCalorieCount fetchMaxNutrients(int id){

        userCalCount=new UserCalorieCount();
        Connection con = getConnection();
        try{
            String sql ="SELECT max_cal, max_protein, max_fat, max_fiber, max_sodium, max_sugar, max_carb FROM " +
                    "foodnutrients.userprofile WHERE id=" + id ;

            PreparedStatement stmt= (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs= (ResultSet) stmt.executeQuery();
            while(rs.next()) {

                userCalCount.setTotal_cal(rs.getInt("max_cal"));
                userCalCount.setTotal_fiber(rs.getDouble("max_fiber"));
                userCalCount.setTotal_protein(rs.getDouble("max_protein"));
                userCalCount.setTotal_fat(rs.getDouble("max_fat"));
                userCalCount.setTotal_sugar(rs.getDouble("max_sugar"));
                userCalCount.setTotal_sodium(rs.getInt("max_sodium"));
                userCalCount.setTotal_carb(rs.getDouble("max_carb"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return userCalCount;
    }
}
