package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    // revisado
    public boolean guardarBombero(Bombero bombero) {
        if (bombero.getIdBombero() != Utils.NIL || !bombero.isEstado()) {
            System.out.println("[BomberoData.guardarBombero] "
                    + "Error: no se puede guardar. "
                    + "Bombero dado de baja o tiene idBombero definido: "
                    + bombero.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "SELECT COUNT(codigoBrigada) FROM bombero WHERE codigoBrigada = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getBrigada().getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) == 5) {
                System.out.println("[BomberoData.guardarBombero] "
                        + "No se agregó: no hay más espacio "
                        + "en brigada con código: " + bombero.getBrigada().getCodigoBrigada() + ". "
                        + "Bombero que se intentó agregar: " + bombero.debugToString());
                ps.close();
                return false;
            }
            ps.close();
            sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, fechaNacimiento, celular, codigoBrigada, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, true);";
            ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bombero.setIdBombero(rs.getInt(1));
                resultado = true;
                System.out.println("[BomberoData.guardarBombero] "
                        + "Agregado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.guardarBombero] "
                        + "No se pudo agregar: " + bombero.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[BomberoData.guardarBombero] "
                        + "Error: entrada duplicada para " + bombero.debugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public Bombero buscarBomberoPorID(int idBombero) {
        Bombero bombero = null;
        try {
//            String sql = "SELECT * FROM bombero "
//                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
//                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
//                    + "WHERE bombero.idBombero = ?;";
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.idBombero = ? "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
                System.out.println("[BomberoData.buscarBombero] "
                        + "Encontrado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.buscarBombero] "
                        + "No se pudo encontrar con idBombero = " + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    // revisado
    public Bombero buscarBomberoPorDni(int dni) {
        Bombero bombero = null;
        try {
//            String sql = "SELECT * FROM bombero "
//                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada AND brigada.estado = true) "
//                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
//                    + "WHERE bombero.dni = ?;";
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.dni = ? "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "Encontrado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "No se pudo encontrar con dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    // revisado
    public List<Bombero> listarBomberos() {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
                bomberos.add(bombero);
            }
            System.out.println("[BomberoData.listarBomberos] "
                    + "Cantidad de bomberos: " + bomberos.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    // revisado
    public boolean modificarBombero(Bombero bombero) {
        if (bombero.getIdBombero() == Utils.NIL || !bombero.isEstado()) {
            System.out.println("[BomberoData.modificarBombero] "
                    + "Error: no se modificó. "
                    + "Bombero dado de baja o sin idBombero definido: "
                    + bombero.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET dni = ?, nombreCompleto = ?, grupoSanguineo = ?, "
                    + "fechaNacimiento = ?, celular = ?, codigoBrigada = ? "
                    + "WHERE idBombero = ? AND estado = true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.setInt(7, bombero.getIdBombero());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.modificarBombero] "
                        + "Modificado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.modificarBombero] "
                        + "No se pudo modificar: " + bombero.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.modificarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // con el fin de asegurar la mayor similitud posible entre objeto java y registro en la BD, se crea este método que posibilita cambiar el estado del objeto java
    public boolean eliminarBombero(Bombero bombero) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE idBombero = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM siniestro "
                    + "WHERE puntuacion = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getIdBombero());
            if (ps.executeUpdate() > 0) {
                bombero.setEstado(false);
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: idBombero=" + bombero.getIdBombero());
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: idBombero=" + bombero.getIdBombero());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBomberoPorID(int idBombero) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE bombero SET estado=false WHERE idBombero=? AND estado=true";
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE idBombero = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM siniestro "
                    + "WHERE puntuacion = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            if (ps.executeUpdate() > 0) {                
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: idBombero=" + idBombero);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: idBombero=" + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBomberoPorDni(int dni) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE dni = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM siniestro "
                    + "WHERE puntuacion = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: dni=" + dni);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBomberoPorDni] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
