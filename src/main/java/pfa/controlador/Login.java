package pfa.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import static com.google.gson.JsonParser.parseString;
import static pfa.controlador.UsuarioDAO.getByUsrAndPwd;
import pfa.modelo.Usuario;
import spark.Route;
import static spark.Spark.halt;
import static pfa.util.JWTUtil.generar;

public class Login {

    public static Route loginRuta = (req, res) -> {
        System.out.println("entrando al login por ruta");
        var g = new Gson();
        final JsonObject jo = parseString(req.body()).getAsJsonObject();
        final String user = getByUsrAndPwd(jo.get("usr").getAsString(), jo.get("pwd").getAsString());
        var usuarioFinal = g.fromJson(user, Usuario.class);
        System.out.println("usr es:");
        System.out.println(user);
        if (usuarioFinal.getUsr().equals("-1")) {
            halt(401, "Usuario no encontrado");
            return "";
        }
        else {
            var token = generar(parseString(user).getAsJsonObject());
            System.out.println("token es:\n" + token);
            res.type("application/json");
            res.body(token);
            return res.body();
        }        
    };    
}
