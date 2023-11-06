package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.*;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    /**
     * SUJETO A CAMBIOS
     */
    private final Connection connection;

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    public boolean crearFalsaBrigada() {
        boolean resultado = false;
        try {
            String sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado, codigoBrigada) VALUES ('brigada inexistente', '', false, -1, false, -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada falsa agregada");
            } else {
                System.out.println("[BrigadaData] No se pudo agregar a la brigada falsa");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BrigadaData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean guardarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
//            if (cuartelData.buscarCuartel(brigada.getCodigoCuartel())==null) {
//                System.out.println("[BrigadaData] No se pudo agregar a la brigada (el cuartel al cual se lo quiere asignar no existe)");
//                return resultado;
//            }
            //String sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) VALUES (?, ?, ?, ?, ?);";
            String sql;
            if (brigada.getCodigoBrigada() != -1) {
                sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado, codigoBrigada) VALUES (?, ?, ?, ?, ?, ?);";
            } else {
                sql = "INSERT INTO brigada(nombreBrigada, especialidad, disponible, codigoCuartel, estado) VALUES (?, ?, ?, ?, ?);";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setBoolean(5, brigada.isEstado());
            if (brigada.getCodigoBrigada() != -1) {
                ps.setInt(6, brigada.getCodigoBrigada());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada agregada");
            } else {
                System.out.println("[BrigadaData] No se pudo agregar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BrigadaData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Brigada buscarBrigada(int codigoBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada bri, cuartel cuar "
                    + "WHERE codigoBrigada=? AND bri.estado=true AND bri.codigoCuartel = cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));
                
                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));
                System.out.println("[BrigadaData] Brigada con codigo=" + codigoBrigada + " encontrada");
            } else {
                System.out.println("[BrigadaData] No se ha encontrado a la brigada con codigo=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public Brigada buscarBrigadaPorNombre(String nombreBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada bri, cuartel cuar "
                    + "WHERE nombreBrigada = ? AND bri.estado = true AND bri.codigoCuartel = cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));
                
                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));
                System.out.println("[BrigadaData] Brigada '" + nombreBrigada + "' encontrada");
            } else {
                System.out.println("[BrigadaData] No se ha encontrado a la brigada '" + nombreBrigada + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public List<Brigada> listarBrigadas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada bri, cuartel cuar "
                    + "WHERE bri.estado = true AND bri.codigoCuartel = cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));
                
                Brigada brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Brigada> listarBrigadasDisponibles() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada bri, cuartel cuar "
                    + "WHERE bri.estado = true AND bri.disponible = true AND bri.codigoCuartel = cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));
                
                Brigada brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Brigada> listarBrigadasOcupadas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada bri, cuartel cuar "
                    + "WHERE bri.estado = true AND bri.disponible = false AND bri.codigoCuartel = cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));
                
                Brigada brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberos(Brigada brigada) {
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada = ? AND estado = true;";    
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ResultSet rs = ps.executeQuery();  
            while (rs.next()) {                
                Bombero bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("idBombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreApellido(rs.getString("nombreApellido"));
                bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                bombero.setTelefono(rs.getLong("celular"));
                bombero.setBrigada(brigada);
                bombero.setEstado(rs.getBoolean("estado"));
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET nombreBrigada=?, especialidad=?, disponible=?, codigoCuartel=? WHERE codigoBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isDisponible());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setInt(5, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada modificada");
            } else {
                System.out.println("[BrigadaData] No se pudo modificar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigada(int codigoBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada eliminada");
            } else {
                System.out.println("[BrigadaData] No se pudo eliminar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigadaPorNombre(String nombreBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET estado=false WHERE nombreBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada eliminada");
            } else {
                System.out.println("[BrigadaData] No se pudo eliminar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
