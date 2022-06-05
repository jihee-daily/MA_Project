package kr.ac.jbnu.se.danim.model;

import android.provider.ContactsContract;

import java.util.HashMap;

public class GlobalStorage {
    private static GlobalStorage globalStorage = null;

    HashMap<String, UserData> userDataHashMap;
    HashMap<String, MapDirectionData> directionDataHashMap;

    private GlobalStorage() {
        this.userDataHashMap = new HashMap<String, UserData>();
        this.directionDataHashMap = new HashMap<String, MapDirectionData>();
    }

    public static GlobalStorage getInstance() {
        if (globalStorage == null) {globalStorage = new GlobalStorage();}
        return globalStorage;
    }

    public HashMap<String, UserData> getUserDataHashMap() {
        return userDataHashMap;
    }
    public void setUserDataHashMap(HashMap<String, UserData> userDataHashMap) {this.userDataHashMap = userDataHashMap;}

    public HashMap<String, MapDirectionData> getDirectionDataHashMap() {return directionDataHashMap;}
    public void setDirectionDataHashMap(HashMap<String, MapDirectionData> directionDataHashMap) {this.directionDataHashMap = directionDataHashMap;}
}