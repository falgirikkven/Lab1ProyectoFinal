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
            String sql;
            if (bombero.getIdBombero() == -1) {
                sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, fechaNacimiento, celular, codigoBrigada, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, fechaNacimiento, celular, codigoBrigada, estado, idBombero) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.setBoolean(7, true);
            if (bombero.getIdBombero() != -1) {
                ps.setInt(8, bombero.getIdBombero());
            }
            if (ps.executeUpdate() > 0) {
                System.out.println("[BomberoData] Bombero agregado");
                
                // actualizando 'cantBomberos' en la brigada en la que ingresó al bombero
                sql = "UPDATE brigada SET cantBomberos=cantBomberos+1 WHERE codigoBrigada=?";
                ps.setInt(1, bombero.getBrigada().getCodigoBrigada());
                ps = connection.prepareStatement(sql);
                if (ps.executeUpdate() > 0) {
                    resultado = true;
                    System.out.println("[BomberoData] Brigada actualizada");
                } else {                    
                    System.out.println("[BomberoData] No se pudo actualizar la brigada");
                }
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
            String sql = "SELECT * FROM bombero bom, brigada bri, cuartel cuar "
                    + "WHERE idBombero=? AND bom.estado=true AND bom.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("bri.codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("idBombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreCompleto(rs.getString("nombreCompleto"));
                bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                bombero.setCelular(rs.getString("celular"));
                bombero.setBrigada(brigada);
                bombero.setEstado(rs.getBoolean("bom.estado"));
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
            String sql = "SELECT * FROM bombero bom, brigada bri, cuartel cuar "
                    + "WHERE dni=? AND bom.estado=true AND bom.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("bri.codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("idBombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreCompleto(rs.getString("nombreCompleto"));
                bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                bombero.setCelular(rs.getString("celular"));
                bombero.setBrigada(brigada);
                bombero.setEstado(rs.getBoolean("bom.estado"));
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
            String sql = "SELECT * FROM bombero bom, brigada bri, cuartel cuar "
                    + "WHERE bom.estado=true AND bom.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            Bombero bombero;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("bri.codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                bombero = new Bombero();
                bombero.setIdBombero(rs.getInt("idBombero"));
                bombero.setDni(rs.getInt("dni"));
                bombero.setNombreCompleto(rs.getString("nombreCompleto"));
                bombero.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                bombero.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                bombero.setCelular(rs.getString("celular"));
                bombero.setBrigada(brigada);
                bombero.setEstado(rs.getBoolean("bom.estado"));
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
                System.out.println("[BomberoData] Bombero eliminado");
                
                // actualizando 'cantBomberos' en la brigada en la cual se dió de baja al bombero
                sql = "UPDATE brigada SET cantBomberos=cantBomberos-1 "
                        + "WHERE codigoBrigada=(SELECT codigoBrigada FROM bombero WHERE idBombero=?);";     // hay que ver si esto funciona
                ps.setInt(1, idBombero);
                ps = connection.prepareStatement(sql);
                if (ps.executeUpdate() > 0) {
                    resultado = true;
                    System.out.println("[BomberoData] Brigada actualizada");
                } else {                    
                    System.out.println("[BomberoData] No se pudo actualizar la brigada");
                }
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
                System.out.println("[BomberoData] Bombero eliminado");
                
                // actualizando 'cantBomberos' en la brigada en la cual se dió de baja al bombero
                sql = "UPDATE brigada SET cantBomberos=cantBomberos-1 "
                        + "WHERE codigoBrigada=(SELECT codigoBrigada FROM bombero WHERE dni=?);";     // hay que ver si esto funciona
                ps.setInt(1, dni);
                ps = connection.prepareStatement(sql);
                if (ps.executeUpdate() > 0) {
                    resultado = true;
                    System.out.println("[BomberoData] Brigada actualizada");
                } else {                    
                    System.out.println("[BomberoData] No se pudo actualizar la brigada");
                }
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
