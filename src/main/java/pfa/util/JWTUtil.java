package pfa.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;
import java.util.regex.Pattern;
import static pfa.util.HibernateUtil.getSessionFactory;
import pfa.modelo.Sucursal;
import pfa.modelo.Entidad;

public class JWTUtil {

    private static final String SECRET = "final-programacion-dos-para-el-inspt";
    private static final long EXPIRATION_TIME = 86_400_000; // 1 día
    //private static final long EXPIRATION_TIME = 864_000_000; // 10 días en milisegundos

    public static String generar(JsonObject jo) {  
        var usr = jo.get("usr").getAsString();
        var rol = jo.get("rol").getAsString();
        if (jo.has("contratos")) {
            var contratos = jo.get("contratos").getAsJsonArray();
            if (contratos.size() > 0) {
                var session = getSessionFactory().openSession();                
                for (JsonElement contratoElem : contratos) {
                    var contrato = contratoElem.getAsJsonObject();
                    if (contrato.get("sucursal").getAsString() != null) {
                        session.beginTransaction();
                        var suc = session.get(Sucursal.class, contrato.get("sucursal").getAsInt());
                        if (suc.getEntidad() != null) {
                            var ent = session.get(Entidad.class, suc.getEntidad());
                            contrato.addProperty("nombreSucursal", String.format("%s (%s)",suc.getNombre(), ent.getNombre()));
                        }
                        else {
                            contrato.addProperty("nombreSucursal", suc.getNombre());
                        }
                        session.getTransaction().commit();
                    }
                }
                session.close();
            }            
            var gson = new Gson();
            return "Bearer " + JWT.create()
                .withSubject(usr)
                .withClaim("rol", rol)
                .withClaim("contratos", gson.toJson(contratos))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        }
        else {
            return "Bearer " + JWT.create()
                .withSubject(usr)
                .withClaim("rol", rol)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        }
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
        var regex = "^\\s*Bearer\\s+(\\S+)";
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
