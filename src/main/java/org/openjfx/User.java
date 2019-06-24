package org.openjfx;

public class User {
    private static String token = "";

    public static void setToken(String token){
        User.token = token;
    }

    public static String getToken(){
        return token;
    }
}
