package pfa.util;

import com.google.gson.JsonObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;
import java.util.regex.Pattern;

public class JWTUtil {

    private static final String SECRET = "final-programacion-dos-para-el-inspt";
    private static final long EXPIRATION_TIME = 86_400_000; // 1 día
    //private static final long EXPIRATION_TIME = 864_000_000; // 10 días en milisegundos

    public static String generar(JsonObject jo) {  
        final String usr = jo.get("usr").getAsString();
        final String rol = jo.get("rol").getAsString();
        return "Bearer " + JWT.create()
                .withSubject(usr)
                .withClaim("rol", rol)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    public static boolean verificar(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token);
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }

    public static String limpiar(String auth) {        
        var token = "";
        final var regex = "^\\s*Bearer\\s+(\\S+)";
        var matcher = Pattern.compile(regex).matcher(auth);
        if (matcher.find()) {
            token = matcher.group(1);            
        }     
        return token;
    }    

    public static String extraerRol (String token) {
        try {            
            return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                        .build()
                        .verify(token)
                        .getClaim("rol").asString();
        }
        catch (JWTVerificationException e) {            
            e.printStackTrace();
            return "";
        }        
    }
}
