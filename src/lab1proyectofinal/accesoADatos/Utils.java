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
    public static final int NIL = -1;

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
        cuartel.setCodigoCuartel(rs.getInt("cuartel.codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("cuartel.nombreCuartel"));
        cuartel.setDireccion(rs.getString("cuartel.direccion"));
        cuartel.setCoordenadaX(rs.getInt("cuartel.coordenadaX"));
        cuartel.setCoordenadaY(rs.getInt("cuartel.coordenadaY"));
        cuartel.setTelefono(rs.getString("cuartel.telefono"));
        cuartel.setCorreo(rs.getString("cuartel.correo"));
        cuartel.setEstado(rs.getBoolean("cuartel.estado"));
        return cuartel;
    }

    public static Brigada obtenerDeResultSetBrigada(ResultSet rs) throws SQLException {
        Cuartel cuartel = obtenerDeResultSetCuartel(rs);
        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("brigada.codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("brigada.nombreBrigada"));
        brigada.setEspecialidad(rs.getString("brigada.especialidad"));
        brigada.setDisponible(rs.getBoolean("brigada.disponible"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        return brigada;
    }

    public static Bombero obtenerDeResultSetBombero(ResultSet rs) throws SQLException {
        Brigada brigada = obtenerDeResultSetBrigada(rs);
        Bombero bombero = new Bombero();
        bombero.setIdBombero(rs.getInt("bombero.idBombero"));
        bombero.setDni(rs.getInt("bombero.dni"));
        bombero.setNombreApellido(rs.getString("bombero.nombreCompleto"));
        bombero.setGrupoSanguineo(rs.getString("bombero.grupoSanguineo"));
        bombero.setFechaNacimiento(rs.getDate("bombero.fechaNacimiento").toLocalDate());
        bombero.setCelular(rs.getString("bombero.celular"));
        bombero.setBrigada(brigada);
        bombero.setEstado(rs.getBoolean("bombero.estado"));
        return bombero;
    }

    // TODO: Testear esto
    public static Siniestro obtenerDeResultSetSiniestro(ResultSet rs) throws SQLException {
        Brigada brigada = null;
        if (rs.getInt("codigoBrigada") != NIL) {
            brigada = obtenerDeResultSetBrigada(rs);
        }
        Siniestro siniestro = new Siniestro();
        siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
        siniestro.setTipo(rs.getString("tipo"));
        siniestro.setFechaSiniestro(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
        siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
        siniestro.setDetalles(rs.getString("detalles"));
        siniestro.setBrigada(brigada);
        if (rs.getInt("puntuacion") != NIL) {
            siniestro.setFechaResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            siniestro.setFechaResolucion(null);
        }
        siniestro.setPuntuacion(rs.getInt("puntuacion"));
        return siniestro;
    }

}
