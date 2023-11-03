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
                sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, telefono, correo, estado, codigoCuartel) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";       // si en el futuro dejamos la idea de que el usuario ingrese un id/código, entonces considero que esto va a quedar obsoleto
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setLong(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setBoolean(7, cuartel.isEstado());       // en la práctica, siempre debería de ser true
            if (cuartel.getCodigoCuartel() != -1) {         // (comentario copiado) si en el futuro dejamos la idea de que el usuario ingrese un id/código, entonces considero que esto va a quedar obsoleto
                ps.setInt(8, cuartel.getCodigoCuartel());
            }
            if (ps.executeUpdate() > 0) {
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
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("estado"));
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
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
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
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada WHERE codigoCuartel=? AND estado=true";
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
                brigada.setEstado(rs.getBoolean("estado"));
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
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada IN "
                    + "(SELECT codigoBrigada FROM brigada WHERE codigoCuartel=? AND estado=true) AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
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
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET nombreCuartel=? , direccion=? , coordenadaX=? , coordenadaY=? , telefono=? , correo=? WHERE codigoCuartel=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuartel.getNombreCuartel());
            ps.setString(2, cuartel.getDireccion());
            ps.setInt(3, cuartel.getCoordenadaX());
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setLong(5, cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setBoolean(7, cuartel.isEstado());
            ps.setInt(8, cuartel.getCodigoCuartel());
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
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE codigoCuartel=? AND estado=true AND (SELECT COUNT(codigoBrigada) FROM brigada WHERE codigoCuartel=? AND estado=true)=0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ps.setInt(2, codigoCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel eliminado");
            } else {
                System.out.println("[CuartelData] No se pudo eliminar el cuartel");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartelPorNombre(String nombreCuartel) {
        boolean resultado = false;
        try {
            String sql = "UPDATE cuartel SET estado=false WHERE nombreCuartel=? AND estado=true AND (SELECT COUNT(codigoBrigada) FROM brigada WHERE codigoCuartel=(SELECT codigoCuartel FROM cuartel WHERE nombreCuartel=?) AND estado=true)=0";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ps.setString(2, nombreCuartel);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[CuartelData] Cuartel eliminado");
            } else {
                System.out.println("[CuartelData] No se pudo eliminar el cuartel");
            }
        } catch (SQLException e) {
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        } 
        return resultado;
    }

}
