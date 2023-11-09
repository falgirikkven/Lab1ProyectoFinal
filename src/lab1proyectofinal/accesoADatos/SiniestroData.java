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

        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fechaResolucion = siniestro.getFechaResolucion();
        if (!(fechaResolucion == null && puntuacion == Siniestro.PUNTUACION_NIL) && !(fechaResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error: datos de resolucion inconsistentes. " + siniestro.toString());
            return false;
        } else if (puntuacion != Siniestro.PUNTUACION_NIL && (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error: puntuacion de resolucion fuera de rango. " + siniestro.toString());
            return false;
        } else if (fechaResolucion != null && siniestro.getFechaSiniestro().isAfter(fechaResolucion)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error: fecha de siniestro es posterior a la fecha de resolucion. " + siniestro.toString());
            return false;
        }

        boolean resultado = false;
        try {
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
            if (fechaResolucion == null) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaResolucion()));
                ps.setInt(8, puntuacion);
            }
            if (siniestro.getCodigoSiniestro() != -1) {
                ps.setInt(9, siniestro.getCodigoSiniestro());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData.guardarSiniestro] Agregado: " + siniestro.toString());
            } else {
                System.out.println("[SiniestroData.guardarSiniestro] No se agregó: " + siniestro.toString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[SiniestroData.guardarSiniestro] Error: entrada duplicada para " + siniestro.toString());
            } else {
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
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                System.out.println("[SiniestroData.buscarSiniestro] Encontrado: " + siniestro.toString());
            } else {
                System.out.println("[CuartelData.buscarCuartel] No se ha encontrado con codigoSiniestro=" + codigoSiniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.buscarSiniestro] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestros] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosSinResolucion() {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro WHERE puntuacion=-1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosSinResolucion] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = null;
        String sql = "SELECT * FROM siniestro WHERE fechaSiniestro BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosEntreFechas] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {

        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fechaResolucion = siniestro.getFechaResolucion();
        if (!(fechaResolucion == null && puntuacion == Siniestro.PUNTUACION_NIL) && !(fechaResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData.modificarSiniestro] Error: datos de resolucion inconsistentes. " + siniestro.toString());
            return false;
        } else if (puntuacion != Siniestro.PUNTUACION_NIL && (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX)) {
            System.out.println("[SiniestroData.modificarSiniestro] Error: puntuacion de resolucion fuera de rango. " + siniestro.toString());
            return false;
        } else if (fechaResolucion != null && siniestro.getFechaSiniestro().isAfter(fechaResolucion)) {
            System.out.println("[SiniestroData.modificarSiniestro] Error: fecha de siniestro es posterior a la fecha de resolucion. " + siniestro.toString());
            return false;
        }

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
            if (fechaResolucion == null) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaResolucion()));
                ps.setInt(8, puntuacion);
            }
            if (siniestro.getCodigoSiniestro() != -1) {
                ps.setInt(9, siniestro.getCodigoSiniestro());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData.modificarSiniestro] Modificado: " + siniestro.toString());
            } else {
                System.out.println("[SiniestroData.modificarSiniestro] No se modificó: " + siniestro.toString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.modificarSiniestro] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean asignarResolucion(Siniestro siniestro, LocalDateTime fechaResolucion, int puntuacion) {
        if (fechaResolucion == null || puntuacion == Siniestro.PUNTUACION_NIL) {
            System.out.println("[SiniestroData.asignarResolucion] Error: datos de resolucion inconsistentes");
            return false;
        } else if ((puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX)) {
            System.out.println("[SiniestroData.asignarResolucion] Error: puntuacion de resolucion fuera de rango");
            return false;
        } else if (siniestro.getFechaSiniestro().isAfter(fechaResolucion)) {
            System.out.println("[SiniestroData.asignarResolucion] Error: fecha de siniestro es posterior a la fecha de resolucion");
            return false;
        }

        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setNull(1, Types.TIMESTAMP);
            ps.setInt(2, puntuacion);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData.asignarResolucion] Resolucion asignada");
            } else {
                System.out.println("[SiniestroData.asignarResolucion] No se pudo asignar resolucion");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.asignarResolucion] Error" + e.getErrorCode() + ": " + e.getMessage());
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
                System.out.println("[SiniestroData.eliminarSiniestro] Eliminado: codigoSiniestro=" + codigoSiniestro);
            } else {
                System.out.println("[SiniestroData.eliminarSiniestro] No se eliminó: codigoCuartel=" + codigoSiniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.eliminarSiniestro] Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
