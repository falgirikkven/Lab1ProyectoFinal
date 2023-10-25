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

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public BomberoData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBombero(Bombero bombero) {
        boolean resultado = false;
        try {
            String sql;
            if (bombero.getIdBombero() == -1) {
                sql = "INSERT INTO bombero(dni, nombre_apellido, fecha_nacimiento, telefono, codigo_brigada, estado) VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO bombero(dni, nombre_apellido, fecha_nacimiento, telefono, codigo_brigada, estado, id_bombero) VALUES (?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreApellido());
            ps.setDate(3, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setLong(4, bombero.getTelefono());
            ps.setInt(5, bombero.getCodigoBrigada());
            ps.setBoolean(6, true);
            if (bombero.getIdBombero() != -1) {
                ps.setInt(7, bombero.getIdBombero());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData] Bombero agregado");
            } else {
                System.out.println("[BomberoData] No se pudo agregar al bombero");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BomberoData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Bombero buscarBombero(int idBombero) {
        Bombero bombero = null;
        try {
            String sql = "SELECT * FROM bombero WHERE id_bombero=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("id_bombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreApellido(rs.getString("nombre_apellido"));
                bombero.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                bombero.setTelefono(rs.getLong("telefono"));
                bombero.setCodigoBrigada(rs.getInt("codigo_brigada"));
                bombero.setEstado(rs.getBoolean("estado"));
                System.out.println("[BomberoData] Bombero con id=" + idBombero + " encontrado");
            } else {
                System.out.println("[BomberoData] No se ha encontrado al bombero con id=" + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    public Bombero buscarBomberoPorDni(int dni) {
        Bombero bombero = null;
        try {
            String sql = "SELECT * FROM bombero WHERE dni=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("id_bombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreApellido(rs.getString("nombre_apellido"));
                bombero.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                bombero.setTelefono(rs.getLong("telefono"));
                bombero.setCodigoBrigada(rs.getInt("codigo_brigada"));
                bombero.setEstado(rs.getBoolean("estado"));
                System.out.println("[BomberoData] Bombero con dni=" + dni + " encontrado");
            } else {
                System.out.println("[BomberoData] No se ha encontrado al bombero con dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    public List<Bombero> listarBomberos() {
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("id_bombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreApellido(rs.getString("nombre_apellido"));
                bombero.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                bombero.setTelefono(rs.getLong("telefono"));
                bombero.setCodigoBrigada(rs.getInt("codigo_brigada"));
                bombero.setEstado(rs.getBoolean("estado"));
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBombero(Bombero bombero) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET dni=?, nombre_apellido=?, fecha_nacimiento=?, telefono=?, codigo_brigada=?, estado=? WHERE id_bombero=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreApellido());
            ps.setDate(3, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setLong(4, bombero.getTelefono());
            ps.setInt(5, bombero.getCodigoBrigada());
            ps.setBoolean(6, true);
            ps.setInt(7, bombero.getIdBombero());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData] Bombero modificado");
            } else {
                System.out.println("[BomberoData] No se pudo modificar al bombero");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBombero(int idBombero) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado=false WHERE id_bombero=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData] Bombero eliminado");
            } else {
                System.out.println("[BomberoData] No se pudo eliminar al bombero");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBomberoPorDni(int dni) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado=false WHERE dni=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData] Bombero eliminado");
            } else {
                System.out.println("[BomberoData] No se pudo eliminar al bombero");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
