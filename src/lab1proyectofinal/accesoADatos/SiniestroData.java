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

        // corroborando la validez de los datos ingresados
        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fecHorIni = siniestro.getFechaHoraInicio();
        LocalDateTime fecHorRes = siniestro.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                    + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora actual");
            return resultado;
        }
        if (fecHorRes != null && puntuacion != Siniestro.PUNTUACION_NIL) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora de resolución de la misma");
                return resultado;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de resolución de la "
                        + "emergencia (" + fecHorRes + ") no puede ser posterior a la fecha y hora actual");
                return resultado;
            } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. Puntuacion (" + puntuacion + ") fuera "
                        + "de rango");
                return resultado;
            }
        } else if (fecHorRes != null || puntuacion != Siniestro.PUNTUACION_NIL) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. No se puede establecer una fecha de resolución "
                    + "sin establecer una puntuación, ni viceversa");
            return false;
        }
        try {
            String sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(fecHorIni));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            // llegados a este punto del código, respecto de 'fechaHoraResolucion' y 'puntuacion' podemos decir que o ambos están establecido, o ambos no están establecidos
            if (fecHorRes == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (fecHorRes != null && puntuacion != Siniestro.PUNTUACION_NIL) {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setInt(8, puntuacion);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                siniestro.setCodigoSiniestro(rs.getInt(1));
                resultado = true;
                System.out.println("[SiniestroData.guardarSiniestro] Siniestro agregado"
                        + "\nDatos del siniestro agregado: " + siniestro.debugToString());
            } else {
                System.out.println("[SiniestroData.guardarSiniestro] No ha podido agregar el siniestro"
                        + "\nDatos del siniestro que se intentó agregar: " + siniestro.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
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
                System.out.println("[SiniestroData.buscarSiniestro] Siniestro encontrado (codigo del siniestro: " + codigoSiniestro + ")");
            } else {
                System.out.println("[SiniestroData.buscarSiniestro] No se ha encontrado el siniestro (codigo del siniestro: " + codigoSiniestro + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.buscarSiniestro] Error " + e.getErrorCode() + " " + e.getMessage());
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
            while (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestros] Error " + e.getErrorCode() + " " + e.getMessage());
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
            while (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosResueltos] Error " + e.getErrorCode() + " " + e.getMessage());
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
            while (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosSinResolucion] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE (fechaHoraInicio>=? AND fechaHoraResolucion<=?) " // comparar NULL con algo aparenta devolver siempre falso
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosEntreFechas] Error " + e.getErrorCode() + " " + e.getMessage());
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
            while (rs.next()) {
                siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosInicioEntreFechas] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {
        boolean resultado = false;

        // corroborando la validez de los datos ingresados
        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fecHorIni = siniestro.getFechaHoraInicio();
        LocalDateTime fecHorRes = siniestro.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                    + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora actual");
            return resultado;
        }
        if (fecHorRes != null && puntuacion != Siniestro.PUNTUACION_NIL) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora de resolución de la misma");
                return resultado;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de resolución de la "
                        + "emergencia (" + fecHorRes + ") no puede ser posterior a la fecha y hora actual");
                return resultado;
            } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. Puntuacion (" + puntuacion + ") fuera "
                        + "de rango");
                return resultado;
            }
        } else if (fecHorRes != null || puntuacion != Siniestro.PUNTUACION_NIL) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. No se puede establecer una fecha de resolución "
                    + "sin establecer una puntuación, ni viceversa");
            return false;
        }
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
            // llegados a este punto del código, respecto de 'fechaHoraResolucion' y 'puntuacion' podemos decir que o ambos están establecido, o ambos no están establecidos
            if (fecHorRes == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setInt(8, puntuacion);
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro modificado"
                        + "\nNuevos datos guardados: " + siniestro.debugToString());
            } else {
                System.out.println("[SiniestroData] No se pudo modificar el siniestro"
                        + "\nDatos que se intentaron guardar: " + siniestro.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean asignarResolucion(Siniestro siniestro, LocalDateTime fechaHoraResolucion, int puntuacion) {
        boolean resultado = false;

        if (!(fechaHoraResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData.asignarResolucion] Error al asignar resolución. Tanto la fecha y hora de la resolución como la puntuación deben ser establecidos");
            return false;
        } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
            System.out.println("[SiniestroData.asignarResolucion] Error al asignar resolución. Puntuacion (" + puntuacion + ") fuera de rango");
            return false;
        } else if (siniestro.getFechaHoraInicio().isAfter(fechaHoraResolucion)) {
            System.out.println("[SiniestroData.asignarResolucion] Error al asignar resolución. La fecha y hora de resolución de la "
                    + "emergencia (" + siniestro.getFechaHoraInicio() + ") es anterior a la fecha y hora de inicio del siniestro");
            return false;
        } else if (fechaHoraResolucion.isAfter(LocalDateTime.now())) {
            System.out.println("[SiniestroData.asignarResolucion] Error al asignar resolución. La fecha y hora de resolución de la "
                    + "emergencia (" + fechaHoraResolucion + ") no puede ser posterior a la fecha y hora actual");
            return false;
        }
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaHoraResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fechaHoraResolucion));
            ps.setInt(2, puntuacion);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData.asignarResolucion] Resolucion asignada"
                        + "\nDatos del siniestro al que se le asignó la resolución: " + siniestro.debugToString()
                        + "\nFecha de resolución asignada: " + fechaHoraResolucion
                        + "\nPuntuación asignada: " + puntuacion);
            } else {
                System.out.println("[SiniestroData.asignarResolucion] No se pudo asignar la resolucion"
                        + "\nDatos del siniestro al que se le intentó asignar la resolución: " + siniestro.debugToString()
                        + "\nFecha de resolución que se intentó asignar: " + fechaHoraResolucion
                        + "\nPuntuación que se intentó asignar: " + puntuacion);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.asignarResolucion] Error " + e.getErrorCode() + " " + e.getMessage());
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
                System.out.println("[SiniestroData.eliminarSiniestro] Siniestro eliminado (codigo del siniestro: " + codigoSiniestro + ")");
            } else {
                System.out.println("[SiniestroData.eliminarSiniestro] No se pudo eliminar el siniestro (codigo del siniestro: " + codigoSiniestro + ")");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.eliminarSiniestro] Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
