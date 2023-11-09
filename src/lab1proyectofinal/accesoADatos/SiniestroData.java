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
import lab1proyectofinal.entidades.*;

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
            // corroborando la validez de los datos ingresados
            int puntuacion = siniestro.getPuntuacion();
            LocalDateTime fecHorIni = siniestro.getFechaHoraInicio();
            LocalDateTime fecHorRes = siniestro.getFechaHoraResolucion();
            if (fecHorIni.isAfter(LocalDateTime.now())) {
                    System.out.println("[SiniestroData] Error al agregar. La fecha y hora de inicio de la emergencia no puede ser posterior a la fecha y hora actual");
                    return resultado;
            } 
            if (fecHorRes != null && puntuacion != Siniestro.PUNTUACION_NIL) {
                if (fecHorIni.isAfter(fecHorRes)) {
                    System.out.println("[SiniestroData] Error al agregar. La fecha y hora de inicio de la emergencia no puede ser posterior a la fecha y hora de resolución de la misma");
                    return resultado;
                } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    System.out.println("[SiniestroData] Error al agregar. Puntuacion fuera de rango");
                    return resultado;
                }  
            }
            
            String sql;
            if (siniestro.getCodigoSiniestro() != -1) {
                sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion, codigoSiniestro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(fecHorIni));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            if (fecHorRes == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (fecHorRes != null && puntuacion != Siniestro.PUNTUACION_NIL) {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setInt(8, puntuacion);
            } else {
                ps.close();
                System.out.println("[SiniestroData] Error al agregar. No puede establecer la puntuación sin establecer la fecha de resolución, ni viceversa");
                return resultado;
            }
            if (siniestro.getCodigoSiniestro() != -1) {
                ps.setInt(9, siniestro.getCodigoSiniestro());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                siniestro.setCodigoSiniestro(rs.getInt(1));
                resultado = true;
                System.out.println("[SiniestroData] Siniestro agregado");
            } else {
                System.out.println("[SiniestroData] No ha podido agregar el siniestro");
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
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE codigoSiniestro=? "
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                System.out.println("[SiniestroData] Siniestro con codigo '" + codigoSiniestro + "' encontrado");
            } else {
                System.out.println("[SiniestroData] No se ha encontrado el siniestro con código '" + codigoSiniestro + "'");
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
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosResueltos() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.puntuacion!=-1 AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
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
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.puntuacion=-1 AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
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
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE (fechaHoraInicio>=? AND fechaHoraResolucion<=?) "      // comparar NULL con algo podría ser fuente de errores
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }
    
    public List<Siniestro> listarSiniestrosInicioEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE (fechaHoraInicio>=? AND fechaHoraInicio<=?) "      
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            if (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
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
            sql = "UPDATE siniestro SET tipo=?, fechaHoraInicio=?, coordenadaX=?, coordenadaY=?, detalles=?, "
                    + "codigoBrigada=?, fechaHoraResolucion=?, puntuacion=? "
                    + "WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(siniestro.getFechaHoraInicio()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            int puntuacion = siniestro.getPuntuacion();
            if (siniestro.getFechaHoraResolucion() == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (siniestro.getFechaHoraResolucion() != null && siniestro.getPuntuacion() != -1) {
                if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    ps.close();
                    System.out.println("[SiniestroData] Error al modificar. Puntuacion fuera de rango");
                    return false;
                }
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaHoraResolucion()));
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

    public boolean asignarResolucion(Siniestro siniestro, LocalDateTime fechaHoraResolucion, int puntuacion) {
        if (!(fechaHoraResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Tanto la fecha de resolución como la puntuación deben ser establecidos");
            return false;
        } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Puntuacion fuera de rango");
            return false;
        } else if (siniestro.getFechaHoraInicio().isAfter(fechaHoraResolucion)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. La fecha de resolución es anterior a la fecha de inicio del siniestro");
            return false;
        }
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaHoraResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fechaHoraResolucion));
            ps.setInt(2, puntuacion);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                System.out.println("[SiniestroData] Resolucion asignada");

                // actualizar la brigada que trató la emergencia
                sql = "UPDATE brigada SET tratandoSiniestro=true WHERE codigoBrigada=?;";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, siniestro.getBrigada().getCodigoBrigada());
                if (ps.executeUpdate() > 0) {
                    resultado = true;
                    System.out.println("[SiniestroData] Brigada actualizada");
                } else {
                    System.out.println("[SiniestroData] No se pudo actualizar la brigada");
                }

            } else {
                System.out.println("[SiniestroData] No se pudo asignar la resolucion");
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
