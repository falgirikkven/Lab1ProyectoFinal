package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class SiniestroData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public SiniestroData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarSiniestro(Siniestro siniestro) {
        boolean resultado = false;
        try {
            // String sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaResolucion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            String sql;
            if (siniestro.getCodigoSiniestro() != -1) {
                sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaResolucion, puntuacion, codigoSiniestro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaResolucion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(siniestro.getFechaSiniestro()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getCodigoBrigada());
            int puntuacion = siniestro.getPuntuacion();
            if (siniestro.getFechaResolucion() == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (siniestro.getFechaResolucion() != null && puntuacion != Siniestro.PUNTUACION_NIL) {
                if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    ps.close();
                    System.out.println("[SiniestroData] Error al agregar. Puntuacion fuera de rango");
                    return false;
                }
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaResolucion()));
                ps.setInt(8, puntuacion);
            } else {
                ps.close();
                System.out.println("[SiniestroData] Error al agregar. Los datos de la resolucion son inconsistentes");
                return false;
            }
            if (siniestro.getCodigoSiniestro() != -1) {
                ps.setInt(9, siniestro.getCodigoSiniestro());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro agregado");
            } else {
                System.out.println("[SiniestroData] No se pudo agregado el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            // System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            // e.printStackTrace();
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Siniestro buscarSiniestro(int codigoSiniestro) {
        Siniestro siniestro = null;
        try {
            String sql = "SELECT * FROM siniestro WHERE codigoSiniestro=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getTimestamp("fechaSiniestro").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                // siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                System.out.println("[SiniestroData] Siniestro con codigo '" + codigoSiniestro + "' encontrado");
            } else {
                System.out.println("[SiniestroData] No se ha encontrado a la siniestro '" + codigoSiniestro + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getTimestamp("fechaSiniestro").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosSinResolucion() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro WHERE puntuacion=-1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getTimestamp("fechaSiniestro").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                // siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        // Implementar este metodo
        List<Siniestro> siniestros = new ArrayList();
        //String sql = "SELECT * FROM siniestro WHERE NOT(ISNULL(fechaResolucion)) AND fechaResolucion BETWEEN ? AND ?";
        String sql = "SELECT * FROM siniestro WHERE fechaSiniestro BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getTimestamp("fechaSiniestro").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                //siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                //siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaResolucion(rs.getTimestamp("fechaResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET tipo=?, fechaSiniestro=?, coordenadaX=?, coordenadaY=?, detalles=?, codigoBrigada=?, fechaResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(siniestro.getFechaSiniestro()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getCodigoBrigada());
            int puntuacion = siniestro.getPuntuacion();
            if (siniestro.getFechaResolucion() == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (siniestro.getFechaResolucion() != null && siniestro.getPuntuacion() != -1) {
                if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    ps.close();
                    System.out.println("[SiniestroData] Error al modificar. Puntuacion fuera de rango");
                    return false;
                }
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaResolucion()));
                ps.setInt(8, siniestro.getPuntuacion());
            } else {
                ps.close();
                System.out.println("[SiniestroData] Error al modificar. Los datos de la resolucion son inconsistentes");
                return false;
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro modificado");
            } else {
                System.out.println("[SiniestroData] No se pudo modificar el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean asignarResolucion(Siniestro siniestro, LocalDateTime fechaResolucion, int puntuacion) {
        if (!(fechaResolucion == null && puntuacion == Siniestro.PUNTUACION_NIL) && !(fechaResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Datos inconsistentes");
            return false;
        } else if (puntuacion != Siniestro.PUNTUACION_NIL && (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Puntuacion fuera de rango");
            return false;
        }
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (fechaResolucion != null) {
                ps.setTimestamp(1, Timestamp.valueOf(fechaResolucion));
            } else {
                ps.setNull(1, Types.TIMESTAMP);
            }
            ps.setInt(2, puntuacion);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Resolucion asignada");
            } else {
                System.out.println("[SiniestroData] No se pudo asignar resolucion");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarSiniestro(int codigoSiniestro) {
        boolean resultado = false;
        try {
            String sql = "DELETE FROM siniestro WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro eliminado");
            } else {
                System.out.println("[SiniestroData] No se pudo eliminar el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
