package kr.ac.jbnu.se.danim.model;

import java.util.HashMap;

public class GlobalStorage {
    private static GlobalStorage globalStorage = null;

    HashMap<String, UserData> userDataHashMap;

    private GlobalStorage() {
        this.userDataHashMap = new HashMap<String, UserData>();
    }

    public static GlobalStorage getInstance() {
        if (globalStorage == null) {
            globalStorage = new GlobalStorage();
        }

        return globalStorage;
    }

    public HashMap<String, UserData> getUserDataHashMap() {
        return userDataHashMap;
    }

    public void setUserDataHashMap(HashMap<String, UserData> userDataHashMap) {
        this.userDataHashMap = userDataHashMap;
    }
}
