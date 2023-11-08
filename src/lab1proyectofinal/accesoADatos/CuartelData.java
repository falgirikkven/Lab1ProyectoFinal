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

    public CuartelData() {
        this.connection = Conexion.getInstance();

    }

    public boolean guardarCuartel(Cuartel cuartel) {
        boolean resultado = false;
        try {
            String sql;
            if (cuartel.getCodigoCuartel() == -1) {
                sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado, codigoCuartel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setString(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setBoolean(7, cuartel.isEstado());
            if (cuartel.getCodigoCuartel() != -1) {
                ps.setInt(8, cuartel.getCodigoCuartel());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData.guardarCuartel] Agregado: " + cuartel.toString());
            } else {
                System.out.println("[CuartelData.guardarCuartel] No se agregó: " + cuartel.toString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[CuartelData.guardarCuartel] Error: entrada duplicada para " + cuartel.toString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Cuartel buscarCuartel(int codigoCuartel) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE codigoCuartel=? and estado=true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData.buscarCuartel] Encontrado: " + cuartel.toString());
            } else {
                System.out.println("[CuartelData.buscarCuartel] No se ha encontrado con id=" + codigoCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.buscarCuartel] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public Cuartel buscarCuartelPorNombre(String nombreCuartel) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE nombreCuartel=? and estado=true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = Utils.obtenerDeResultSetCuartel(rs);
                System.out.println("[CuartelData.buscarCuartelPorNombre] Encontrado: " + cuartel.toString());
            } else {
                System.out.println("[CuartelData.buscarCuartelPorNombre] No se ha encontrado con nombre='" + nombreCuartel + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData.buscarCuartelPorNombre] Error" + e.getErrorCode() + ": " + e.getMessage());
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
            System.out.println("[CuartelData.listarCuarteles] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return cuarteles;
    }

    public List<Brigada> listarBrigadasEnCuartel(int codigoCuartel) {
        // TODO: probar este metodo
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada JOIN cuartel ON (brigada.codigoCuartel=cuartel.codigoCuartel) WHERE cuartel.codigoCuartel=? AND brigada.estado=true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosEnCuartel(Cuartel cuartel) {
        // TODO: probar este metodo
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero JOIN brigada JOIN cuartel ON (bombero.codigoBrigada=brigada.codigoBrigada AND brigada.codigoCuartel=cuartel.codigoCuartel) WHERE cuartel.codigoCuartel=? AND bombero.estado=true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
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
            String sql = "UPDATE cuartel SET nombreCuartel=?, direccion=?, coordenadaX=?, coordenadaY=?, telefono=?, correo=? WHERE codigoCuartel=? AND estado=true";
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
                System.out.println("[CuartelData.modificarCuartel] Modificado: " + cuartel.toString());
            } else {
                System.out.println("[CuartelData.modificarCuartel] No se modificó: " + cuartel.toString());
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData.modificarCuartel] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartel(int codigoCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE codigoCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData.eliminarCuartel] Eliminado: codigoCuartel=" + codigoCuartel);
            } else {
                System.out.println("[CuartelData.eliminarCuartel] No se eliminó: codigoCuartel=" + codigoCuartel);
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData.eliminarCuartel] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartelPorNombre(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE nombreCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData.eliminarCuartelPorNombre] Eliminado: nombreCuartel='" + nombreCuartel + "'");
            } else {
                System.out.println("[CuartelData.eliminarCuartelPorNombre] No se eliminó: nombreCuartel='" + nombreCuartel + "'");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData.eliminarCuartelPorNombre] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
