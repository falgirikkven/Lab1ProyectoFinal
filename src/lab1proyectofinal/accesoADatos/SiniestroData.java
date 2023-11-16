package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.BrigadaDistancia;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class SiniestroData {

    private static Cuartel cuartelNulo = null;
    private static Brigada brigadaNula = null;
    public static String nombreEntidadNula = "nulo";

    private Connection connection;

    public SiniestroData() {
        this.connection = Conexion.getInstance();
    }

    public static void inicializar() {
        CuartelData cuartelData = new CuartelData();
        BrigadaData brigadaData = new BrigadaData();
        Cuartel cuartel = cuartelData.buscarCuartelPorNombre(nombreEntidadNula);
        if (cuartel == null) {
            cuartel = new Cuartel(nombreEntidadNula, "", 0, 0, "0", "");
            if (!cuartelData.guardarCuartel(cuartel) || !cuartelData.eliminarCuartelPorNombre(nombreEntidadNula)) {
                System.out.println("[SiniestroData.inicializar] Error al inicializar");
                return;
            }
        }

        Brigada brigada = brigadaData.buscarBrigadaPorNombre(nombreEntidadNula);
        if (brigada == null) {
            brigada = new Brigada(nombreEntidadNula, "", false, cuartel);
            if (!brigadaData.guardarBrigada(brigada) || !brigadaData.eliminarBrigadaPorNombre(nombreEntidadNula)) {
                System.out.println("[SiniestroData.inicializar] Error al inicializar");
                return;
            }
        }

        cuartel.setEstado(false);
        brigada.setEstado(false);

        cuartelNulo = cuartel;
        brigadaNula = brigada;
    }

    public static Cuartel obtenerCuartelNulo() {
        if (cuartelNulo == null) {
            inicializar();
        }
        return cuartelNulo;
    }

    public static Brigada obtenerBrigadaNula() {
        if (brigadaNula == null) {
            inicializar();
        }
        return brigadaNula;
    }

    public boolean guardarSiniestro(Siniestro siniestro) {
        if (siniestro.getCodigoSiniestro() != Utils.NIL) {
            System.out.println("[SiniestroData.guardarSiniestro] Error: no se puede guardar. "
                    + "Siniestro dado de baja o tiene codigoSiniestro definido. "
                    + siniestro.debugToString());
            return false;
        }

        // corroborando la validez de los datos ingresados
        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fecHorIni = siniestro.getFechaHoraInicio();
        LocalDateTime fecHorRes = siniestro.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                    + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora actual");
            return false;
        }
        if (fecHorRes != null && puntuacion != Utils.NIL) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora de resolución de la misma");
                return false;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. La fecha y hora de resolución de la "
                        + "emergencia (" + fecHorRes + ") no puede ser posterior a la fecha y hora actual");
                return false;
            } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. Puntuacion (" + puntuacion + ") fuera "
                        + "de rango");
                return false;
            }
        } else if (fecHorRes != null || puntuacion != Utils.NIL) {
            System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. No se puede establecer una fecha de resolución "
                    + "sin establecer una puntuación, ni viceversa");
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(fecHorIni));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            if (siniestro.getBrigada() != null) {
                ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            } else {
                ps.setInt(6, SiniestroData.obtenerBrigadaNula().getCodigoBrigada());
            }
            if (fecHorRes == null && puntuacion == Utils.NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (fecHorRes != null && puntuacion != Utils.NIL) {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setInt(8, puntuacion);
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                siniestro.setCodigoSiniestro(rs.getInt(1));
                resultado = true;
                System.out.println("[SiniestroData.guardarSiniestro] "
                        + "Agregado: " + siniestro.debugToString());
            } else {
                System.out.println("[SiniestroData.guardarSiniestro] "
                        + "No se pudo agregar: " + siniestro.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[SiniestroData.guardarSiniestro] "
                        + "Error: entrada duplicada para " + siniestro.debugToString());
            } else {
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
                System.out.println("[SiniestroData.buscarSiniestro] "
                        + "Encontrado: " + siniestro.debugToString());
            } else {
                System.out.println("[SiniestroData.buscarSiniestro] "
                        + "No se ha encontrado con codigoSiniestro=" + codigoSiniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.buscarSiniestro] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            System.out.println("[SiniestroData.listarSiniestros] "
                    + "Cantidad de siniestros: " + siniestros.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestros] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosResueltos() {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.puntuacion!=-1 AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            System.out.println("[SiniestroData.listarSiniestrosResueltos] "
                    + "Cantidad de siniestros: " + siniestros.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosResueltos] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosSinResolucion() {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE siniestro.puntuacion=-1 AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            System.out.println("[SiniestroData.listarSiniestrosSinResolucion] "
                    + "Cantidad de siniestros: " + siniestros.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosSinResolucion] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE (fechaHoraInicio>=? AND fechaHoraInicio<=?) "
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            System.out.println("[SiniestroData.listarSiniestrosEntreFechas] "
                    + "Cantidad de siniestros: " + siniestros.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosEntreFechas] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosInicioFinEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = null;
        try {
            String sql = "SELECT * FROM siniestro, brigada, cuartel "
                    + "WHERE (fechaHoraInicio>=? AND fechaHoraResolucion<=?) " // comparar NULL con algo aparenta devolver siempre falso
                    + "AND siniestro.codigoBrigada=brigada.codigoBrigada "
                    + "AND brigada.codigoCuartel=cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            siniestros = new ArrayList();
            while (rs.next()) {
                Siniestro siniestro = Utils.obtenerDeResultSetSiniestro(rs);
                siniestros.add(siniestro);
            }
            System.out.println("[SiniestroData.listarSiniestrosInicioFinEntreFechas] "
                    + "Cantidad de siniestros: " + siniestros.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarSiniestrosInicioFinEntreFechas] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<BrigadaDistancia> listarBrigadasConvenientes(Siniestro siniestro) {
        // TODO: Corregir sentencia SQL para que tome en cuenta cantidad de bomberos
        // y que no este atentiendo un siniestro

        // BrigadaData.listarBrigadasAsignables (casi)
        List<BrigadaDistancia> brigadas = null;
        try {
            //String sql = "SELECT * FROM brigada "
            //        + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel AND cuartel.estado = true) "
            //        + "WHERE brigada.estado = true AND brigada.disponible = true;";
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true "
                    + "AND brigada.disponible = true "
                    + "AND brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero WHERE bombero.codigoBrigada=brigada.codigoBrigada AND bombero.estado=true)=5 "
                    + "AND (SELECT COUNT(codigoBrigada) FROM siniestro WHERE siniestro.codigoBrigada=brigada.codigoBrigada AND siniestro.puntuacion=-1)=0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                double distanciaX = (double) (siniestro.getCoordenadaX() - brigada.getCuartel().getCoordenadaX());
                double distanciaY = (double) (siniestro.getCoordenadaY() - brigada.getCuartel().getCoordenadaY());
                double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
                brigadas.add(new BrigadaDistancia(brigada, distancia));
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.listarBrigadasConvenientes] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        List<BrigadaDistancia> brigadasConvenientes = new ArrayList();
        List<BrigadaDistancia> inconveniInconvenientes = new ArrayList();
        for (BrigadaDistancia bd : brigadas) {
            if (bd.getBrigada().getEspecialidad().equals(siniestro.getTipo())) {
                brigadasConvenientes.add(bd);
            } else {
                inconveniInconvenientes.add(bd);
            }
        }
        Collections.sort(brigadasConvenientes);
        Collections.sort(inconveniInconvenientes);

        brigadas.clear();
        for (BrigadaDistancia bd : brigadasConvenientes) {
            brigadas.add(bd);
        }
        for (BrigadaDistancia bd : inconveniInconvenientes) {
            brigadas.add(bd);
        }

        System.out.println("[SiniestroData.listarBrigadasConvenientes] "
                + "Cantidad de brigadas: " + brigadas.size());
        return brigadas;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {
        if (siniestro.getCodigoSiniestro() == Utils.NIL) {
            System.out.println("[SiniestroData.modificarSiniestro] Error: no se pudo modificar."
                    + "Siniestro dado de baja o no tiene codigoSiniestro definido. "
                    + siniestro.debugToString());
            return false;
        }

        // corroborando la validez de los datos ingresados
        int puntuacion = siniestro.getPuntuacion();
        LocalDateTime fecHorIni = siniestro.getFechaHoraInicio();
        LocalDateTime fecHorRes = siniestro.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[SiniestroData.modificarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                    + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora actual");
            return false;
        }
        if (fecHorRes != null && puntuacion != Utils.NIL) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[SiniestroData.modificarSiniestro] Error al agregar. La fecha y hora de inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la fecha y hora de resolución de la misma");
                return false;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[SiniestroData.modificarSiniestro] Error al agregar. La fecha y hora de resolución de la "
                        + "emergencia (" + fecHorRes + ") no puede ser posterior a la fecha y hora actual");
                return false;
            } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                System.out.println("[SiniestroData.guardarSiniestro] Error al agregar. Puntuacion (" + puntuacion + ") fuera "
                        + "de rango");
                return false;
            }
        } else if (fecHorRes != null || puntuacion != Utils.NIL) {
            System.out.println("[SiniestroData.modificarSiniestro] Error al agregar. No se puede establecer una fecha de resolución "
                    + "sin establecer una puntuación, ni viceversa");
            return false;
        }

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
            if (siniestro.getBrigada() != null) {
                ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            } else {
                ps.setInt(6, SiniestroData.obtenerBrigadaNula().getCodigoBrigada());
            }
            if (fecHorRes == null && puntuacion == Utils.NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setInt(8, puntuacion);
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData.modificarSiniestro] "
                        + "Modificado: " + siniestro.debugToString());
            } else {
                System.out.println("[SiniestroData.modificarSiniestro] "
                        + "No se pudo modificar: " + siniestro.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.modificarSiniestro] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
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
                System.out.println("[SiniestroData.eliminarSiniestro] "
                        + "Eliminado: codigoSiniestro=" + codigoSiniestro);
            } else {
                System.out.println("[SiniestroData.eliminarSiniestro] "
                        + "No se pudo eliminar: codigoCuartel=" + codigoSiniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData.eliminarSiniestro] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
