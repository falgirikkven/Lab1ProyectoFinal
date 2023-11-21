package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    private final Connection connection;

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    // revisado
    public boolean guardarBrigada(Brigada brigada) {
        if (brigada.getCodigoBrigada() != Utils.NIL || !brigada.isEstado()) {
            System.out.println("[BrigadaData.guardarBrigada] "
                    + "Error: no se puede guardar. "
                    + "Brigada dada de baja o tiene codigoBrigada definido: "
                    + brigada.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) "
                    + "VALUES (?, ?, ?, ?, true);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                brigada.setCodigoBrigada(rs.getInt(1));
                resultado = true;
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "Agregada: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "No se pudo agregar: " + brigada.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "Error: entrada duplicada para " + brigada.debugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public Brigada buscarBrigadaPorCodigo(int codigoBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND brigada.codigoBrigada = ? "
                    + "AND brigada.estado = true);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigada] "
                        + "Encontrada: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.buscarBrigada] "
                        + "No se encontró con codigoBrigada=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigada] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    // revisado
    public Brigada buscarBrigadaPorNombre(String nombreBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND brigada.nombreBrigada = ? "
                    + "AND brigada.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                        + "Encontrada: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                        + "No se encontró con nombreBrigada='" + nombreBrigada + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    // revisado
    public List<Brigada> listarBrigadas() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[BrigadaData.listarBrigadas] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadas] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // (revisado) brigada asignable: disponible=true & cantidad de bomberos es 5 & la brigada no está atendiendo una emergencia
    public List<Brigada> listarBrigadasAsignables() {
        List<Brigada> brigadas = null;
        try {
            //String sql = "SELECT * FROM brigada "
            //        + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
            //        + "WHERE brigada.estado = true AND brigada.disponible = true;";
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true "
                    + "AND brigada.disponible = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true) = 5 "
                    + "AND (SELECT COUNT(codigoBrigada) FROM siniestro "
                    + "WHERE siniestro.codigoBrigada = brigada.codigoBrigada AND siniestro.puntuacion = -1) = 0);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // (revisado, potencialmente innecesario) brigada no asignable: toda brigada activa que no sea asignable (considero que es potencialmente descartable)
    public List<Brigada> listarBrigadasNoAsignables() {
        List<Brigada> brigadas = null;
        try {
            //String sql = "SELECT * FROM brigada "
            //        + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
            //        + "WHERE brigada.estado = true AND brigada.disponible = false;";
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true "
                    + "AND (brigada.disponible = false "
                    + "OR (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true) < 5 "
                    + "OR (SELECT COUNT(codigoBrigada) FROM siniestro "
                    + "WHERE siniestro.codigoBrigada = brigada.codigoBrigada AND siniestro.puntuacion = -1) > 0));";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[BrigadaData.listarBrigadasNoAsignables] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasNoAsignables] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // revisado
    public List<Brigada> listarBrigadasAtendiendoEmergencia() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND brigada.estado = true "
                    + "AND cuartel.estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM siniestro "
                    + "WHERE siniestro.codigoBrigada = brigada.codigoBrigada "
                    + "AND puntuacion = -1) > 0);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAtendiendoEmergencia] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // (revisado) brigada incompleta: cantidad de bomberos menor que 5 (la brigada NO tiene la cantidad de integrantes requerida para actuar)
    public List<Brigada> listarBrigadasIncompletas() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND brigada.estado = true "
                    + "AND cuartel.estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true) < 5);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasIncompletas] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // revisado
    public List<Bombero> listarBomberosDeBrigada(Brigada brigada) {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "WHERE bombero.codigoBrigada = ? "
                    + "AND bombero.estado = true"
                    + "AND brigada.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs, brigada);
                bomberos.add(bombero);
            }
            System.out.println("[BrigadaData.listarBomberosDeBrigada] "
                    + "Cantidad de bomberos: " + bomberos.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBomberosDeBrigada] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    // revisado
    public boolean modificarBrigada(Brigada brigada) {
        if (brigada.getCodigoBrigada() == Utils.NIL || !brigada.isEstado()) {
            System.out.println("[BrigadaData.modificarBrigada] "
                    + "Error: no se pudo modificar. "
                    + "Brigada dada de baja o sin codigoBrigada definido: "
                    + brigada.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET nombreBrigada = ?, especialidad = ?, disponible = ?, codigoCuartel = ? "
                    + "WHERE brigada.estado = true "
                    + "AND brigada.codigoBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setInt(5, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.modificarBrigada] "
                        + "Modificada: " + brigada.debugToString());
            } else {
                System.out.println("[BrigadaData.modificarBrigada] "
                        + "No se pudo modificar: " + brigada.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.modificarBrigada] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // (revisado) con el fin de asegurar la mayor similitud posible entre objeto java y registro en la BD, se crea este método que posibilita cambiar el estado del objeto java
    public boolean eliminarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true"
            String sql = "UPDATE brigada SET estado = false "
                    + "WHERE brigada.codigoBrigada = ? "
                    + "AND brigada.estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = ? "
                    + "AND bombero.estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ps.setInt(2, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                brigada.setEstado(false);
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "Eliminada: codigoBrigada=" + brigada.getCodigoBrigada());
            } else {
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "No se pudo eliminar: codigoBrigada=" + brigada.getCodigoBrigada());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigada] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBrigadaPorCodigo(int codigoBrigada) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true"
            String sql = "UPDATE brigada SET estado = false "
                    + "WHERE brigada.codigoBrigada = ? "
                    + "AND brigada.estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = ? "
                    + "AND bombero.estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ps.setInt(2, codigoBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "Eliminada: codigoBrigada=" + codigoBrigada);
            } else {
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "No se pudo eliminar: codigoBrigada=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigada] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBrigadaPorNombre(String nombreBrigada) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE brigada SET estado=false WHERE nombreBrigada=? AND estado=true";
            String sql = "UPDATE brigada SET estado = false "
                    + "WHERE brigada.nombreBrigada = ? "
                    + "AND brigada.estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada IN (SELECT codigoBrigada FROM brigada "
                    + "WHERE nombreBrigada = ?) "
                    + "AND bombero.estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ps.setString(2, nombreBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] "
                        + "Eliminada: nombreBrigada='" + nombreBrigada + "'");
            } else {
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] "
                        + "No se pudo eliminar: nombreBrigada='" + nombreBrigada + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigadaPorNombre] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
