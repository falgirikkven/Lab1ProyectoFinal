package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;

/**
 *
 * @author Grupo-3
 */
public class BomberoData {

    private final Connection connection;

    public BomberoData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBombero(Bombero bombero) {
        if (bombero.getIdBombero() != Utils.NIL || !bombero.isEstado()) {
            System.out.println("[BomberoData.guardarBombero] Error: no se puede guardar. "
                    + "Bombero dado de baja o tiene idBombero definido. "
                    + bombero.DebugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, fechaNacimiento, celular, codigoBrigada, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreApellido());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.setBoolean(7, bombero.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bombero.setIdBombero(rs.getInt(1));
                resultado = true;
                System.out.println("[BomberoData.guardarBombero] "
                        + "Agregado: " + bombero.DebugToString());
            } else {
                System.out.println("[BomberoData.guardarBombero] "
                        + "No se agregó: " + bombero.DebugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[BomberoData.guardarBombero] "
                        + "Error: entrada duplicada para " + bombero.DebugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Bombero buscarBombero(int idBombero) {
        Bombero bombero = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE bombero.estado = true AND bombero.idBombero = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
                System.out.println("[BomberoData.buscarBombero] "
                        + "Encontrado: " + bombero.DebugToString());
            } else {
                System.out.println("[BomberoData.buscarBombero] "
                        + "No se ha encontrado con idBombero=" + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    public Bombero buscarBomberoPorDni(int dni) {
        Bombero bombero = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE bombero.estado = true AND bombero.dni = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "Encontrado: " + bombero.DebugToString());
            } else {
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "No se ha encontrado con dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    public List<Bombero> listarBomberos() {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
                    + "WHERE bombero.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBombero(Bombero bombero) {
        if (bombero.getIdBombero() == Utils.NIL || !bombero.isEstado()) {
            System.out.println("[BomberoData.modificarBombero] Error: no se puede modificar. "
                    + "Bombero dado de baja o no tiene idBombero definido. "
                    + bombero.DebugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "UPDATE bombero "
                    + "SET dni=?, nombreCompleto=?, grupoSanguineo=?, fechaNacimiento=?, celular=?, codigoBrigada=? "
                    + "WHERE idBombero=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreApellido());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.setInt(7, bombero.getIdBombero());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.modificarBombero] "
                        + "Modificado: " + bombero.DebugToString());
            } else {
                System.out.println("[BomberoData.modificarBombero] "
                        + "No se modificó: " + bombero.DebugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.modificarBombero] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBombero(int idBombero) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero "
                    + "SET estado = false "
                    + "WHERE bombero.estado = true AND bombero.idBombero = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: idBombero=" + idBombero);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se eliminó: idBombero=" + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBombero] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBomberoPorDni(int dni) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero "
                    + "SET estado = false "
                    + "WHERE bombero.estado = true AND bombero.dni = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: dni=" + dni);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se eliminó: dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBomberoPorDni] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
