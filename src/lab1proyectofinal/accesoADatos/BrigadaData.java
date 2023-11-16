package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.entidades.*;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    /**
     * SUJETO A CAMBIOS
     */
    private final Connection connection;
    public static Brigada brigadaNull = new Brigada("brigada null", "---", false, CuartelData.cuartelNull, false);

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    public boolean insertarBrigadaNull() {
        boolean resultado = false;
        try {
            String sql = "SELECT COUNT(nombreBrigada) FROM brigada WHERE nombreBrigada='brigada null'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    ps.close();     // me parece que podría dar error (si así fuera, se podría crear otro PreparedStatement)
                    sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) "
                            + "VALUES (?,?,?,?,?)";
                    ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, brigadaNull.getNombreBrigada());
                    ps.setString(2, brigadaNull.getEspecialidad());
                    ps.setBoolean(3, brigadaNull.isDisponible());
                    ps.setInt(4, brigadaNull.getCuartel().getCodigoCuartel());
                    ps.setBoolean(5, brigadaNull.isEstado());
                    ps.executeUpdate();
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        brigadaNull.setCodigoBrigada(rs.getInt(1));
                        resultado = true;
                        System.out.println("[CuartelData.insertarBrigadaNull] 'brigada null' agregada");
                    } else {
                        System.out.println("[CuartelData.insertarBrigadaNull] No se pudo agregar no se pudo agregar la 'brigada null'");
                    }
                    ps.close();
                } else {
                    System.out.println("[CuartelData.insertarBrigadaNull] No se ha podido agregar la 'brigada null' porque ya está agregada");
                }
            } else {
                System.out.println("[CuartelData.insertarBrigadaNull] Error al buscar la 'brigada null' en la BD");
            }
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[CuartelData.insertarBrigadaNull] Error " + errorCode + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean guardarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
            String sql;
            sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setBoolean(5, brigada.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                brigada.setCodigoBrigada(rs.getInt(1));
                resultado = true;
                System.out.println("[BrigadaData.guardarBrigada] Brigada agregada"
                        + "\nDatos de la brigada agregada: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.guardarBrigada] No se pudo agregar a la brigada"
                        + "\nDatos de la brigada que se intentó agregar: " + brigada.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BrigadaData.guardarBrigada] Error " + errorCode + " " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Brigada buscarBrigada(int codigoBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE codigoBrigada=? AND brigada.estado=true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigada] Brigada encontrada (código de brigada: " + codigoBrigada + ")");
            } else {
                System.out.println("[BrigadaData.buscarBrigada] No se ha encontrado a la brigada (código de brigada: " + codigoBrigada + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigada] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public Brigada buscarBrigadaPorNombre(String nombreBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE nombreBrigada = ? AND brigada.estado = true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] Brigada encontrada (nombre de brigada: " + nombreBrigada + ")");
            } else {
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] No se ha encontrado la brigada (nombre de brigada: " + nombreBrigada + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigadaPorNombre] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public List<Brigada> listarBrigadas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadas] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada asignable: disponible=true & cantidad de bomberos es 5 & la brigada no está atendiendo una emergencia
    public List<Brigada> listarBrigadasAsignables() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "      // una "parte brigada" y otra "parte cuartel"
                    + "WHERE brigada.estado = true "            // la parte brigada tiene un estado true
                    + "AND brigada.disponible = true "          // la parte brigada está dispobible
                    + "AND brigada.codigoCuartel = cuartel.codigoCuartel "      // la parte brigada y la parte cuartel están vinculados por 'codigoCuartel' (la brigada pertenece al cuartel)
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero WHERE bombero.codigoBrigada=brigada.codigoBrigada AND bombero.estado=true)=5 "     // la cantidad de bomberos activos que pertenecen a la parte de brigada tiene que ser igual a 5
                    + "AND (SELECT COUNT(codigoBrigada) FROM siniestro WHERE siniestro.codigoBrigada=brigada.codigoBrigada AND siniestro.puntuacion=-1)=0;";        // la cantidad de siniestros aun no resueltos que están siendo atendidos por la brigada es igual a 0
            
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAsignables] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada no asignable: toda brigada activa que no sea asignable (considero que es potencialmente descartable)
    public List<Brigada> listarBrigadasNoAsignables() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "      // una "parte brigada" y otra "parte cuartel"
                    + "WHERE brigada.estado = true "            // la parte brigada tiene un estado true
                    + "AND brigada.codigoCuartel = cuartel.codigoCuartel "      // la parte brigada y la parte cuartel están vinculados por 'codigoCuartel' (la brigada pertenece al cuartel)
                    + "AND (brigada.disponible != true "          // la parte brigada no está dispobible                    
                    + "OR (SELECT COUNT(codigoBrigada) FROM bombero WHERE bombero.codigoBrigada=brigada.codigoBrigada AND bombero.estado=true)!=5 "     // la cantidad de bomberos activos que pertenecen a la parte de brigada es distinta de 5
                    + "OR (SELECT COUNT(codigoBrigada) FROM siniestro WHERE siniestro.codigoBrigada=brigada.codigoBrigada AND siniestro.puntuacion=-1)!=0);";        // la cantidad de siniestros aun no resueltos que están siendo atendidos por la brigada es igual a 0            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasNoAsignables] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Brigada> listarBrigadasAtendiendoEmergencia() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true "
                    + "AND brigada.codigoCuartel = cuartel.codigoCuartel"
                    + "AND (SELECT COUNT(codigoBrigada) FROM siniestro WHERE siniestro.codigoBrigada=brigada.codigoBrigada AND puntuacion=-1)!=0; ";
                    
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAtendiendoEmergencia] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada incompleta: cantidad de bomberos menor que 5 (la brigada NO tiene la cantidad de integrantes requerida para actuar)
    public List<Brigada> listarBrigadasIncompletas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true "
                    + "AND brigada.codigoCuartel = cuartel.codigoCuartel";
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero WHERE bombero.codigoBrigada=brigada.codigoBrigada)<5; "
                    
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasIncompletas] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosDeBrigada(Brigada brigada) {
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada = ? AND estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs, brigada);
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBomberosDeBrigada] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET nombreBrigada=?, especialidad=?, disponible=?, codigoCuartel=? WHERE codigoBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setInt(5, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.modificarBrigada] Brigada modificada"
                        + "\nNuevos datos guardados: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.modificarBrigada] No se pudo modificar a la brigada"
                        + "\nDatos que se intentó guardar: " + brigada.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.modificarBrigada] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigada(int codigoBrigada) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true"
            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero WHERE codigoBrigada=? AND estado=true)=0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ps.setInt(2, codigoBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigada] Brigada eliminada (código de brigada: " + codigoBrigada + ")");
            } else {
                System.out.println("[BrigadaData.eliminarBrigada] No se pudo eliminar a la brigada (código de brigada: " + codigoBrigada + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigada] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigadaPorNombre(String nombreBrigada) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE brigada SET estado=false WHERE nombreBrigada=? AND estado=true";
            String sql = "UPDATE brigada SET estado=false WHERE nombreBrigada=? AND estado=true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero WHERE codigoBrigada IN (SELECT codigoBrigada FROM brigada WHERE nombreBrigada=?) AND estado=true)=0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ps.setString(2, nombreBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] Brigada eliminada (nombre de brigada: " + nombreBrigada + ")");
            } else {
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] No se pudo eliminar la brigada (nombre de brigada: " + nombreBrigada + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigadaPorNombre] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
