package lab1proyectofinal.accesoADatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class Utils {

    /**
     * SUJETO A CAMBIOS
     */
    public static final int TIPO_SINIESTRO_CANTIDAD = 6;
    private static final Pattern p = Pattern.compile("^\\d+$");

    public static String[] obtenerEspecialidades() {
        String[] s = new String[]{
            "Incendio en viviendas e industrias",
            "Salvamento en derrumbes",
            "Rescates en ámbito montaña",
            "Rescates de personas en accidentes de tráfico",
            "Socorrer inundaciones",
            "Operativos de prevención"
        };
        return s;
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("^\\d+$");
    }

    public static Cuartel obtenerDeResultSetCuartel(ResultSet rs) throws SQLException {
        Cuartel cuartel = new Cuartel();
        cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
        cuartel.setDireccion(rs.getString("direccion"));
        cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
        cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
        cuartel.setTelefono(rs.getString("telefono"));
        cuartel.setCorreo(rs.getString("correo"));
        cuartel.setEstado(rs.getBoolean("estado"));
        return cuartel;
    }

    public static Brigada obtenerDeResultSetBrigada(ResultSet rs) throws SQLException {
        Cuartel cuartel = new Cuartel();
        cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
        cuartel.setDireccion(rs.getString("direccion"));
        cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
        cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
        cuartel.setTelefono(rs.getString("telefono"));
        cuartel.setCorreo(rs.getString("correo"));
        cuartel.setEstado(rs.getBoolean("cuartel.estado"));
        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("nombreBrigada"));
        brigada.setEspecialidad(rs.getString("especialidad"));
        brigada.setDisponible(rs.getBoolean("disponible"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        return brigada;
    }

    public static Bombero obtenerDeResultSetBombero(ResultSet rs) throws SQLException {
        Cuartel cuartel = new Cuartel();
        cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
        cuartel.setDireccion(rs.getString("direccion"));
        cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
        cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
        cuartel.setTelefono(rs.getString("telefono"));
        cuartel.setCorreo(rs.getString("correo"));
        cuartel.setEstado(rs.getBoolean("cuartel.estado"));
        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("nombreBrigada"));
        brigada.setEspecialidad(rs.getString("especialidad"));
        brigada.setDisponible(rs.getBoolean("disponible"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        Bombero bombero = new Bombero();
        bombero.setIdBombero(rs.getInt("idBombero"));
        bombero.setDni(rs.getInt("dni"));
        bombero.setNombreApellido(rs.getString("nombreApellido"));
        bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
        bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
        bombero.setTelefono(rs.getLong("celular"));
        bombero.setBrigada(brigada);
        bombero.setEstado(rs.getBoolean("bombero.estado"));
        return bombero;
    }

    public static Siniestro obtenerDeResultSetSiniestro(ResultSet rs) throws SQLException {
        Siniestro siniestro = new Siniestro();
        siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
        siniestro.setTipo(rs.getString("tipo"));
        siniestro.setFechaSiniestro(rs.getTimestamp("fechaSiniestro").toLocalDateTime());
        siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
        siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
        siniestro.setDetalles(rs.getString("detalles"));
        siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
        if (rs.getInt("puntuacion") != Siniestro.PUNTUACION_NIL) {
            siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
        } else {
            siniestro.setFechaResolucion(null);
        }
        siniestro.setPuntuacion(rs.getInt("puntuacion"));
        return siniestro;
    }

}
