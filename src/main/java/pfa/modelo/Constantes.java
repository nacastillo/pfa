package pfa.modelo;

public class Constantes {

    public static final String cadenaMenuAdmin
            = "Seleccione una opción\n"
            + "1.\tAgregar entidad\n"
            + "2.\tListar entidades\n"
            + "3.\tAgregar vigilante\n"
            + "4.\tAgregar investigador\n"
            + "5.\tAgregar administrador\n"
            + "6.\tListar usuarios con clave\n"
            + "7.\tListar usuarios con rol\n"
            + "8.\tAgregar detenido\n"
            + "9.\tListar detenidos\n"
            + "10.\tLeer fecha\n"
            + "11.\tBuscar vigilante por codigo\n"
            + "12.\tAgregar sucursal a entidad\n"
            + "13.\tListar entidades con sucursales\n"
            + "14.\tAgregar delito\n"
            + "15.\tListar vigilantes\n "
            + "\n\n"
            + "0.\tSalir";

    public static final String cadenaMenuInves
            = "Seleccione una opcion\n"
            + "1.\tConsultar sucursales asignadas a los vigilantes\n"
            + "2.\tConsultar fechas asignadas a los vigilantes\n"
            + "3.\tConsultar si los vigilantes tienen armas asignadas\n"
            + "4.\tConsultar datos personales de los vigilantes\n"
            + "5.\tConsultar bandas ingresadas\n"
            + "6.\tConsultar jueces ingresados\n"
            + "7.\tConsultar detenidos ingresados\n"
            + "8.\tConsultar delitos ingresados\n"
            + "9.\tesVigi\n"
            + "10.\tesInves\n"
            + "11.\tesAdmin\n"
            + "\n\n"
            + "0.\tSalir";

    public static final String cadenaMenuVigis = 
            "Ingrese una opcion\n" + 
            "1.\tConsultar sucursales asignadas\n" + 
            "2.\tConsultar fechas asignadas\n" + 
            "3.\tSalir del sistema\n" + 
            "4.\tes vigilante\n" + 
            "5.\tes inv\n" + 
            "6.\tes admin\n" + 
            "\n\n" + 
            "0.\tCerrar sesión\n";

    public static final String menuPrincipalAdmin =
            "MENU PRINCIPAL:\n"
            + "1.\tGestion de usuarios \n"
            + "2.\tGestion de entidades \n"
            + "3.\tGestion de sucursales \n"
            + "4.\tGestion de detenidos \n"
            + "9.\tCerrar sistema \n"
            + "\n"
            + "0.\tCerrar sesión\n";

    public static final String cadenaMenuGestionUsuarios = 
        "MENU DE GESTION DE USUARIOS:\n\n" +
        "1.\tAgregar vigilante \n" +
        "2.\tAgregar investigador \n" +
        "3.\tAgregar administrador \n" +
        "4.\tListar usuarios con rol \n" +
        "5.\tListar usuarios con clave \n" +
        "6.\tEliminar usuario \n" +
        "7.\tBuscar vigilante por código\n" +
        "8.\tListar vigilantes\n"+
        "\n" +
        "0.\tVolver al menu principal\n\n";
}
