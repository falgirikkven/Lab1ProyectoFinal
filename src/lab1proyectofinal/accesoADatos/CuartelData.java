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
        // TODO: Implementar este método
        boolean resultado = false;
        try {
            String sql;
            if (cuartel.getCodigoCuartel() == -1) {
                sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, codigoCuartel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setInt(5, (int) cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            if (cuartel.getCodigoCuartel() != -1) {
                ps.setInt(7, cuartel.getCodigoCuartel());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] cuartel agregado");
            } else {
                System.out.println("[CuartelData] cuartel no agregado");
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
        // TODO: Implementar este método
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                System.out.println("[CuartelData] cuartel con id=" + codigoCuartel + " encontrado");
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
        // TODO: Implementar este método
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE nombreCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                System.out.println("[CuartelData] cuartel con id=" + nombreCuartel + " encontrado");
            } else {
                System.out.println("[CuartelData] No se ha encontrado al cuartel con id=" + nombreCuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public List<Cuartel> listarCuarteles() {
        // TODO: Implementar este método
        List<Cuartel> cuarteles = new ArrayList();
        try {
            String sql = "SELECT * FROM cuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuarteles.add(cuartel);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuarteles;
    }

    public List<Brigada> listarBrigadasEnCuartel(int codigoCuartel) {
        // TODO: Implementar este método
        List<Brigada> brigadas = new ArrayList();
        try {
            //SELECT*FROM brigada WHERE codigoBrigda IN(SELECT codigoBrigada FROM cuartel WHERE codigoBrigada=?)AND estado=true;
            String sql = "SELECT * FROM brigada WHERE codigoCuartel = ? AND estado = true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setCodigoCuartel(rs.getInt("codigoCuartel"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setDisponible(rs.getBoolean("disponible"));
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
        // TODO: Implementar este método
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada IN "
                    + "(SELECT codigoBrigada FROM brigada WHERE codigoCuartel = ? AND estado = true) AND estado = true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
                //inserta bombero
                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("idBombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreApellido(rs.getString("nombreApellido"));
                bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                bombero.setTelefono(rs.getLong("celular"));
                bombero.setCodigoBrigada(rs.getInt("codigoBrigada"));
                bombero.setEstado(rs.getBoolean("estado"));
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
        // TODO: Implementar este método
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET nombreCuartel= ? , direccion = ? , coordenadaX = ? , coordenadaY = ? , telefono = ? , correo = ? WHERE codigoCuartel=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setInt(5, (int) cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setInt(7, cuartel.getCodigoCuartel());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel modificado");
            } else {
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartel(int codigoCuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE codigoCuartel=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel modificado");
            } else {
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarNombre(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE nombreCuartel=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel modificado");
            } else {
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
