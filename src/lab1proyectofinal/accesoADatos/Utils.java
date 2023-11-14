package lab1proyectofinal.accesoADatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    
    public static void imprimirListaBombero(List<Bombero> lista){
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Bombero bom : lista) {
            System.out.println(bom.debugToString());
        }
    }
    
    public static void imprimirListaSiniestro(List<Siniestro> lista){
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Siniestro sin : lista) {
            System.out.println(sin.debugToString());
        }
    }
    
    public static void imprimirListaBrigada(List<Brigada> lista){
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Brigada bri : lista) {
            System.out.println(bri.debugToString());
        }
    }
    
    public static void imprimirListaCuartel(List<Cuartel> lista){
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Cuartel cru : lista) {
            System.out.println(cru.debugToString());
        }
    }
    
    public static void imprimirResultadoBombero(Bombero bombero){
        if (bombero != null) {
            System.out.println("Mostrando:\n" + bombero.debugToString());
        } 
    }
    
    public static void imprimirResultadoSiniestro(Siniestro siniestro){
        if (siniestro != null) {
            System.out.println("Mostrando:\n" + siniestro.debugToString());
        } 
    }
    
    public static void imprimirResultadoBrigada(Brigada brigada){
        if (brigada != null) {
            System.out.println("Mostrando:\n" + brigada.debugToString());
        } 
    }
    
    public static void imprimirResultadoCuartel(Cuartel cuartel){
        if (cuartel != null) {
            System.out.println("Mostrando:\n" + cuartel.debugToString());
        } 
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
        if (rs.getInt("puntuacion") != Siniestro.PUNTUACION_NIL) {
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
        if (rs.getInt("puntuacion") != Siniestro.PUNTUACION_NIL) {
            siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
        } else {
            siniestro.setFechaHoraResolucion(null);
        }
        siniestro.setPuntuacion(rs.getInt("puntuacion"));
        return siniestro;
    }

}
