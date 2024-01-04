package lab1proyectofinal.accesoADatos;

import java.awt.Dimension;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Emergencia;
import java.lang.Math;
import java.time.LocalTime;

/**
 *
 * @author Grupo-3
 */
public class Utils {

    public static final int codigoNoEstablecido = -1;
    public static final int desempenioNoEstablecida = -1;
    public static final String nombreEntidadNula = "nulo";

    public static final Font fuentePlana = new Font("Lucida Sans Unicode", 0, 14);
    public static final Font fuenteNegrita = new Font("Lucida Sans Unicode", 1, 14);

    public static Date horaPorDefecto() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static JLabel jLabConfigurado() {
        JLabel jLab = new JLabel();
        jLab.setFont(fuentePlana);
        Dimension dim = new Dimension(300, 80);
        jLab.setPreferredSize(dim);
        return jLab;
    }

    public static enum Dato {
        ID_BOMBERO,
        CODIGO_CUARTEL,
        CODIGO_BRIGADA,
        CODIGO_SINIESTRO,
        DNI,
        NOMBRE,
        NOMBRE_COMPLETO,
        CELULAR,
        TELEFONO,
        GRUPO_SANGUINEO,
        CORREO,
        FECHA_NACIMIENTO,
        FECHA_INICIO,
        FECHA_RESOLUCION,
        DESEMPEÑO,
        ESTADO,
        BRIGADA,
        CUARTEL
    }

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

    public static boolean esCorreoValido(String correo) {
        return correo.matches("(?=[a-z]\\w{2,}@[a-z]\\w{2,}(\\.[a-z]{2,4}){1,2}$).{1,50}");
    }

    public static boolean esTelefonoValido(String telefono) {
        return telefono.matches("^\\d{9,15}$");
    }   

    public static Cuartel obtenerDeResultSetCuartel(ResultSet rs) throws SQLException {
        Cuartel cuartel = new Cuartel();
        cuartel.setCodigoCuartel(rs.getInt("cuartel.codigoCuartel"));
        cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
        cuartel.setDireccion(rs.getString("direccion"));
        cuartel.setCoordenadaX(rs.getDouble("coordenadaX"));
        cuartel.setCoordenadaY(rs.getDouble("coordenadaY"));
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
        brigada.setEnServicio(rs.getBoolean("enServicio"));
        brigada.setCuartel(cuartel);
        brigada.setEstado(rs.getBoolean("brigada.estado"));
        return brigada;
    }

    public static Brigada obtenerDeResultSetBrigada(ResultSet rs, Cuartel cuartel) throws SQLException {
        Brigada brigada = new Brigada();
        brigada.setCodigoBrigada(rs.getInt("brigada.codigoBrigada"));
        brigada.setNombreBrigada(rs.getString("nombreBrigada"));
        brigada.setEspecialidad(rs.getString("especialidad"));
        brigada.setEnServicio(rs.getBoolean("enServicio"));
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

    public static Emergencia obtenerDeResultSetEmergencia(ResultSet rs) throws SQLException {
        Brigada brigada = obtenerDeResultSetBrigada(rs);

        Emergencia emergencia = new Emergencia();
        emergencia.setCodigoEmergencia(rs.getInt("codigoEmergencia"));
        emergencia.setTipo(rs.getString("tipo"));
        emergencia.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        emergencia.setCoordenadaX(rs.getDouble("coordenadaX"));
        emergencia.setCoordenadaY(rs.getDouble("coordenadaY"));
        emergencia.setDetalles(rs.getString("detalles"));
        emergencia.setBrigada(brigada);
        if (rs.getInt("desempenio") != Utils.desempenioNoEstablecida) {
            emergencia.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            emergencia.setFechaHoraResolucion(null);
        }
        emergencia.setDesempenio(rs.getInt("desempenio"));
        return emergencia;
    }

    public static Emergencia obtenerDeResultSetEmergencia(ResultSet rs, Brigada brigada) throws SQLException {
        Emergencia emergencia = new Emergencia();
        emergencia.setCodigoEmergencia(rs.getInt("codigoEmergencia"));
        emergencia.setTipo(rs.getString("tipo"));
        emergencia.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
        emergencia.setCoordenadaX(rs.getDouble("coordenadaX"));
        emergencia.setCoordenadaY(rs.getDouble("coordenadaY"));
        emergencia.setDetalles(rs.getString("detalles"));
        emergencia.setBrigada(brigada);
        if (rs.getInt("desempenio") != Utils.desempenioNoEstablecida) {
            emergencia.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            emergencia.setFechaHoraResolucion(null);
        }
        emergencia.setDesempenio(rs.getInt("desempenio"));
        return emergencia;
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

    public static Date localDateToDate(LocalDate ldate) {
        ZonedDateTime zonedDateTime = ldate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public static Date localTimeToDate(LocalTime ltime) {
        Instant instant = ltime.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

}
