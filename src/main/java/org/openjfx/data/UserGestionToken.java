package org.openjfx.data;

public class UserGestionToken {
    private static String token = "";

    public static void setToken(String token){
        UserGestionToken.token = token;
    }

    public static String getToken(){
        return token;
    }
}
