package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;

/**
 *
 * @author Grupo-3
 */
public class CuartelData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;
    public static Cuartel cuartelNull = new Cuartel("cuartel null", "---", 0, 0, "---", "---", false);

    public CuartelData() {
        this.connection = Conexion.getInstance();
    }

    public boolean insertarCuartelNull() {
        boolean resultado = false;
        try {
            String sql = "SELECT COUNT(nombreCuartel) FROM cuartel WHERE nombreCuartel='cuartel null'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 0) {
                    ps.close();     // me parece que podría dar error (si así fuera, se podría crear otro PreparedStatement)
                    sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado) "
                            + "VALUES (?,?,?,?,?,?,?)";
                    ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, cuartelNull.getNombreCuartel());
                    ps.setString(2, cuartelNull.getDireccion());
                    ps.setInt(3, cuartelNull.getCoordenadaX());
                    ps.setInt(4, cuartelNull.getCoordenadaY());
                    ps.setString(5, cuartelNull.getTelefono());
                    ps.setString(6, cuartelNull.getCorreo());
                    ps.setBoolean(7, cuartelNull.isEstado());
                    ps.executeUpdate();
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        cuartelNull.setCodigoCuartel(rs.getInt(1));
                        resultado = true;
                        System.out.println("[CuartelData] Cuartel null agregado");
                    } else {
                        System.out.println("[CuartelData] No se pudo agregar el cuartel null");
                    }
                    ps.close();
                } else {
                    System.out.println("[CuartelData] No se ha podido agregar el cuartel null porque ya está agregado");
                }
            } else {
                System.out.println("[CuartelData] Error al buscar a cuartelNull en la BD");
            }
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[CuartelData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }   

    public boolean guardarCuartel(Cuartel cuartel) {
        boolean resultado = false;
        try {
            String sql;
            sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";             
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setString(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setBoolean(7, cuartel.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cuartel.setCodigoCuartel(rs.getInt(1));
                resultado = true;
                System.out.println("[CuartelData] Cuartel agregado");
            } else {
                System.out.println("[CuartelData] No se pudo agregar el cuartel");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[CuartelData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Cuartel buscarCuartel(int codigoCuartel) {
        Cuartel cuartel = null;
        try {
            String sql;
            sql = "SELECT * FROM cuartel WHERE codigoCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData] Cuartel con id=" + codigoCuartel + " encontrado");
            } else {
                System.out.println("[CuartelData] No se ha encontrado al cuartel con id=" + codigoCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public Cuartel buscarCuartelPorNombre(String nombreCuartel) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE nombreCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData] Cuartel con nombre=" + nombreCuartel + " encontrado");
            } else {
                System.out.println("[CuartelData] No se ha encontrado al cuartel con nombre=" + nombreCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public List<Cuartel> listarCuarteles() {
        List<Cuartel> cuarteles = new ArrayList();
        try {
            String sql = "SELECT * FROM cuartel WHERE estado=true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                cuarteles.add(cuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuarteles;
    }

    public List<Brigada> listarBrigadasDelCuartel(Cuartel cuartel) {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada WHERE codigoCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs, cuartel);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosDelCuartel(Cuartel cuartel) {
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero, brigada "
                    + "WHERE bombero.codigoBrigada IN "
                    + "(SELECT codigoBrigada FROM brigada WHERE codigoCuartel = ? AND codigoCuartel IN (SELECT codigoCuartel FROM cuartel WHERE codigoCuartel=? AND estado=true) AND estado = true) "
                    + "AND bombero.estado = true "
                    + "AND brigada.codigoBrigada = bombero.codigoBrigada ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ps.setInt(2, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            Bombero bombero;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs, cuartel);
                bombero = Utils.obtenerDeResultSetBombero(rs, brigada);
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarCuartel(Cuartel cuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET nombreCuartel=? , direccion=? , coordenadaX=? , coordenadaY=? , telefono=? , correo=? "
                    + "WHERE codigoCuartel=? "
                    + "AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setString(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setInt(7, cuartel.getCodigoCuartel());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel modificado");
            } else {
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartel(int codigoCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false "
                    + "WHERE codigoCuartel=? "
                    + "AND estado=true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM brigada WHERE codigoCuartel=? AND estado=true)=0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ps.setInt(2, codigoCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel eliminado");
            } else {
                System.out.println("[CuartelData] No se pudo eliminar el cuartel");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartelPorNombre(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false "
                    + "WHERE nombreCuartel=? "
                    + "AND estado=true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM brigada "
                    + "WHERE codigoCuartel=(SELECT codigoCuartel FROM cuartel WHERE nombreCuartel=?) AND estado=true)=0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ps.setString(2, nombreCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel eliminado");
            } else {
                System.out.println("[CuartelData] No se pudo eliminar el cuartel");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
