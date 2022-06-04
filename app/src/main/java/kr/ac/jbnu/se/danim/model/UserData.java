package kr.ac.jbnu.se.danim.model;

public class UserData {
    private String userName;
    private int userAge;
    private int userHeight;
    private int userWeight;

    public UserData() {
        this.userName = "";
        this.userAge = 0;
        this.userHeight = 0;
        this.userWeight = 0;
    }

    public UserData(String userName, int userAge, int userHeight, int userWeight) {
        this.userName = userName;
        this.userAge = userAge;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }
}
