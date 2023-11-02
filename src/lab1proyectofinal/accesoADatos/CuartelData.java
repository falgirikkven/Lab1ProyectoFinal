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
    
    public Cuartel buscarCuartel(int codigoCuartel, boolean estado) { 
        Cuartel cuartel = null;
        try {
            String sql;            
            sql = "SELECT * FROM cuartel WHERE codigoCuartel=? AND estado=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ps.setBoolean(2, estado);
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

    public Cuartel buscarCuartelPorNombre(String nombreCuartel, boolean estado) {
        Cuartel cuartel = null;
        try {
            String sql = "SELECT * FROM cuartel WHERE nombreCuartel=? AND estado=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreCuartel);
            ps.setBoolean(2, estado);
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
                System.out.println("[CuartelData] Cuartel con id=" + nombreCuartel + " encontrado");
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

    public List<Cuartel> listarCuarteles(boolean estado) {        
        List<Cuartel> cuarteles = new ArrayList();
        try {
            String sql = "SELECT * FROM cuartel WHERE estado=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, estado);
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

    public List<Brigada> listarBrigadasEnCuartel(int codigoCuartel, boolean estado) {        
        List<Brigada> brigadas = new ArrayList();
        try {            
            String sql = "SELECT * FROM brigada WHERE codigoCuartel=? AND estado=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoCuartel);
            ps.setBoolean(2, estado);
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
    
    public List<Bombero> listarBomberosEnCuartel(Cuartel cuartel, boolean estadoBrigada, boolean estadoBombero) {        
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada IN "
                    + "(SELECT codigoBrigada FROM brigada WHERE codigoCuartel=? AND estado=?) AND estado=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuartel.getCodigoCuartel());
            ps.setBoolean(2, estadoBrigada);
            ps.setBoolean(3, estadoBombero);
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
            String sql = "UPDATE cuartel SET nombreCuartel=? , direccion=? , coordenadaX=? , coordenadaY=? , telefono=? , correo=?, estado=? WHERE codigoCuartel=?";
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

    public boolean bajaBomberos(Cuartel cuartel){
        boolean resultado = false;
        try {
            
        } catch (Exception e) {
        }
    }
    
    public boolean eliminarCuartel(int codigoCuartel) {
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
