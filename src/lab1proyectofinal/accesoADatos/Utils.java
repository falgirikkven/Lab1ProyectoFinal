package lab1proyectofinal.accesoADatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class Utils {

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

    public static String[] obtenerGrupoSanguineo() {
        String[] s = new String[]{
            "A+",
            "B+",
            "AB+",
            "O+",
            "A-",
            "B-",
            "AB-",
            "O-"
        };
        return s;
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("^\\d+$");
    }

    public static Cuartel obtenerDeResultSetCuartel(ResultSet rs) throws SQLException {
        Cuartel cuartel = new Cuartel();
        cuartel.setCodigoCuartel(rs.getInt("cuartel.codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
        cuartel.setDireccion(rs.getString("direccion"));
        cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
        cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
        cuartel.setTelefono(rs.getString("telefono"));
        cuartel.setCorreo(rs.getString("correo"));
        cuartel.setEstado(rs.getBoolean("cuartel.estado"));
        return cuartel;
    }

    public static Brigada obtenerDeResultSetBrigada(ResultSet rs) throws SQLException {
        Cuartel cuartel = obtenerDeResultSetCuartel(rs);

        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("brigada.codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("nombreBrigada"));
        brigada.setEspecialidad(rs.getString("especialidad"));
        brigada.setDisponible(rs.getBoolean("disponible"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        return brigada;
    }

    public static Brigada obtenerDeResultSetBrigada(ResultSet rs, Cuartel cuartel) throws SQLException {
        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("brigada.codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("nombreBrigada"));
        brigada.setEspecialidad(rs.getString("especialidad"));
        brigada.setDisponible(rs.getBoolean("disponible"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        return brigada;
    }

    public static Bombero obtenerDeResultSetBombero(ResultSet rs) throws SQLException {
        Brigada brigada = obtenerDeResultSetBrigada(rs);

        Bombero bombero = new Bombero();
        bombero.setIdBombero(rs.getInt("idBombero"));
        bombero.setDni(rs.getInt("dni"));
        bombero.setNombreCompleto(rs.getString("nombreCompleto"));
        bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
        bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
        bombero.setCelular(rs.getString("celular"));
        bombero.setBrigada(brigada);
        bombero.setEstado(rs.getBoolean("bombero.estado"));
        return bombero;
    }

    public static Bombero obtenerDeResultSetBombero(ResultSet rs, Brigada brigada) throws SQLException {
        Bombero bombero = new Bombero();
        bombero.setIdBombero(rs.getInt("idBombero"));
        bombero.setDni(rs.getInt("dni"));
        bombero.setNombreCompleto(rs.getString("nombreCompleto"));
        bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
        bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
        bombero.setCelular(rs.getString("celular"));
        bombero.setBrigada(brigada);
        bombero.setEstado(rs.getBoolean("bombero.estado"));
        return bombero;
    }

    public static Siniestro obtenerDeResultSetSiniestro(ResultSet rs) throws SQLException {
        Brigada brigada = obtenerDeResultSetBrigada(rs);

        Siniestro siniestro = new Siniestro();
        siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
        siniestro.setTipo(rs.getString("tipo"));
        siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
        siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
        siniestro.setDetalles(rs.getString("detalles"));
        siniestro.setBrigada(brigada);
        if (rs.getInt("puntuacion") != Utils.NIL) {
            siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            siniestro.setFechaHoraResolucion(null);
        }
        siniestro.setPuntuacion(rs.getInt("puntuacion"));
        return siniestro;
    }

    public static Siniestro obtenerDeResultSetSiniestro(ResultSet rs, Brigada brigada) throws SQLException {
        Siniestro siniestro = new Siniestro();
        siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
        siniestro.setTipo(rs.getString("tipo"));
        siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
        siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
        siniestro.setDetalles(rs.getString("detalles"));
        siniestro.setBrigada(brigada);
        if (rs.getInt("puntuacion") != Utils.NIL) {
            siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            siniestro.setFechaHoraResolucion(null);
        }
        siniestro.setPuntuacion(rs.getInt("puntuacion"));
        return siniestro;
    }

    // LocalDate -> Calendar
    public static Calendar localDateToCalendar(LocalDate ldate) {
        ZonedDateTime zonedDateTime = ldate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
