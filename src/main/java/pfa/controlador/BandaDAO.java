package pfa.controlador;

import com.google.gson.Gson;

import org.hibernate.Session;
import org.hibernate.Transaction;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;
import pfa.modelo.dto.Banda;
import pfa.util.HibernateUtil;
import static spark.Spark.post;

public class BandaDAO {

    public static Route getAll = (req, res) -> {
        return "ruta de getAll Banda";
    };

    public static Route crear = (req, res) -> {
        Gson gson = new Gson();
        Banda banda = gson.fromJson(req.body(), Banda.class);
        /*
        System.out.println("ID: " + banda.getId());
        System.out.println("numero: " + banda.getNumero());
        System.out.println("nombre: " + banda.getNombre());
        System.out.println("cantidadMiembros: " + banda.getCantidadMiembros());
        */
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(banda);            
            session.getTransaction().commit();
            session.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Saliendo de banda/crear");
        return "";
    };

    public static Route leer = (req, res) -> {
        return "ruta de leer Banda";
    };

    public static Route actualizar = (req, res) -> {
        return "ruta de actualizar Banda";
    };

    public static Route borrar = (req, res) -> {
        return "ruta de borrar Banda";
    };

    public static RouteGroup bandasRouter = () -> {
        get("", getAll);
        post("/c", crear);
        get("/l", leer);
        get("/a", actualizar);
        get("/b", borrar);
    };
}
