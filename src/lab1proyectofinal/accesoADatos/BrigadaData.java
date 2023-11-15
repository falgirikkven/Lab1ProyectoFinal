package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    private final Connection connection;

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBrigada(Brigada brigada) {
        if (brigada.getCodigoBrigada() != Utils.NIL || !brigada.isEstado()) {
            System.out.println("[BrigadaData.guardarBrigada] Error: no se puede guardar. "
                    + "Brigada dada de baja o tiene codigoBrigada definido. "
                    + brigada.DebugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) "
                    + "VALUES (?, ?, ?, ?, ?);";
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
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "Agregada: " + brigada.DebugToString());
            } else {
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "No se agregó: " + brigada.DebugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[BrigadaData.guardarBrigada] "
                        + "Error: entrada duplicada para " + brigada.DebugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Brigada buscarBrigada(int codigoBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE brigada.estado = true AND brigada.codigoBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigada] "
                        + "Encontrada: " + brigada.DebugToString());
            } else {
                System.out.println("[BrigadaData.buscarBrigada] "
                        + "No se ha encontrado con codigoBrigada=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigada] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public Brigada buscarBrigadaPorNombre(String nombreBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada JOIN cuartel "
                    + "ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE brigada.estado = true AND brigada.nombreBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                        + "Encontrada: " + brigada.DebugToString());
            } else {
                System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                        + "No se ha encontrado con nombreBrigada='" + nombreBrigada + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.buscarBrigadaPorNombre] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public List<Brigada> listarBrigadas() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE brigada.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadas] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Brigada> listarBrigadasDisponibles() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE brigada.estado = true AND brigada.disponible = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasDisponibles] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Brigada> listarBrigadasOcupadas() {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE brigada.estado = true AND brigada.disponible = false;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasOcupadas] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosEnBrigada(Brigada brigada) {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE bombero.estado = true AND brigada.codigoBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBomberosEnBrigada] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBrigada(Brigada brigada) {
        if (brigada.getCodigoBrigada() == Utils.NIL || !brigada.isEstado()) {
            System.out.println("[BrigadaData.modificarBrigada] Error: no se puede modificar."
                    + "Brigada dada de baja o no tiene codigoBrigada definido. "
                    + brigada.DebugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "UPDATE brigada "
                    + "SET nombreBrigada = ?, especialidad = ?, disponible = ?, codigoCuartel = ? "
                    + "WHERE brigada.estado = true AND brigada.codigoBrigada = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setInt(5, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.modificarBrigada] "
                        + "Modificada: " + brigada.DebugToString());
            } else {
                System.out.println("[BrigadaData.modificarBrigada] "
                        + "No se modificó: " + brigada.DebugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.modificarBrigada] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigada(int codigoBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada "
                    + "SET estado = false "
                    + "WHERE brigada.estado = true AND brigada.codigoBrigada = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "Eliminada: codigoBrigada=" + codigoBrigada);
            } else {
                System.out.println("[BrigadaData.eliminarBrigada] "
                        + "No se eliminó: codigoBrigada=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.eliminarBrigada] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigadaPorNombre(String nombreBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada "
                    + "SET estado = false "
                    + "WHERE brigada.estado = true AND brigada.nombreBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] "
                        + "Eliminada: nombreBrigada='" + nombreBrigada + "'");
            } else {
                System.out.println("[BrigadaData.eliminarBrigadaPorNombre] "
                        + "No se eliminó: nombreBrigada='" + nombreBrigada + "'");
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
