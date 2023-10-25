package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class Utils {

    /**
     * SUJETO A CAMBIOS
     */
    public static final int TIPO_SINIESTRO_CANTIDAD = 6;

    public static String obtenerTipoSiniestro(int codigo) {
        String s = null;
        switch (codigo) {
            case 1:
                s = "Incendio en viviendas e industrias";
                break;
            case 2:
                s = "Salvamento en derrumbes";
                break;
            case 3:
                s = "Rescates en ámbito montaña";
                break;
            case 4:
                s = "Rescates de personas en accidentes de tráfico";
                break;
            case 5:
                s = "Socorrer inundaciones";
                break;
            case 6:
                s = "Operativos de prevención";
                break;
            default:
                s = "";
                System.out.println("[Utils.obtenerTipoSiniestro Error] Código no válido");
                break;
        }
        return s;
    }
}
