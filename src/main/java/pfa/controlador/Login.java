package pfa.controlador;


import static com.google.gson.JsonParser.parseString;
import static pfa.controlador.UsuarioDAO.getByUsrAndPwd;
import spark.Route;
import static spark.Spark.halt;
import static pfa.util.JWTUtil.generar;

public class Login {

    public static Route loginRuta = (req, res) -> {                
        var jo = parseString(req.body()).getAsJsonObject();        
        String user = getByUsrAndPwd(jo.get("usr").getAsString(), jo.get("pwd").getAsString());        
        if (parseString(user).getAsJsonObject().get("usr").getAsString().equals("-1")) {            
            halt(401, "Usuario no encontrado");
            return "";
        }
        else {
            var token = generar(parseString(user).getAsJsonObject());
            res.type("application/json");
            res.body(token);
            return res.body();
        }        
    };    
}
