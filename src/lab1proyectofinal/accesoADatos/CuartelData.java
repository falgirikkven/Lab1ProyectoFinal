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

    private Connection connection;

    public CuartelData() {
        this.connection = Conexion.getInstance();
    }

    // revisado
    public boolean guardarCuartel(Cuartel cuartel) {
        if (cuartel.getCodigoCuartel() != Utils.NIL || !cuartel.isEstado()) {
            System.out.println("[CuartelData.guardarCuartel] Error: no se puede guardar. "
                    + "Cuartel dado de baja o tiene codigoCuartel definido: "
                    + cuartel.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, true);";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setString(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cuartel.setCodigoCuartel(rs.getInt(1));
                resultado = true;
                System.out.println("[CuartelData.guardarCuartel] "
                        + "Agregado: " + cuartel.debugToString());
            } else {
                System.out.println("[CuartelData.guardarCuartel] "
                        + "No se pudo agregar: " + cuartel.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[CuartelData.guardarCuartel] "
                        + "Error: entrada duplicada para "
                        + cuartel.debugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public Cuartel buscarCuartelPorCodigo(int codigoCuartel) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel "
                    + "WHERE cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData.buscarCuartel] "
                        + "Encontrado: " + cuartel.debugToString());
            } else {
                System.out.println("[CuartelData.buscarCuartel] "
                        + "No se encontró cuartel con codigoCuartel = " + codigoCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.buscarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    // revisado
    public Cuartel buscarCuartelPorNombre(String nombreCuartel) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel "
                    + "WHERE cuartel.nombreCuartel = ? "
                    + "AND cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData.buscarCuartelPorNombre] "
                        + "Encontrado: " + cuartel.debugToString());
            } else {
                System.out.println("[CuartelData.buscarCuartelPorNombre] "
                        + "No se encontró cuartel con nombreCuartel='" + nombreCuartel + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.buscarCuartelPorNombre] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    // revisado
    public boolean buscarNombreEntreInactivos(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "SELECT nombreCuartel FROM cuartel "
                    + "WHERE cuartel.nombreCuartel = ? "
                    + "AND cuartel.estado = false;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resultado = true;
                System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                        + "Se encontró con nombre='" + nombreCuartel + "'");
            } else {
                System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                        + "No se encontró con nombre='" + nombreCuartel + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado
    public List<Cuartel> listarCuarteles() {
        List<Cuartel> cuarteles = null;
        try {
            String sql = "SELECT * FROM cuartel "
                    + "WHERE cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            cuarteles = new ArrayList();
            while (rs.next()) {
                Cuartel cuartel = Utils.obtenerDeResultSetCuartel(rs);
                cuarteles.add(cuartel);
            }
            System.out.println("[CuartelData.listarCuarteles] "
                    + "Cantidad de cuarteles: " + cuarteles.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.listarCuarteles] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return cuarteles;
    }

    // revisado
    public List<Brigada> listarBrigadasDelCuartel(Cuartel cuartel) {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[CuartelData.listarBrigadasEnCuartel] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.listarBrigadasEnCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // revisado
    public List<Bombero> listarBomberosDelCuartel(Cuartel cuartel) {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
                bomberos.add(bombero);
            }
            System.out.println("[CuartelData.listarBomberosDelCuartel] "
                    + "Cantidad de bomberos: " + bomberos.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.listarBomberosDelCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    // revisado
    public boolean modificarCuartel(Cuartel cuartel) {
        if (cuartel.getCodigoCuartel() == Utils.NIL || !cuartel.isEstado()) {
            System.out.println("[CuartelData.modificarCuartel] "
                    + "Error: no se puede modificar."
                    + "Cuartel dado de baja o no tiene codigoCuartel definido: "
                    + cuartel.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET nombreCuartel = ?, direccion = ?, coordenadaX = ?, "
                    + "coordenadaY = ?, telefono = ?, correo = ? "
                    + "WHERE cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true;";
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
                System.out.println("[CuartelData.modificarCuartel] "
                        + "Modificado: " + cuartel.debugToString());
            } else {
                System.out.println("[CuartelData.modificarCuartel] "
                        + "No se pudo modificar: " + cuartel.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.modificarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // con el fin de asegurar la mayor similitud posible entre objeto java y registro en la BD, se crea este método que posibilita cambiar el estado del objeto java
    public boolean eliminarCuartel(Cuartel cuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado = false "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM brigada "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ps.setInt(2, cuartel.getCodigoCuartel());
            if (ps.executeUpdate() > 0) {
                cuartel.setEstado(false);
                resultado = true;
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "Eliminado: codigoCuartel=" + cuartel.getCodigoCuartel());
            } else {
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "No se pudo eliminar: codigoCuartel=" + cuartel.getCodigoCuartel());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.eliminarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarCuartelPorCodigo(int codigoCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado = false "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM brigada "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ps.setInt(2, codigoCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "Eliminado: codigoCuartel=" + codigoCuartel);
            } else {
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "No se pudo eliminar: codigoCuartel=" + codigoCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.eliminarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarCuartelPorNombre(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado = false "
                    + "WHERE nombreCuartel = ? "
                    + "AND estado = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM brigada "
                    + "WHERE codigoCuartel IN (SELECT codigoCuartel FROM cuartel "
                    + "WHERE nombreCuartel = ?) "
                    + "AND estado=true) = 0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ps.setString(2, nombreCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData.eliminarCuartelPorNombre] "
                        + "Eliminado: nombreCuartel='" + nombreCuartel + "'");
            } else {
                System.out.println("[CuartelData.eliminarCuartelPorNombre] "
                        + "No se pudo eliminar: nombreCuartel='" + nombreCuartel + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.eliminarCuartelPorNombre] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
