package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.*;

/**
 *
 * @author Grupo-3
 */
public class BomberoData {

    /**
     * SUJETO A CAMBIOS
     */
    private final Connection connection;

    public BomberoData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBombero(Bombero bombero) {
        boolean resultado = false;
        try {            
            String sql = "SELECT COUNT(codigoBrigada) FROM bombero WHERE codigoBrigada=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getBrigada().getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1)==5) {
                    System.out.println("[BomberoData] No se pudo agregar al bombero. No hay más espacio en la brigada");
                    ps.close();
                    return resultado;
                }
            }
            ps.close();
            sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, fechaNacimiento, celular, codigoBrigada, estado) VALUES (?, ?, ?, ?, ?, ?, true)";            
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
                System.out.println("[BomberoData] Bombero agregado");                                
            } else {
                System.out.println("[BomberoData] No se pudo agregar al bombero");
            }
            ps.close();
        } catch (SQLException e) {
            // System.out.println("[BomberoData Error " + e.getErrorCode() + "] " + e.getMessage());
            // e.printStackTrace();
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
            String sql = "SELECT * FROM bombero, brigada, cuartel "
                    + "WHERE idBombero=? AND bombero.estado=true AND bombero.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
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
            String sql = "SELECT * FROM bombero, brigada, cuartel "
                    + "WHERE dni=? AND bombero.estado=true AND bombero.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
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
            String sql = "SELECT * FROM bombero, brigada, cuartel "
                    + "WHERE bombero.estado=true "
                    + "AND bombero.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
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
            String sql = "UPDATE bombero SET dni=?, nombreCompleto=?, grupoSanguineo=?, fechaNacimiento=?, celular=? WHERE idBombero=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getIdBombero());
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
            String sql = "UPDATE bombero SET estado=false WHERE idBombero=? AND estado=true";
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
            String sql = "UPDATE bombero SET estado=false WHERE dni=? AND estado=true";
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
